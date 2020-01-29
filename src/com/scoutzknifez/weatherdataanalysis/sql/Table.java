package com.scoutzknifez.weatherdataanalysis.sql;

import com.scoutzknifez.weatherdataanalysis.sql.insertion.Databasable;
import com.scoutzknifez.weatherdataanalysis.structures.dtos.WeatherForTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The enumerators in this class file MUST be named the same as
 * the table names inside of the database.
 *
 * The {@link Class} parameter for each parameter must implement {@link Databasable}
 * and must have a static method called "createInstance" with a ResultSet as a parameter.
 */
@Getter
@AllArgsConstructor
public enum Table {
    WEATHER_FOR_TIME(WeatherForTime.class);

    private Class constructorClass;
}
