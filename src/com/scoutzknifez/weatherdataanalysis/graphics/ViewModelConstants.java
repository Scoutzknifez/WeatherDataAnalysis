package com.scoutzknifez.weatherdataanalysis.graphics;

public class ViewModelConstants {
    // Screen size
    public static final int MAIN_APPLICATION_WIDTH = 1000;
    public static final int MAIN_APPLICATION_HEIGHT = 800;

    // Pixel per minute JSpinner rules
    public static double startVal = .4;
    public static double min = .04;
    public static double max = 1.01;
    public static double stepSize = .05;

    // Displayed temperature rules
    public static int startLow = 20;
    public static int startHigh = 100;
    public static int lowMin = -25;
    public static int lowMax = 40;
    public static int highMin = 50;
    public static int highMax = 125;
    public static int temperatureStepSize = 5;
}
