package com.mgsofttech.ddmods.helperClass;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.util.Patterns;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import com.google.firebase.messaging.Constants;
import com.mgsofttech.ddmods.ActivityConfig;
import com.mgsofttech.ddmods.model.ModelUser;

import java.io.File;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;

public class HelperCode {
    public static String limitDecimalString(String str) {
        return new DecimalFormat("#").format(Double.parseDouble(str));
    }

    public static String limitTwoDecimalString(String str) {
        if (str.contains(".")) {
            return new DecimalFormat("#.##").format(Double.parseDouble(str));
        }
        return limitDecimalString(str);
    }

    public static void setFontPrice(Context context, TextView textView) {
        textView.setTypeface(Typeface.createFromAsset(context.getAssets(), "font/customfont2.ttf"));
    }

    public static Date getDateToday() {
        return Calendar.getInstance().getTime();
    }

    public static Date getDateXDaysBeforeToday(int i) {
        Calendar instance = Calendar.getInstance();
        instance.add(6, -i);
        return instance.getTime();
    }

    public static Date getDateXDaysAfterToday(int i) {
        Calendar instance = Calendar.getInstance();
        instance.add(6, i);
        return instance.getTime();
    }

    public static String getListCount(ArrayList<?> arrayList) {
        return arrayList == null ? "0" : String.valueOf(arrayList.size());
    }

    public static String capitalizeFirstLetter(String str) {
        try {
            return str.substring(0, 1).toUpperCase() + str.substring(1);
        } catch (NullPointerException unused) {
            return str;
        }
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        View currentFocus = activity.getCurrentFocus();
        if (currentFocus == null) {
            currentFocus = new View(activity);
        }
        inputMethodManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
    }

    public static boolean isValidEmail(String str) {
        return Patterns.EMAIL_ADDRESS.matcher(str).matches();
    }

    public static void openWebpage(Context context, String str) {
        context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(str)));
    }

    public static ArrayList<String> setupSearchTagsChar(String str) {
        String lowerCase = str.trim().toLowerCase();
        ArrayList<String> arrayList = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < lowerCase.length(); i++) {
            String valueOf = String.valueOf(lowerCase.charAt(i));
            arrayList.add(sb + valueOf);
            sb.append(valueOf);
        }
        StringBuilder sb2 = new StringBuilder();
        for (int i2 = 0; i2 < lowerCase.length(); i2++) {
            String valueOf2 = String.valueOf(lowerCase.charAt(i2));
            if (!valueOf2.equals(StringUtils.SPACE)) {
                arrayList.add(sb2 + valueOf2);
                sb2.append(valueOf2);
            }
        }
        arrayList.removeAll(Collections.singleton((Object) null));
        arrayList.removeAll(Collections.singleton(""));
        return arrayList;
    }

    public static ArrayList<String> setupSearchTags(String str) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        StringWriter stringWriter = new StringWriter();
        String[] split = str.trim().split(StringUtils.SPACE);
        for (int i = 0; i < split.length; i++) {
            if (i == 0) {
                stringWriter.append(split[i]);
            } else {
                stringWriter.append(StringUtils.SPACE).append(split[i]);
            }
            arrayList.add(stringWriter.toString());
        }
        StringBuilder sb = new StringBuilder();
        char[] charArray = str.toCharArray();
        int length = charArray.length;
        Character[] chArr = new Character[length];
        int i2 = 0;
        int i3 = 0;
        while (i2 < charArray.length) {
            chArr[i2] = Character.valueOf(charArray[i3]);
            i2++;
            i3++;
        }
        for (int i4 = 0; i4 < length; i4++) {
            sb.append(chArr[i4].toString());
            arrayList.add(sb.toString());
        }
        arrayList.addAll(Arrays.asList(str.trim().toLowerCase().replaceAll("[^0-9a-zA-Z]+", "uvxyza").trim().split("uvxyza")));
        ArrayList arrayList3 = new ArrayList(arrayList);
        HashSet hashSet = new HashSet(arrayList3);
        arrayList3.clear();
        arrayList3.addAll(hashSet);
        Iterator it = arrayList3.iterator();
        while (it.hasNext()) {
            String str2 = (String) it.next();
            if (!str2.isEmpty() && !str2.equals(StringUtils.SPACE)) {
                arrayList.add(str2);
            }
        }
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            String str3 = (String) it2.next();
            if (str3.trim().length() > 1) {
                arrayList2.add(str3.trim());
            }
        }
        HashSet hashSet2 = new HashSet(arrayList2);
        arrayList2.clear();
        arrayList2.addAll(hashSet2);
        ArrayList<String> arrayList4 = new ArrayList<>();
        Iterator it3 = arrayList2.iterator();
        while (it3.hasNext()) {
            String str4 = (String) it3.next();
            if (str4.length() < 30) {
                arrayList4.add(str4.trim().toLowerCase());
            }
        }
        arrayList4.removeAll(Collections.singleton((Object) null));
        arrayList4.removeAll(Collections.singleton(""));
        return arrayList4;
    }

    public static void animateViewBubbleA(View view) {
        view.setVisibility(View.VISIBLE);
        ScaleAnimation scaleAnimation = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, 1, 0.5f, 1, 0.5f);
        scaleAnimation.setDuration(500);
        scaleAnimation.setInterpolator(new OvershootInterpolator());
        view.startAnimation(scaleAnimation);
    }

    public static void animateViewBubbleB(View view) {
        view.setVisibility(View.VISIBLE);
        ScaleAnimation scaleAnimation = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, 1, 0.5f, 1, 0.5f);
        scaleAnimation.setDuration(1000);
        scaleAnimation.setInterpolator(new OvershootInterpolator());
        view.startAnimation(scaleAnimation);
    }

    public static Bitmap getBitmapFromView(View view) {
        Bitmap createBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Drawable background = view.getBackground();
        if (background != null) {
            background.draw(canvas);
        } else {
            canvas.drawColor(-1);
        }
        view.draw(canvas);
        return createBitmap;
    }

    public static boolean isValidYouTubeVideo(String str) {
        return Pattern.compile("^(http(s)?://)?((w){3}.)?youtu(be|.be)?(\\.com)?/.+").matcher(str).matches();
    }

    public static String getYouTubeId(String str) {
        if (str == null || str.length() < 6) {
            return "";
        }
        Matcher matcher = Pattern.compile("(?<=youtu.be/|watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*").matcher(str);
        return matcher.find() ? matcher.group() : Constants.IPC_BUNDLE_KEY_SEND_ERROR;
    }

    public static String getFileNameFromURL(String str) {
        if (str == null) {
            return "";
        }
        try {
            String host = new URL(str).getHost();
            if (host.length() > 0 && str.endsWith(host)) {
                return "";
            }
            int lastIndexOf = str.lastIndexOf(47) + 1;
            int length = str.length();
            int lastIndexOf2 = str.lastIndexOf(63);
            if (lastIndexOf2 == -1) {
                lastIndexOf2 = length;
            }
            int lastIndexOf3 = str.lastIndexOf(35);
            if (lastIndexOf3 != -1) {
                length = lastIndexOf3;
            }
            return str.substring(lastIndexOf, Math.min(lastIndexOf2, length));
        } catch (MalformedURLException unused) {
            return "";
        }
    }

    public static boolean isProductPurchased(String str, ModelUser modelUser, ArrayList<String> arrayList) {
        if (ActivityConfig.tempOrderProductList != null && ActivityConfig.tempOrderProductList.contains(str)) {
            return true;
        }
        if (arrayList == null || !arrayList.contains(modelUser.getUserID())) {
            return false;
        }
        return true;
    }

    public static String getBytesToMBString(long j) {
        return String.format(Locale.ENGLISH, "%.2fMb", new Object[]{Double.valueOf(((double) j) / 1048576.0d)});
    }

    public static String getYouTubeThumbnail(String str) {
        return "https://i3.ytimg.com/vi/" + getYouTubeId(str) + "/maxresdefault.jpg";
    }

    public static void createFolderDownloads() {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + File.separator + "KBS");
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public static String prettyNumber(Number number) {
        char[] cArr = {' ', 'k', 'M', 'B', 'T', 'P', 'E'};
        long longValue = number.longValue();
        double d = (double) longValue;
        int floor = (int) Math.floor(Math.log10(d));
        int i = floor / 3;
        if (floor < 3 || i >= 7) {
            return new DecimalFormat("#,##0").format(longValue);
        }
        return new DecimalFormat("#0.0").format(d / Math.pow(10.0d, (double) (i * 3))) + cArr[i];
    }

    public static String getFolderDownloads() {
        createFolderDownloads();
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + File.separator + "KBS";
    }
}
