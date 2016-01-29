package io.vextil.billboard.ui

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.widget.TextView

open class FontIconView : TextView {

    var tface: Typeface? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        if (tface == null) {
            tface = Typeface.createFromAsset(context.assets, "fonts/icons.ttf")
        }
        typeface = tface
    }

}
