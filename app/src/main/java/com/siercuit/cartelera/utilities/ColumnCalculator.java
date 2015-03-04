package com.siercuit.cartelera.utilities;

import android.app.Activity;
import android.util.DisplayMetrics;

public class ColumnCalculator
{

    public static Integer get(Activity activity)
    {
        DisplayMetrics displayMetrics = activity.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        Integer columns;
        if (dpWidth >= 900) {
            columns = 3;
        } else if(dpWidth >= 550) {
            columns = 2;
        } else {
            columns = 1;
        }
        return columns;
    }

    public static Boolean isTabletHeight(Activity activity)
    {
        DisplayMetrics displayMetrics = activity.getResources().getDisplayMetrics();
        float dpHeight = displayMetrics.heightPixels / displayMetrics.density;
        Integer columns;
        if(dpHeight >= 550) {
            return true;
        } else {
            return false;
        }
    }
}
