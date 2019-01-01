package com.exampleAPI.zooKeeperAPI.support;


import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

@Component
public class TimeFormat {
    public static String currentToDate(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy:MM:dd:hh");
        return dateFormat.format(System.currentTimeMillis()).replace(":","");
    }
}
