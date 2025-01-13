package com.example.ui;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    public static String formatTimestamp(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return sdf.format(new Date(timestamp));
    }
}