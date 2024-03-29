package com.vologhat.pygmalion.annotations;

import static android.util.TypedValue.COMPLEX_UNIT_DIP;
import static android.util.TypedValue.COMPLEX_UNIT_IN;
import static android.util.TypedValue.COMPLEX_UNIT_MM;
import static android.util.TypedValue.COMPLEX_UNIT_PT;
import static android.util.TypedValue.COMPLEX_UNIT_PX;
import static android.util.TypedValue.COMPLEX_UNIT_SP;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef(value = {
        COMPLEX_UNIT_PX,
        COMPLEX_UNIT_DIP,
        COMPLEX_UNIT_SP,
        COMPLEX_UNIT_PT,
        COMPLEX_UNIT_IN,
        COMPLEX_UNIT_MM,
})
@Retention(RetentionPolicy.SOURCE)
public @interface ComplexDimensionUnit { }