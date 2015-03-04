package com.siercuit.cartelera.views;

import android.content.Context;
import android.util.AttributeSet;

import com.siercuit.cartelera.R;

public class IndicatorView extends FontIconView
{
    public IndicatorView(Context context) {
        super(context);
    }

    public IndicatorView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public void setState(Boolean opened)
    {
        if (opened) {
            setText(R.string.fi_minus_round);
        } else {
            setText(R.string.fi_plus_round);
        }
    }

}
