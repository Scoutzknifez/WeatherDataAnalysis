package com.scoutzknifez.weatherdataanalysis;

import com.scoutzknifez.weatherdataanalysis.graphics.OverallView;
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

    public static void main(String[] args) {
        List<WeatherForTime> weathers = (List<WeatherForTime>) SQLHelper.getFromTable(Table.WEATHER_FOR_TIME);
        List<TimeAtMoment> times = new ArrayList<>();
        weathers.forEach(weatherForTime -> times.add(new TimeAtMoment(weatherForTime.getTime() * 1000)));

        int i = 0;
        for(WeatherForTime wft : weathers) {
            i++;
            Utils.log("%s: %s", i, wft);
        }

        i = 0;
        for(TimeAtMoment tam : times) {
            i++;
            Utils.log("%s: %s", i , tam);
        }

        makeFrame();
    }

    private static void makeFrame() {
        JFrame frame = new JFrame("Weather");
        overallView = new OverallView();
        frame.setContentPane(overallView.getParentContainer());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();

        frame.setSize(Constants.MAIN_APPLICATION_WIDTH, Constants.MAIN_APPLICATION_HEIGHT);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation((int) dimension.getWidth()/2 - Constants.MAIN_APPLICATION_WIDTH/2, (int) dimension.getHeight()/2 - Constants.MAIN_APPLICATION_HEIGHT/2);
        frame.setMinimumSize(new Dimension(700, Constants.MAIN_APPLICATION_HEIGHT/2));

        frame.setVisible(true);
    }
}
