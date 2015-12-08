package com.siercuit.cartelera.views;

import android.content.Context;
import android.util.AttributeSet;

import io.vextil.billboard.R;

public class SmileyRatingView extends FontIconView
{

    private static final int[] smiles = {
            R.string.fi_smile_very_sad,
            R.string.fi_smile_sad,
            R.string.fi_smile_normal,
            R.string.fi_smile_happy,
            R.string.fi_smile_very_happy,
            R.string.fi_smile_super_happy
    };

    public SmileyRatingView(Context context) {
        super(context);
    }

    public SmileyRatingView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public void setRating(Integer rating)
    {
        Integer calculated = ((rating * 5) / 100);
        setText(smiles[calculated]);
    }

}


