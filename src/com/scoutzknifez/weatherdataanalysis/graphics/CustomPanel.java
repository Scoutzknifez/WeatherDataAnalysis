package com.scoutzknifez.weatherdataanalysis.graphics;

import com.scoutzknifez.weatherdataanalysis.Main;
import com.scoutzknifez.weatherdataanalysis.structures.dtos.WeatherForTime;
import com.scoutzknifez.weatherdataanalysis.utility.structures.TimeAtMoment;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.util.*;
import java.util.List;
import java.util.function.Supplier;

@Setter
public class CustomPanel extends JPanel {
    private Map<Point, WeatherForTime> pointWeatherMap = new LinkedHashMap<>();
    private double minutePixelIncrementor = .4;
    private WeatherForTime closestWeatherToMouse = null;

    Supplier<Integer> getLeftGuidelineStart = () -> {
        return 25;
    };
    Supplier<Integer> getBottomGuidelineHeight = () -> {
        return (int) getSize().getHeight() - 40;
    };
    Supplier<Integer> getTemperatureIncrements = () -> {
        return getBottomGuidelineHeight.get() / 125;
    };
    Supplier<Integer> getHourIncrements = () -> {
        return Math.min(40, (int) (getSize().getWidth() - getLeftGuidelineStart.get()) / 24) ;
    };
    Supplier<Double> getMinuteIncrements = () -> minutePixelIncrementor;

    public CustomPanel() {
        // Listener for mouse motion inside drawing panel
        addMouseMotionListener(new MouseMotionListener() {
            // Used for scrolling across data horizontally
            @Override
            public void mouseDragged(MouseEvent e) {
                //Utils.log(e.getX());
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

                if (closestDistance < 25) {
                    setClosestWeather(pointWeatherMap.get(closestPoint));
                } else {
                    setClosestWeather(null);
                }
            }
        });

        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

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

        if (closestWeatherToMouse != null) {
            drawClosestWeather(g2, closestWeatherToMouse);
        }
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
        Point start = getLocationOfTemperature(Main.weathers.get(0));
        for (int i = 1; i < Main.weathers.size(); i++) {
            Point end = getLocationOfTemperature(Main.weathers.get(i));
            g2.drawLine(start.x, start.y, end.x, end.y);
            start = end;
        }
    }

    private void drawClosestWeather(Graphics2D g2, WeatherForTime weather) {
        Point point = getLocationOfTemperature(weather);
        g2.drawOval((int) point.getX() - 5, (int) point.getY() - 5, 10, 10);
    }

    private Point getLocationOfTemperature(WeatherForTime weather) {
        int x0 = getLeftGuidelineStart.get();
        int y0 = getBottomGuidelineHeight.get();

        TimeAtMoment time = new TimeAtMoment(weather.getTime() * 1000);

        Point point = new Point((int) (x0 + (Main.startTime.minutesBetween(time) * getMinuteIncrements.get())), (int) (y0 - (weather.getTemperature() * getTemperatureIncrements.get())));
        pointWeatherMap.put(point, weather);

        return point;
    }
}
