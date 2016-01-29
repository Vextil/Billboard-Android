package io.vextil.billboard.ui

import android.content.Context
import android.util.AttributeSet
import io.vextil.billboard.ui.FontIconView

import io.vextil.billboard.R

class IndicatorView : FontIconView {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    fun setState(opened: Boolean) {
        if (opened) {
            setText(R.string.fi_minus_round)
        } else {
            setText(R.string.fi_plus_round)
        }
    }

}
