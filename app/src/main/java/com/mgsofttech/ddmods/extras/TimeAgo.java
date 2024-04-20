package com.mgsofttech.ddmods.extras;

import android.content.Context;
import android.util.Log;

import com.mgsofttech.ddmods.R;

import java.util.Calendar;
import java.util.Date;
import org.apache.commons.lang3.StringUtils;

public class TimeAgo {
    protected Context context;

    public TimeAgo(Context context2) {
        this.context = context2;
    }

    public static Date currentDate() {
        return Calendar.getInstance().getTime();
    }

    public static String getTimeAgoShort(Date date, Context context2) {
        String str;
        if (date == null) {
            return null;
        }
        long time = date.getTime();
        if (time > currentDate().getTime() || time <= 0) {
            return null;
        }
        int timeDistanceInMinutes = getTimeDistanceInMinutes(time);
        if (timeDistanceInMinutes == 0) {
            str = context2.getResources().getString(R.string.date_short_util_term_a) + StringUtils.SPACE + context2.getResources().getString(R.string.date_short_util_unit_minute);
        } else if (timeDistanceInMinutes == 1) {
            return "1 " + context2.getResources().getString(R.string.date_short_util_unit_minute) + StringUtils.SPACE + context2.getResources().getString(R.string.date_short_util_suffix);
        } else if (timeDistanceInMinutes >= 2 && timeDistanceInMinutes <= 44) {
            str = timeDistanceInMinutes + StringUtils.SPACE + context2.getResources().getString(R.string.date_short_util_unit_minutes);
        } else if (timeDistanceInMinutes >= 45 && timeDistanceInMinutes <= 89) {
            str = "" + context2.getResources().getString(R.string.date_short_util_term_an) + StringUtils.SPACE + context2.getResources().getString(R.string.date_short_util_unit_hour);
        } else if (timeDistanceInMinutes >= 90 && timeDistanceInMinutes <= 1439) {
            str = "" + Math.round((float) (timeDistanceInMinutes / 60)) + StringUtils.SPACE + context2.getResources().getString(R.string.date_short_util_unit_hours);
        } else if (timeDistanceInMinutes >= 1440 && timeDistanceInMinutes <= 2519) {
            str = "1 " + context2.getResources().getString(R.string.date_short_util_unit_day);
        } else if (timeDistanceInMinutes >= 2520 && timeDistanceInMinutes <= 43199) {
            str = Math.round((float) (timeDistanceInMinutes / 1440)) + StringUtils.SPACE + context2.getResources().getString(R.string.date_short_util_unit_days);
        } else if (timeDistanceInMinutes >= 43200 && timeDistanceInMinutes <= 86399) {
            str = "" + context2.getResources().getString(R.string.date_short_util_term_a) + StringUtils.SPACE + context2.getResources().getString(R.string.date_short_util_unit_month);
        } else if (timeDistanceInMinutes >= 86400 && timeDistanceInMinutes <= 525599) {
            str = Math.round((float) (timeDistanceInMinutes / 43200)) + StringUtils.SPACE + context2.getResources().getString(R.string.date_short_util_unit_months);
        } else if (timeDistanceInMinutes >= 525600 && timeDistanceInMinutes <= 655199) {
            str = "" + context2.getResources().getString(R.string.date_short_util_term_a) + StringUtils.SPACE + context2.getResources().getString(R.string.date_short_util_unit_year);
        } else if (timeDistanceInMinutes >= 655200 && timeDistanceInMinutes <= 914399) {
            str = "" + context2.getResources().getString(R.string.date_short_util_term_a) + StringUtils.SPACE + context2.getResources().getString(R.string.date_short_util_unit_year);
        } else if (timeDistanceInMinutes < 914400 || timeDistanceInMinutes > 1051199) {
            str = "" + Math.round((float) (timeDistanceInMinutes / 525600)) + StringUtils.SPACE + context2.getResources().getString(R.string.date_short_util_unit_years);
        } else {
            str = "2 " + context2.getResources().getString(R.string.date_short_util_unit_years);
        }
        return str + StringUtils.SPACE + context2.getResources().getString(R.string.date_short_util_suffix);
    }

    public static String getTimeAgo(Date date, Context context2) {
        String str;
        if (date == null) {
            Log.d("logTimeData", "Null A");
            return null;
        }
        int timeDistanceInMinutes = getTimeDistanceInMinutes(date.getTime());
        if (timeDistanceInMinutes == 0) {
            Log.d("logTimeData", "Case A - " + timeDistanceInMinutes);
            str = context2.getResources().getString(R.string.date_util_term_a) + " moment";
        } else if (timeDistanceInMinutes == 1) {
            Log.d("logTimeData", "Case B - " + timeDistanceInMinutes);
            return "1 " + context2.getResources().getString(R.string.date_util_unit_minute) + StringUtils.SPACE + context2.getResources().getString(R.string.date_util_suffix);
        } else if (timeDistanceInMinutes >= 2 && timeDistanceInMinutes <= 44) {
            str = timeDistanceInMinutes + StringUtils.SPACE + context2.getResources().getString(R.string.date_util_unit_minutes);
        } else if (timeDistanceInMinutes >= 45 && timeDistanceInMinutes <= 89) {
            str = "" + context2.getResources().getString(R.string.date_util_term_an) + StringUtils.SPACE + context2.getResources().getString(R.string.date_util_unit_hour);
        } else if (timeDistanceInMinutes >= 90 && timeDistanceInMinutes <= 1439) {
            str = "" + Math.round((float) (timeDistanceInMinutes / 60)) + StringUtils.SPACE + context2.getResources().getString(R.string.date_util_unit_hours);
        } else if (timeDistanceInMinutes >= 1440 && timeDistanceInMinutes <= 2519) {
            str = "1 " + context2.getResources().getString(R.string.date_util_unit_day);
        } else if (timeDistanceInMinutes >= 2520 && timeDistanceInMinutes <= 43199) {
            str = Math.round((float) (timeDistanceInMinutes / 1440)) + StringUtils.SPACE + context2.getResources().getString(R.string.date_util_unit_days);
        } else if (timeDistanceInMinutes >= 43200 && timeDistanceInMinutes <= 86399) {
            str = "" + context2.getResources().getString(R.string.date_util_term_a) + StringUtils.SPACE + context2.getResources().getString(R.string.date_util_unit_month);
        } else if (timeDistanceInMinutes >= 86400 && timeDistanceInMinutes <= 525599) {
            str = Math.round((float) (timeDistanceInMinutes / 43200)) + StringUtils.SPACE + context2.getResources().getString(R.string.date_util_unit_months);
        } else if (timeDistanceInMinutes >= 525600 && timeDistanceInMinutes <= 655199) {
            str = "" + context2.getResources().getString(R.string.date_util_term_a) + StringUtils.SPACE + context2.getResources().getString(R.string.date_util_unit_year);
        } else if (timeDistanceInMinutes >= 655200 && timeDistanceInMinutes <= 914399) {
            str = "" + context2.getResources().getString(R.string.date_util_term_a) + StringUtils.SPACE + context2.getResources().getString(R.string.date_util_unit_year);
        } else if (timeDistanceInMinutes < 914400 || timeDistanceInMinutes > 1051199) {
            str = "" + Math.round((float) (timeDistanceInMinutes / 525600)) + StringUtils.SPACE + context2.getResources().getString(R.string.date_util_unit_years);
        } else {
            str = "2 " + context2.getResources().getString(R.string.date_util_unit_years);
        }
        return str + StringUtils.SPACE + context2.getResources().getString(R.string.date_util_suffix);
    }

    private static int getTimeDistanceInMinutes(long j) {
        return Math.round((float) ((Math.abs(currentDate().getTime() - j) / 1000) / 60));
    }
}
