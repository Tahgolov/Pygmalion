package com.vologhat.pygmalion.utils;

import android.graphics.Color;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import androidx.annotation.ColorInt;
import androidx.annotation.FloatRange;
import androidx.annotation.IntRange;
import androidx.annotation.Nullable;

import com.vologhat.pygmalion.annotations.ColorType;
import com.vologhat.pygmalion.annotations.ComplexDimensionUnit;

public class ResourceUtils {

    /**
     * Compares the given value with all possible representations of boolean type
     * and if it's valid returns {@link Boolean} instance otherwise null
     *
     * @param value The given value
     * @return The {@link Boolean} instance if the value is boolean representation otherwise null
     */
    @Nullable
    static public Boolean convertValueToBoolean(@Nullable final CharSequence value) {
        if(value == null) return null;

        if(value.equals("0")
                || value.equals("false")
                || value.equals("FALSE"))
            return false;

        if(value.equals("1")
                || value.equals("true")
                || value.equals("TRUE"))
            return true;

        return null;
    }

    /**
     * Checks if a value is a representation of the integer type
     * and returns {@link Integer} instance if it's true otherwise null
     *
     * @param value The given value
     * @return The {@link Integer} instance if a value is an integer type representation otherwise null
     */
    @Nullable
    static public Integer convertValueToInteger(@Nullable final CharSequence value) {
        //in the android/widget/cts/util/XmlUtils used the copy of code from Integer.decode()
        //but we need to validate integer string representation
        if(value != null)
            try {
                return Integer.decode(value.toString());
            } catch(final NumberFormatException ignored) {
            }
        return null;
    }

    /**
     * Checks if a value is a representation of the float type
     * and returns {@link Float} instance if it's true otherwise null
     *
     * @param value The given value
     * @return The {@link Float} instance if a value is the float type representation otherwise null
     */
    @Nullable
    static public Float convertValueToFLoat(@Nullable final CharSequence value) {
        if(value != null)
            try {
                return Float.parseFloat(value.toString());
            } catch(final NumberFormatException ignored) {
            }
        return null;
    }

    /**
     * <p>Creates a complex data integer that stores a dimension value and units.
     *
     * <p>The resulting value can be passed to e.g.
     * {@link TypedValue#complexToDimensionPixelOffset(int, DisplayMetrics)} to calculate the pixel
     * value for the dimension.
     *
     * @param value the value of the dimension
     * @param units the units of the dimension, e.g. {@link TypedValue#COMPLEX_UNIT_DIP}
     * @return A complex data integer representing the value and units of the dimension.
     */
    public static int createComplexDimension(
            @IntRange(from = -0x800000, to = 0x7FFFFF) int value,
            @ComplexDimensionUnit int units) {
        if(units < TypedValue.COMPLEX_UNIT_PX || units > TypedValue.COMPLEX_UNIT_MM) {
            throw new IllegalArgumentException("Must be a valid COMPLEX_UNIT_*: " + units);
        }
        return intToComplex(value) | units;
    }

    /**
     * <p>Creates a complex data integer that stores a dimension value and units.
     *
     * <p>The resulting value can be passed to e.g.
     * {@link TypedValue#complexToDimensionPixelOffset(int, DisplayMetrics)} to calculate the pixel
     * value for the dimension.
     *
     * @param value the value of the dimension
     * @param units the units of the dimension, e.g. {@link TypedValue#COMPLEX_UNIT_DIP}
     * @return A complex data integer representing the value and units of the dimension.
     */
    static public int createComplexDimension(
            @FloatRange(from = -0x800000, to = 0x7FFFFF) float value,
            @ComplexDimensionUnit int units) {
        if(units < TypedValue.COMPLEX_UNIT_PX || units > TypedValue.COMPLEX_UNIT_MM) {
            throw new IllegalArgumentException("Must be a valid COMPLEX_UNIT_*: " + units);
        }
        return floatToComplex(value) | units;
    }

    /**
     * Convert a base value to a complex data integer.  This sets the {@link
     * TypedValue#COMPLEX_MANTISSA_MASK} and {@link TypedValue#COMPLEX_RADIX_MASK} fields of the
     * data to create a floating point representation of the given value. The units are not set.
     *
     * <p>This is the inverse of {@link TypedValue#complexToFloat(int)}.
     *
     * @param value An integer value.
     * @return A complex data integer representing the value.
     */
    public static int intToComplex(int value) {
        if(value < -0x800000 || value >= 0x800000) {
            throw new IllegalArgumentException("Magnitude of the value is too large: " + value);
        }
        return createComplex(value, TypedValue.COMPLEX_RADIX_23p0);
    }

    /**
     * Convert a base value to a complex data integer.  This sets the {@link
     * TypedValue#COMPLEX_MANTISSA_MASK} and {@link TypedValue#COMPLEX_RADIX_MASK} fields of the
     * data to create a floating point representation of the given value. The units are not set.
     *
     * <p>This is the inverse of {@link TypedValue#complexToFloat(int)}.
     *
     * @param value A floating point value.
     * @return A complex data integer representing the value.
     */
    static public int floatToComplex(@FloatRange(from = -0x800000, to = 0x7FFFFF) float value) {
        // validate that the magnitude fits in this representation
        if(value < (float) -0x800000 - .5f || value >= (float) 0x800000 - .5f) {
            throw new IllegalArgumentException("Magnitude of the value is too large: " + value);
        }
        try {
            // If there's no fraction, use integer representation, as that's clearer
            if(value == (float) (int) value) {
                return createComplex((int) value, TypedValue.COMPLEX_RADIX_23p0);
            }
            float absValue = Math.abs(value);
            // If the magnitude is 0, we don't need any magnitude digits
            if(absValue < 1f) {
                return createComplex(Math.round(value * (1 << 23)), TypedValue.COMPLEX_RADIX_0p23);
            }
            // If the magnitude is less than 2^8, use 8 magnitude digits
            if(absValue < (float) (1 << 8)) {
                return createComplex(Math.round(value * (1 << 15)), TypedValue.COMPLEX_RADIX_8p15);
            }
            // If the magnitude is less than 2^16, use 16 magnitude digits
            if(absValue < (float) (1 << 16)) {
                return createComplex(Math.round(value * (1 << 7)), TypedValue.COMPLEX_RADIX_16p7);
            }
            // The magnitude requires all 23 digits
            return createComplex(Math.round(value), TypedValue.COMPLEX_RADIX_23p0);
        } catch(IllegalArgumentException ex) {
            // Wrap exception so as to include the value argument in the message.
            throw new IllegalArgumentException("Unable to convert value to complex: " + value, ex);
        }
    }

    /**
     * Construct a complex data integer.  This validates the radix and the magnitude of the
     * mantissa, and sets the {@link TypedValue#COMPLEX_MANTISSA_MASK} and
     * {@link TypedValue#COMPLEX_RADIX_MASK} components as provided. The units are not set.
     * *
     *
     * @param mantissa an integer representing the mantissa.
     * @param radix    a radix option, e.g. {@link TypedValue#COMPLEX_RADIX_23p0}.
     * @return A complex data integer representing the value.
     */
    static private int createComplex(@IntRange(from = -0x800000, to = 0x7FFFFF) int mantissa,
                                     int radix) {
        if(mantissa < -0x800000 || mantissa >= 0x800000) {
            throw new IllegalArgumentException("Magnitude of mantissa is too large: " + mantissa);
        }
        if(radix < TypedValue.COMPLEX_RADIX_23p0 || radix > TypedValue.COMPLEX_RADIX_0p23) {
            throw new IllegalArgumentException("Invalid radix: " + radix);
        }
        return ((mantissa & TypedValue.COMPLEX_MANTISSA_MASK) << TypedValue.COMPLEX_MANTISSA_SHIFT)
                | (radix << TypedValue.COMPLEX_RADIX_SHIFT);
    }

    /**
     * Determines a <var>color</var> type
     */
    @ColorType
    static public int getColorType(@ColorInt final int color) {
        final int alpha = Color.alpha(color);
        if(alpha == 0xFF)
            return TypedValue.TYPE_INT_COLOR_ARGB8;
        else if(alpha >= 0xF)
            return TypedValue.TYPE_INT_COLOR_RGB8;
        else if(alpha >= 0x4)
            return TypedValue.TYPE_INT_COLOR_ARGB4;
        else
            return TypedValue.TYPE_INT_COLOR_RGB4;
    }
}
