package io.vextil.billboard.ui

import android.content.Context
import android.graphics.Typeface

enum class Icon {

    BABY, EARTH, CALENDAR, CAMERA, CLOCK, HOUSE,
    PIN, TRASH, SUPPORT, SIGN, EYE, SETTINGS, MOVIE, MONEY,
    CREDITCARD, BUG, SMILE_VERY_SAD, SMILE_SAD, SMILE_SUPER_SAD,
    SMILE_HAPPY, SMILE_VERY_HAPPY, SMILE_SUPER_HAPPY, SMILE_NORMAL,
    THUMB_UP, THUMB_DOWN, STAR, OK, DELETE, PLUS, PLUS_ROUND, DELETE_ROUND, PIG,
    ARROW_RIGHT_ROUND, ARROW_LEFT_ROUND, MINUS, SYNC, MINUS_ROUND, USER,
    FACEBOOK, TWITTER, YOUTUBE, WARNING, COMMENT, HEART, SEARCH,
    KEY, LIGHTBULB, OK_ROUND, ESP,  MASKS, TICKET, FOOD, GIFT, SUB, TAG, BOMB,
    CUP, GHOST, DOWNLOAD, MAIL, MESSAGE, MARKET, PLAY, USERS, CART, THREED;

    companion object {
        var typeface: Typeface? = null
    }

    fun getTypeface(context: Context): Typeface {
        if (typeface == null) {
            typeface = Typeface.createFromAsset(context.assets, "fonts/icons.ttf")
        }
        return typeface!!
    }

    fun getCharacter(enum: Enum<Icon>): String {
        when (enum) {
            BABY -> return "a"
            EARTH -> return "b"
            CALENDAR -> return "c"
            CAMERA -> return "d"
            CLOCK -> return "e"
            HOUSE -> return "f"
            PIN -> return "g"
            TRASH -> return "h"
            SUPPORT -> return "i"
            SIGN -> return "j"
            EYE -> return "k"
            SETTINGS -> return "l"
            MOVIE -> return "m"
            MONEY -> return "n"
            CREDITCARD -> return "o"
            BUG -> return "p"
            SMILE_VERY_SAD -> return "q"
            SMILE_SAD -> return "r"
            SMILE_SUPER_SAD -> return "s"
            SMILE_HAPPY -> return "t"
            SMILE_VERY_HAPPY -> return "u"
            SMILE_SUPER_HAPPY -> return "v"
            SMILE_NORMAL -> return "w"
            THUMB_UP -> return "x"
            THUMB_DOWN -> return "y"
            STAR -> return "z"
            OK -> return "A"
            DELETE -> return "B"
            PLUS -> return "C"
            DELETE_ROUND -> return "D"
            PLUS_ROUND -> return "E"
            PIG -> return "F"
            ARROW_RIGHT_ROUND -> return "G"
            ARROW_LEFT_ROUND -> return "H"
            MINUS -> return "I"
            SYNC -> return "J"
            MINUS_ROUND -> return "K"
            USER -> return "L"
            FACEBOOK -> return "M"
            TWITTER -> return "N"
            YOUTUBE -> return "O"
            WARNING -> return "P"
            COMMENT -> return "Q"
            HEART -> return "R"
            SEARCH -> return "S"
            KEY -> return "T"
            LIGHTBULB -> return "U"
            OK_ROUND -> return "V"
            ESP -> return "W"
            THREED -> return "X"
            MASKS -> return "Y"
            TICKET -> return "Z"
            FOOD -> return "0"
            GIFT -> return "1"
            SUB -> return "2"
            TAG -> return "3"
            BOMB -> return "4"
            CUP -> return "5"
            GHOST -> return "6"
            DOWNLOAD -> return "7"
            MAIL -> return "8"
            MESSAGE -> return "9"
            MARKET -> return "!"
            PLAY -> return "\\"
            USERS -> return "#"
            CART -> return "$"
        }
        return ""
    }
}