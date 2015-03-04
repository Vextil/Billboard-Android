package com.siercuit.cartelera.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.siercuit.cartelera.App;

public class FontIconView extends TextView
{

    public FontIconView(Context context) {
        super(context);
    }

    public FontIconView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        setTypeface(App.getIconsTypeface());
    }

}
