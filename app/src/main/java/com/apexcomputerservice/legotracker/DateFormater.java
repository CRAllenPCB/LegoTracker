package com.apexcomputerservice.legotracker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Chris on 4/22/2017.
 */

public class DateFormater {

    SimpleDateFormat sdf = new SimpleDateFormat("MMMM d, yyyy", Locale.US);
    String textConversion;


    public String ConvertToText(Calendar c){
        textConversion = sdf.format(c.getTime());

        return textConversion;
    }





}
