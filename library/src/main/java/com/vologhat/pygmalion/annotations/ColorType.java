package com.vologhat.pygmalion.annotations;

import android.annotation.SuppressLint;
import android.util.TypedValue;

import androidx.annotation.IntDef;

@SuppressLint("UniqueConstants")
@IntDef(value = {
        TypedValue.TYPE_INT_COLOR_RGB4,
        TypedValue.TYPE_INT_COLOR_ARGB4,
        TypedValue.TYPE_INT_COLOR_RGB8,
        TypedValue.TYPE_INT_COLOR_ARGB8,
        //for logical completeness
        TypedValue.TYPE_FIRST_COLOR_INT,
        TypedValue.TYPE_LAST_COLOR_INT,
})
public @interface ColorType { }