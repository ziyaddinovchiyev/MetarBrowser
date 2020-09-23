package com.cinemo.metarbrowser.util;

import androidx.room.TypeConverter;

import java.text.ParseException;
import java.util.Date;

public class DateConverter {

    @TypeConverter
    public String dateToString(Date date) {
        return Utils.df.format(date);
    }

    @TypeConverter
    public Date stringToDate(String date) {
        try {
            return Utils.df.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }
}
