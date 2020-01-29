package com.scoutzknifez.weatherdataanalysis.utility;

import com.scoutzknifez.weatherdataanalysis.utility.structures.TimeAtMoment;

public class Utils {
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
