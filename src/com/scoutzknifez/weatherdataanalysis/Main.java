package com.scoutzknifez.weatherdataanalysis;

import com.scoutzknifez.weatherdataanalysis.graphics.OverallView;
import com.scoutzknifez.weatherdataanalysis.graphics.ViewModelConstants;
import com.scoutzknifez.weatherdataanalysis.sql.SQLHelper;
import com.scoutzknifez.weatherdataanalysis.sql.Table;
import com.scoutzknifez.weatherdataanalysis.structures.dtos.WeatherForTime;
import com.scoutzknifez.weatherdataanalysis.utility.Constants;
import com.scoutzknifez.weatherdataanalysis.utility.Utils;
import com.scoutzknifez.weatherdataanalysis.utility.structures.TimeAtMoment;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static OverallView overallView;
    public static List<WeatherForTime> weathers;
    public static TimeAtMoment startTime;

    public static void main(String[] args) {
        weathers = (List<WeatherForTime>) SQLHelper.getFromTable(Table.WEATHER_FOR_TIME);
        startTime = new TimeAtMoment(weathers.get(0).getTime() * 1000);

        makeFrame();
    }

    private static void makeFrame() {
        JFrame frame = new JFrame("Weather");
        overallView = new OverallView();
        frame.setContentPane(overallView.getParentContainer());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();

        frame.setSize(ViewModelConstants.MAIN_APPLICATION_WIDTH, ViewModelConstants.MAIN_APPLICATION_HEIGHT);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation((int) dimension.getWidth()/2 - ViewModelConstants.MAIN_APPLICATION_WIDTH/2,
                (int) dimension.getHeight()/2 - ViewModelConstants.MAIN_APPLICATION_HEIGHT/2
        );
        frame.setMinimumSize(new Dimension(700, ViewModelConstants.MAIN_APPLICATION_HEIGHT/2));

        frame.setVisible(true);
    }
}
