package com.scoutzknifez.weatherdataanalysis.graphics;

public class ViewModelConstants {
    // Screen size
    public static final int MAIN_APPLICATION_WIDTH = 1000;
    public static final int MAIN_APPLICATION_HEIGHT = 800;

    // Pixel per minute JSpinner rules
    public static final double startVal = .4;
    public static final double min = .02;
    public static final double max = 2.01;
    public static final double stepSize = .025;

    // Displayed temperature rules
    public static final int startLow = 20;
    public static final int startHigh = 100;
    public static final int lowMin = -25;
    public static final int lowMax = 40;
    public static final int highMin = 50;
    public static final int highMax = 125;
    public static final double temperatureStepSize = 2.5;
}
