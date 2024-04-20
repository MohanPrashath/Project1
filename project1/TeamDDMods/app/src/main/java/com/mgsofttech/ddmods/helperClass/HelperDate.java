package com.mgsofttech.ddmods.helperClass;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HelperDate {
    public static final String[] FEEDRAW_DATETIME_FORMAT = {"yyyy-MM-dd'T'HH:mm:ssZ", "d M yyyy HH:mm:ss Z", "d MMM yyyy HH:mm:ss Z", "EEE, d M yyyy HH:mm:ss Z", "EEE, d MMM yyyy HH:mm:ss Z"};
    public static final String[] germanMonth = {"Jan", "Feb", "Mär", "Apr", "Mai", "Jun", "Jul", "Aug", "Sep", "Okt", "Nov", "Dez"};

    public static Date getFirebaseTimestamp(String str) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(str);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
