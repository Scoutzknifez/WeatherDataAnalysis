package com.scoutzknifez.weatherdataanalysis.graphics;

import com.scoutzknifez.weatherdataanalysis.Main;
import com.scoutzknifez.weatherdataanalysis.structures.dtos.WeatherForTime;
import com.scoutzknifez.weatherdataanalysis.utility.Utils;
import com.scoutzknifez.weatherdataanalysis.utility.structures.TimeAtMoment;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.util.*;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

@Setter
@Getter
public class CustomPanel extends JPanel {
    private OverallView viewHolder;

    private Map<Point, WeatherForTime> pointWeatherMap = new LinkedHashMap<>();
    // X Limiters
    private double minutePixelIncrementer = .4;
    private TimeAtMoment startTime;
    // Y Limiters
    private double highestTempDisplayed = ViewModelConstants.startHigh;
    private double lowestTempDisplayed = ViewModelConstants.startLow;

    private WeatherForTime closestWeatherToMouse = null;

    Supplier<Integer> getLeftGuidelineStart = () -> 25;
    Supplier<Integer> getBottomGuidelineHeight = () -> (int) getSize().getHeight() - 40;
    Supplier<Double> getTemperatureIncrements = () -> (double) getBottomGuidelineHeight.get() / (getHighestTempDisplayed() - getLowestTempDisplayed());
    Supplier<Double> getMinuteIncrements = () -> minutePixelIncrementer;

    //Function<WeatherForTime, Double> getTemperatureX = weatherForTime ->
    Function<WeatherForTime, Double> getTemperatureY = weatherForTime -> (weatherForTime.getTemperature() - getLowestTempDisplayed()) * getTemperatureIncrements.get();

    public CustomPanel(OverallView viewHolder) {
        setViewHolder(viewHolder);
        startTime = new TimeAtMoment(Main.weathers.get(0).getTime() * 1000);

        // Listener for mouse motion inside drawing panel
        addMouseMotionListener(new MouseMotionListener() {
            // Used for scrolling across data horizontally
            @Override
            public void mouseDragged(MouseEvent e) {
                Utils.log(e.getX());
            }

            // Used for focusing on a point of data, much like desmos graphing system.
            @Override
            public void mouseMoved(MouseEvent e) {
                // Remove the marker for current hovered weather if left of start guideline.
                int xCord = e.getX();
                if (closestWeatherToMouse != null && xCord < getLeftGuidelineStart.get()) {
                    setClosestWeather(null);
                    return;
                }

                if (xCord < getLeftGuidelineStart.get())
                    return;

                List<Point> points = new ArrayList<>(pointWeatherMap.keySet());
                double closestDistance = e.getPoint().distance(points.get(0));
                Point closestPoint = points.get(0);
                for (int i = 1; i < points.size(); i++) {
                    double newDistance = e.getPoint().distance(points.get(i));
                    if (newDistance < closestDistance) {
                        closestDistance = newDistance;
                        closestPoint = points.get(i);
                    }
                }

                if (closestDistance < 25)
                    setClosestWeather(pointWeatherMap.get(closestPoint));
                else
                    setClosestWeather(null);
            }
        });

        addMouseWheelListener(e -> {
            try {
                getViewHolder().getPixelPerMinuteSpinner().setValue(e.getWheelRotation() > 0 ?
                        getViewHolder().getPixelPerMinuteSpinner().getPreviousValue() :
                        getViewHolder().getPixelPerMinuteSpinner().getNextValue());
                setClosestWeather(null);
            } catch (IllegalArgumentException ignored) {}
        });

        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    if (e.isControlDown()) {
                        Utils.log(getClosestWeatherToMouse());
                    } else {
                        if (getClosestWeatherToMouse() != null) {
                            startTime = new TimeAtMoment(getClosestWeatherToMouse().getTime() * 1000);
                            repaint();
                        }
                    }
                } else {
                    startTime = new TimeAtMoment(Main.weathers.get(0).getTime() * 1000);
                    repaint();
                }
            }
            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {
                setClosestWeather(null);
            }
        });
    }

    private void setClosestWeather(WeatherForTime weatherForTime) {
        if (closestWeatherToMouse != weatherForTime) {
            setClosestWeatherToMouse(weatherForTime);
            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        // Clear the old drawing on the panel
        super.paintComponent(g);

        // Ready the drawing panel
        pointWeatherMap.clear();
        Graphics2D g2 = (Graphics2D) g;

        drawLabels(g2);
        drawGuidelines(g2);
        graphTemperatures(g2);

        if (closestWeatherToMouse != null)
            drawClosestWeather(g2, closestWeatherToMouse);
    }

    private void drawLabels(Graphics2D g2) {
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("TimesRoman", Font.PLAIN, 20));

        AffineTransform old = g2.getTransform();
        AffineTransform transform = new AffineTransform();
        transform.rotate(Math.toRadians(-90), 20, getSize().getHeight() / 2);
        g2.transform(transform);
        g2.drawString("Temperature", 0, (int) getSize().getHeight() / 2);
        g2.setTransform(old);

        g2.drawString("Hours", getSize().width / 2, (int) getSize().getHeight() - 10);
    }

    private void drawGuidelines(Graphics2D g2) {
        g2.drawLine(getLeftGuidelineStart.get(), 0, getLeftGuidelineStart.get(), getBottomGuidelineHeight.get());
        g2.drawLine(getLeftGuidelineStart.get(), getBottomGuidelineHeight.get(),
                (int) getSize().getWidth(), getBottomGuidelineHeight.get()
        );
    }

    private void graphTemperatures(Graphics2D g2) {
        int startIndex = 0;
        for (int i = startIndex; i < Main.weathers.size(); i++) {
            if (getStartTime().getMillis() <= (Main.weathers.get(i).getTime() * 1000)) {
                startIndex = i;
                break;
            }
        }

        Stroke og = g2.getStroke();
        g2.setStroke(new BasicStroke(3));

        WeatherForTime weatherStart = Main.weathers.get(startIndex);
        Point start = getLocationOfTemperature(weatherStart);
        for (int i = startIndex + 1; i < Main.weathers.size(); i++) {
            WeatherForTime weatherEnd = Main.weathers.get(i);
            Point end = getLocationOfTemperature(weatherEnd);

            g2.setColor(
                    Utils.getGradientBetween(
                            Utils.getColorFromWeather(weatherStart),
                            Utils.getColorFromWeather(weatherEnd),
                            .5f
                    )
            );
            g2.drawLine(start.x, start.y, end.x, end.y);

            weatherStart = weatherEnd;
            start = end;
        }
        g2.setStroke(og);
    }

    private void drawClosestWeather(Graphics2D g2, WeatherForTime weather) {
        Point point = getLocationOfTemperature(weather);
        g2.setColor(Color.WHITE);
        g2.drawOval((int) point.getX() - 5, (int) point.getY() - 5, 10, 10);
    }

    private Point getLocationOfTemperature(WeatherForTime weather) {
        int x0 = getLeftGuidelineStart.get();
        int y0 = getBottomGuidelineHeight.get();

        TimeAtMoment time = new TimeAtMoment(weather.getTime() * 1000);

        Point point = new Point((int) (x0 + (startTime.minutesBetween(time) * getMinuteIncrements.get())), (int) (y0 - getTemperatureY.apply(weather)));
        pointWeatherMap.put(point, weather);

        return point;
    }
}
