package com.scoutzknifez.weatherdataanalysis.graphics;

import com.scoutzknifez.weatherdataanalysis.Main;
import com.scoutzknifez.weatherdataanalysis.structures.dtos.WeatherForTime;
import com.scoutzknifez.weatherdataanalysis.utility.Utils;
import com.scoutzknifez.weatherdataanalysis.utility.structures.TimeAtMoment;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.function.Supplier;

public class CustomPanel extends JPanel {
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
    Supplier<Double> getMinuteIncrements = () -> {
        return Math.max(.4, (double) getHourIncrements.get() / 60);
    };

    public CustomPanel() {
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        drawLabels(g2);
        drawGuidelines(g2);
        graphTemperatures(g2);
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

    private Point getLocationOfTemperature(WeatherForTime weather) {
        int x0 = getLeftGuidelineStart.get();
        int y0 = getBottomGuidelineHeight.get();

        TimeAtMoment time = new TimeAtMoment(weather.getTime() * 1000);

        Utils.log("Max increment: %s", getMinuteIncrements.get());
        Point point = new Point((int) (x0 + (Main.startTime.minutesBetween(time) * getMinuteIncrements.get())), (int) (y0 - (weather.getTemperature() * getTemperatureIncrements.get())));
        return point;
    }
}
