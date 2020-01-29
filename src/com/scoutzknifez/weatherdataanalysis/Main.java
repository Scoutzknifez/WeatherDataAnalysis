package com.scoutzknifez.weatherdataanalysis;

import com.scoutzknifez.weatherdataanalysis.sql.SQLHelper;
import com.scoutzknifez.weatherdataanalysis.sql.Table;
import com.scoutzknifez.weatherdataanalysis.structures.dtos.WeatherForTime;
import com.scoutzknifez.weatherdataanalysis.utility.Utils;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<WeatherForTime> weathers = (List<WeatherForTime>) SQLHelper.getFromTable(Table.WEATHER_FOR_TIME);

        int i = 0;
        for(WeatherForTime wft : weathers) {
            i++;
            Utils.log("%s: %s", i, wft);
        }
    }
}
