package io.vextil.billboard.ui

import android.content.Context
import android.util.AttributeSet
import io.vextil.billboard.ui.FontIconView

import io.vextil.billboard.R

class SmileyRatingView : FontIconView {

    private val smiles = intArrayOf(
            R.string.fi_smile_very_sad,
            R.string.fi_smile_sad,
            R.string.fi_smile_normal,
            R.string.fi_smile_happy,
            R.string.fi_smile_very_happy,
            R.string.fi_smile_super_happy
    )

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    fun setRating(rating: Int?) {
        val calculated = rating!! * 5 / 100
        setText(smiles[calculated])
    }

}


