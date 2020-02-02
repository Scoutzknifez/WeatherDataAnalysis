package com.scoutzknifez.weatherdataanalysis.utility.structures;

import com.scoutzknifez.weatherdataanalysis.utility.Constants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
public class TimeAtMoment {
    private long millis;
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private int second;

    public TimeAtMoment(long time) {
        setMillis(time);
        refreshTime();
    }

    public void addTime(long time) {
        setMillis(getMillis() + time);
        refreshTime();
    }

    public int hoursBetween(TimeAtMoment time) {
        return (int) (Math.abs(getMillis() - time.getMillis()) / Constants.MILLIS_IN_HOUR);
    }

    public int minutesBetween(TimeAtMoment time) {
        return (int) (Math.abs(getMillis() - time.getMillis())/ Constants.MILLIS_IN_MINUTE);
    }

    public void refreshTime() {
        Date date = new Date(getMillis());
        DateFormat format = new SimpleDateFormat("yyyy:MM:dd:HH:mm:ss");
        String formatted = format.format(date);
        String[] splits = formatted.split(":");
        try {
            year = Integer.parseInt(splits[0]);
            month = Integer.parseInt(splits[1]);
            day = Integer.parseInt(splits[2]);
            hour = Integer.parseInt(splits[3]);
            minute = Integer.parseInt(splits[4]);
            second = Integer.parseInt(splits[5]);
        } catch (Exception e) {
            System.out.println("Can not properly create TimeAtMoment object.");
        }
    }

    public String dateString() {
        return getMonth() + "/" + getDay() + "/" + getYear();
    }

    public String timeString() {
        StringBuilder sb = new StringBuilder();
        String section;

        if(getHour() > 12) {
            section = (getHour() - 12) + ":";
        } else if (getHour() == 0) {
            section = "12:";
        } else {
            section = (getHour()) + ":";
        }
        sb.append(section);

        if(getMinute() < 10) {
            section = "0" + getMinute() + ":";
        } else {
            section = "" + getMinute() + ":";
        }
        sb.append(section);

        if(getSecond() < 10) {
            section = "0" + getSecond();
        } else {
            section = "" + getSecond();
        }
        sb.append(section);

        if(getHour() > 12) {
            sb.append(" PM");
        } else {
            sb.append(" AM");
        }

        return sb.toString();
    }

    @Override
    public String toString() {
        return dateString() + " " + timeString();
    }
}
