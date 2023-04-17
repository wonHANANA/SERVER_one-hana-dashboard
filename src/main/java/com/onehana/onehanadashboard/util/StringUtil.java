package com.onehana.onehanadashboard.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class StringUtil {
    public static boolean isValidDateFormat(String dateStr) {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        df.setLenient(false);
        try {
            df.parse(dateStr);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}
