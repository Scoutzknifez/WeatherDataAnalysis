package com.scoutzknifez.weatherdataanalysis.utility;

import com.scoutzknifez.weatherdataanalysis.structures.dtos.WeatherForTime;
import com.scoutzknifez.weatherdataanalysis.utility.structures.TimeAtMoment;

import java.awt.*;

public class Utils {

    public static Color getGradientBetween(Color color1, Color color2, float similarityToColor2) {
        int r = (int) ((color2.getRed() * similarityToColor2) + (color1.getRed() * (1 - similarityToColor2)));
        int g = (int) ((color2.getGreen() * similarityToColor2) + (color1.getGreen() * (1 - similarityToColor2)));
        int b = (int) ((color2.getBlue() * similarityToColor2) + (color1.getBlue() * (1 - similarityToColor2)));

        return new Color(r, g, b);
    }

    public static Color getColorFromWeather(WeatherForTime weather) {

        if (weather.getTemperature() >= 150) {
            return Color.PINK;
        }
        else if (weather.getTemperature() >= 100) {
            float percent = (float) (weather.getTemperature() - 100f) / 50f;
            return getGradientBetween(Color.RED, Color.PINK, percent);
        }
        else if (weather.getTemperature() >= 75) {
            float percent = (float) (weather.getTemperature() - 75f) / 25f;
            return getGradientBetween(Color.YELLOW, Color.RED, percent);
        }
        else if (weather.getTemperature() >= 65) {
            float percent = (float) (weather.getTemperature() - 65f) / 10f;
            return getGradientBetween(Color.GREEN, Color.YELLOW, percent);
        }
        else if (weather.getTemperature() >= 55) {
            float percent = (float) (weather.getTemperature() - 55f) / 10f;
            return getGradientBetween(Color.BLUE, Color.GREEN, percent);
        }
        else if (weather.getTemperature() >= 33) {
            float percent = (float) (weather.getTemperature() - 33f) / 22f;
            return getGradientBetween(Color.CYAN, Color.BLUE, percent);
        }
        else if (weather.getTemperature() >= 0) {
            float percent = (float) weather.getTemperature() / 33f;
            return getGradientBetween(Color.WHITE, Color.CYAN, percent);
        }
        else {
            return Color.WHITE;
        }
    }


    /**
     * Sends a message out to console with time stamp of log execution
     *
     * NOTE: use %s to replace part of string with object
     *
     * @param message   message to display with replaceable characters for objects
     * @param objects   Objects to replace inside of the message string
     */
    public static void log(String message, Object... objects) {
        TimeAtMoment timeAtMoment = new TimeAtMoment(System.currentTimeMillis());

        System.out.println("[" + timeAtMoment + "] " + String.format(message, objects));
    }

    public static void log(Object obj) {
        log(obj.toString(), null);
    }

    public static double getRoundedInt(String string) {
        return getRoundedInt(getDouble(string));
    }

    public static int getRoundedInt(double input) {
        return (int) Math.round(input);
    }

    public static double getDouble(String string) {
        try {
            return Double.parseDouble(string);
        } catch (Exception e) {
            Utils.log("Can not parse String into double. %s", e);
            return -1.0;
        }
    }

    public static int getInt(String string) {
        try {
            return Integer.parseInt(string);
        } catch (Exception e) {
            Utils.log("Can not parse String into int. %s", e);
            return -1;
        }
    }
}
