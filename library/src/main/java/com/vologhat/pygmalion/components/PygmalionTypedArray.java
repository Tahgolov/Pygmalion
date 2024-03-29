package com.vologhat.pygmalion.components;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.TypedValue;

import androidx.annotation.AnyRes;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.Dimension;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.StyleableRes;

import com.vologhat.pygmalion.annotations.ComplexDimensionUnit;
import com.vologhat.pygmalion.utils.AndroidVersioning;
import com.vologhat.pygmalion.utils.ReflectionUtils;
import com.vologhat.pygmalion.utils.ResourceUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

public class PygmalionTypedArray {
    final static private Field sDataFld;
    final static private Field sIndicesFld;
    final static private Method getValueAtMtd;
    final static private Method loadStringValueAtMtd;

    final private TypedValue mTmpValue = new TypedValue();
    final private TypedArray mTypedArray;
    final private Resources mOrigResources;

    private int[] mData, mIndices;

    static {
        final Class<?> taClz = TypedArray.class;
        try {
            sDataFld = ReflectionUtils.getField(taClz, "mData");
            sIndicesFld = ReflectionUtils.getField(taClz, "mIndices");
            getValueAtMtd = ReflectionUtils.getMethod(taClz, "getValueAt", int.class, TypedValue.class);
            loadStringValueAtMtd = ReflectionUtils.getMethod(taClz, "loadStringValueAt", int.class);
        } catch(final Throwable cause) {
            throw new RuntimeException(cause);
        }
    }

    public PygmalionTypedArray(final TypedArray ta) {
        mTypedArray = ta;

        Resources res = ta.getResources();
        if(res instanceof PygmalionResources)
            res = ((PygmalionResources) res).getOriginalResources();
        mOrigResources = res;

        try {
            mData = (int[]) sDataFld.get(ta);
            mIndices = (int[]) sIndicesFld.get(ta);
        } catch(final Throwable cause) {
            throw new RuntimeException(cause);
        }
    }

    /**
     * Hack to check if {@link TypedArray} has been already recycled bypassing encapsulation without using reflection
     */
    public boolean isRecycled() {
        try {
            mTypedArray.length();
            return false;
        } catch(final RuntimeException e) {
            return true;
        }
    }

    /**
     * Returns a wrapped {@link TypedArray} instance
     */
    public TypedArray getTypedArray() {
        return mTypedArray;
    }

    /**
     * Returns a data buffer for some sort of manipulations
     */
    public int[] getDataBuffer() {
        return mData;
    }

    /**
     * Returns a buffer with indices of data buffer
     */
    public int[] getIndicesBuffer() {
        return mIndices;
    }

    /**
     * Returns the original {@link Resources} instance.
     * If Pygmalion wasn't loaded, {@link #getResources()} will gives the same result
     */
    public Resources getOriginalResources() {
        return mOrigResources;
    }

    /**
     * @see TypedArray#length()
     */
    public int length() {
        return mTypedArray.length();
    }

    /**
     * @see TypedArray#getIndexCount()
     */
    public int getIndexCount() {
        return mTypedArray.getIndexCount();
    }

    /**
     * @see TypedArray#getIndex(int)
     */
    public int getIndex(final int at) {
        return mTypedArray.getIndex(at);
    }

    /**
     * @see #getOriginalResources()
     * @see TypedArray#getResources()
     */
    public Resources getResources() {
        return mTypedArray.getResources();
    }

    /**
     * @see TypedArray#getText(int)
     */
    public CharSequence getText(@StyleableRes final int index) {
        return mTypedArray.getText(index);
    }

    /**
     * @see TypedArray#getString(int)
     */
    @Nullable
    public String getString(@StyleableRes final int index) {
        return mTypedArray.getString(index);
    }

    /**
     * @see TypedArray#getNonResourceString(int)
     */
    public String getNonResourceString(@StyleableRes final int index) {
        return mTypedArray.getNonResourceString(index);
    }

    /**
     * @see TypedArray#getBoolean(int, boolean)
     */
    public boolean getBoolean(@StyleableRes int index, final boolean defValue) {
        return mTypedArray.getBoolean(index, defValue);
    }

    /**
     * @see TypedArray#getInt(int, int)
     */
    public int getInt(@StyleableRes final int index, final int defValue) {
        return mTypedArray.getInt(index, defValue);
    }

    /**
     * @see TypedArray#getFloat(int, float)
     */
    public float getFloat(@StyleableRes final int index, final float defValue) {
        return mTypedArray.getFloat(index, defValue);
    }

    /**
     * @see TypedArray#getColor(int, int)
     */
    @ColorInt
    public int getColor(@StyleableRes final int index, @ColorInt final int defValue) {
        return mTypedArray.getColor(index, defValue);
    }

    /**
     * @see TypedArray#getColorStateList(int)
     */
    @Nullable
    public ColorStateList getColorStateList(@StyleableRes final int index) {
        return mTypedArray.getColorStateList(index);
    }

    /**
     * @see TypedArray#getInteger(int, int)
     */
    public int getInteger(@StyleableRes final int index, final int defValue) {
        return mTypedArray.getInteger(index, defValue);
    }

    /**
     * @see TypedArray#getDimension(int, float)
     */
    public float getDimension(@StyleableRes final int index, final float defValue) {
        return mTypedArray.getDimension(index, defValue);
    }

    /**
     * @see TypedArray#getDimensionPixelOffset(int, int)
     */
    public int getDimensionPixelOffset(@StyleableRes final int index, final int defValue) {
        return mTypedArray.getDimensionPixelOffset(index, defValue);
    }

    /**
     * @see TypedArray#getDimensionPixelSize(int, int)
     */
    public int getDimensionPixelSize(@StyleableRes final int index, final int defValue) {
        return mTypedArray.getDimensionPixelSize(index, defValue);
    }

    /**
     * @see TypedArray#getLayoutDimension(int, int)
     */
    public int getLayoutDimension(@StyleableRes final int index, final int defValue) {
        return mTypedArray.getLayoutDimension(index, defValue);
    }

    /**
     * @see TypedArray#getLayoutDimension(int, String)
     */
    public int getLayoutDimension(@StyleableRes final int index, final String name) {
        return mTypedArray.getLayoutDimension(index, name);
    }

    /**
     * @see TypedArray#getFraction(int, int, int, float)
     */
    public float getFraction(@StyleableRes final int index, final int base, final int pbase,
                             final float defValue) {
        return mTypedArray.getFraction(index, base, pbase, defValue);
    }

    /**
     * @see TypedArray#getResourceId(int, int)
     */
    @AnyRes
    public int getResourceId(@StyleableRes final int index, final int defValue) {
        return mTypedArray.getResourceId(index, defValue);
    }

    //own implementation
    public int getThemeAttributeId(@StyleableRes int index, final int defValue) {
        index *= Style.STYLE_NUM_ENTRIES;
        final int[] data = mData;
        if(data[index + Style.STYLE_TYPE] == TypedValue.TYPE_ATTRIBUTE) {
            return data[index + Style.STYLE_DATA];
        }
        return defValue;
    }

    /**
     * @see TypedArray#getDrawable(int)
     */
    @Nullable
    public Drawable getDrawable(@StyleableRes final int index) {
        return mTypedArray.getDrawable(index);
    }

    /**
     * @see TypedArray#getFont(int)
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    public Typeface getFont(@StyleableRes final int index) {
        return mTypedArray.getFont(index);
    }

    /**
     * @see TypedArray#getTextArray(int)
     */
    public CharSequence[] getTextArray(@StyleableRes final int index) {
        return mTypedArray.getTextArray(index);
    }

    /**
     * @see TypedArray#getValue(int, TypedValue)
     */
    public boolean getValue(@StyleableRes final int index, final TypedValue outValue) {
        return mTypedArray.getValue(index, outValue);
    }

    /**
     * @see TypedArray#getType(int)
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public int getType(@StyleableRes final int index) {
        return mTypedArray.getType(index);
    }

    /**
     * @see TypedArray#getSourceResourceId(int, int)
     */
    @RequiresApi(api = Build.VERSION_CODES.Q)
    @AnyRes
    public int getSourceResourceId(@StyleableRes final int index, @AnyRes final int defValue) {
        return mTypedArray.getSourceResourceId(index, defValue);
    }

    /**
     * @see TypedArray#hasValue(int)
     */
    public boolean hasValue(@StyleableRes final int index) {
        return mTypedArray.hasValue(index);
    }

    /**
     * @see TypedArray#hasValueOrEmpty(int)
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    public boolean hasValueOrEmpty(@StyleableRes int index) {
        return mTypedArray.hasValueOrEmpty(index);
    }

    /**
     * @see TypedArray#peekValue(int)
     */
    public TypedValue peekValue(@StyleableRes final int index) {
        return mTypedArray.peekValue(index);
    }

    /**
     * @see TypedArray#getPositionDescription()
     */
    public String getPositionDescription() {
        return mTypedArray.getPositionDescription();
    }

    //own implementation

    /**
     * Extracts theme attributes from a typed array for later resolution using
     * {@link android.content.res.Resources.Theme#resolveAttributes(int[], int[])}. Removes the
     * entries from the typed array so that subsequent calls to typed getters will return the default
     * value without crashing.
     *
     * @return an array of length {@link #getIndexCount()} populated with theme attributes, or null if
     * there are no theme attributes in the typed array
     * @throws RuntimeException if the TypedArray has already been recycled.
     */
    @Nullable
    public int[] extractThemeAttrs() {
        return extractThemeAttrs(null);
    }

    //own implementation

    /**
     * @see #extractThemeAttrs()
     */
    @Nullable
    public int[] extractThemeAttrs(@Nullable final int[] scrap) {
        if(isRecycled())
            throw new RuntimeException("Cannot make calls to a recycled instance!");

        int[] attrs = null;

        final int[] data = mData;
        final int N = length();
        for(int i = 0; i < N; ++i) {
            final int index = Style.STYLE_NUM_ENTRIES;
            if(data[index + Style.STYLE_TYPE] != TypedValue.TYPE_ATTRIBUTE)// Not an attribute, ignore.
            {
                continue;
            }

            // Null the entry so that we can safely call getZzz().
            data[index + Style.STYLE_TYPE] = TypedValue.TYPE_NULL;

            final int attr = data[index + Style.STYLE_DATA];
            if(attr == 0)// Useless data, ignore.
            {
                continue;
            }

            // Ensure we have a usable attribute array.
            if(attrs == null) {
                if(scrap != null && scrap.length == N) {
                    attrs = scrap;
                    Arrays.fill(attrs, 0);
                } else {
                    attrs = new int[N];
                }
            }

            attrs[i] = attr;
        }

        return attrs;
    }

    /**
     * @see TypedArray#getChangingConfigurations()
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public int getChangingConfigurations() {
        return mTypedArray.getChangingConfigurations();
    }

    //own implementation

    /**
     * Retrieves the data at the specialized <var>index</var> from the internal data buffer
     * and populates the given <var>outValue</var> with retrieved data
     *
     * @param index    The index which to retrieve the data
     * @param outValue The {@link TypedValue} object for populating with retrieved data
     * @return {@code true} if the data was successfully retrieved and populated into <var>outValue</var> otherwise {@code false}
     * @throws RuntimeException if the TypedArray has already been recycled.
     */
    public boolean getValueAt(@StyleableRes final int index, @NonNull final TypedValue outValue) {
        if(isRecycled())
            throw new RuntimeException("Cannot make calls to a recycled instance!");

        try {
            return (boolean) getValueAtMtd.invoke(mTypedArray, index, outValue);
        } catch(final Throwable ignored) {
            return false;
        }
    }

    //own implementation

    /**
     * Returns the string loaded from the string pool by specified <var>index</var>
     *
     * @param index The index which to retrieve the data
     * @return The specific indexed string
     * @throws RuntimeException if the TypedArray has already been recycled.
     */
    @Nullable
    public CharSequence loadStringValueAt(@StyleableRes final int index) {
        if(isRecycled())
            throw new RuntimeException("Cannot make calls to a recycled instance!");

        try {
            return (CharSequence) loadStringValueAtMtd.invoke(mTypedArray, index);
        } catch(final Throwable ignored) {
            return null;
        }
    }

    /**
     * Sets a new boolean <var>value</var> by specific <var>index</var>
     *
     * @param index The index which to retrieve the data
     * @param value The new value that should be set
     * @return {@code true} if new value has been set otherwise {@code false}
     * @throws RuntimeException if the {@link TypedArray} has already been recycled.
     * @see TypedArray#getBoolean(int, boolean)
     */
    public boolean setBoolean(@StyleableRes int index, final boolean value) {
        if(isRecycled())
            throw new RuntimeException("Cannot make calls to a recycled instance!");

        index *= Style.STYLE_NUM_ENTRIES;

        final int[] data = mData;
        final int type = data[index + Style.STYLE_TYPE];
        if(type == TypedValue.TYPE_NULL) {
            return false;
        } else if(type >= TypedValue.TYPE_FIRST_INT
                && type <= TypedValue.TYPE_LAST_INT) {
            data[index + Style.STYLE_DATA] = Boolean.compare(value, false);
            return true;
        }

        final TypedValue tv = mTmpValue;
        if(getValueAt(index, tv)) {
            final CharSequence strValue = tv.coerceToString();
            final Boolean oldValue = ResourceUtils.convertValueToBoolean(strValue);
            if(oldValue != null)//check if the value is actually a boolean
            {
                data[index + Style.STYLE_DATA] = Boolean.compare(value, false);
                return true;
            }
            return false;
        }

        // We already checked for TYPE_NULL. This should never happen.
        throw new RuntimeException("setBoolean of bad type: 0x" + Integer.toHexString(type));
    }

    /**
     * Sets a new int <var>value</var> by specific <var>index</var>
     *
     * @param index The index which to retrieve the data
     * @param value The new value that should be set
     * @return {@code true} if new value has been set otherwise {@code false}
     * @throws RuntimeException if the {@link TypedArray} has already been recycled.
     * @see TypedArray#getInt(int, int)
     */
    public boolean setInt(@StyleableRes int index, final int value) {
        if(isRecycled())
            throw new RuntimeException("Cannot make calls to a recycled instance!");

        index *= Style.STYLE_NUM_ENTRIES;

        final int[] data = mData;
        final int type = data[index + Style.STYLE_TYPE];
        if(type == TypedValue.TYPE_NULL) {
            return false;
        } else if(type >= TypedValue.TYPE_FIRST_INT
                && type <= TypedValue.TYPE_LAST_INT) {
            data[index + Style.STYLE_DATA] = value;
            return true;
        }

        final TypedValue tv = mTmpValue;
        if(getValueAt(index, tv)) {
            final CharSequence strValue = tv.coerceToString();
            final Integer oldValue = ResourceUtils.convertValueToInteger(strValue);
            if(oldValue != null)//check if the value is actually a int
            {
                data[index + Style.STYLE_DATA] = value;
                return true;
            }
            return false;
        }

        // We already checked for TYPE_NULL. This should never happen.
        throw new RuntimeException("setInt of bad type: 0x" + Integer.toHexString(type));
    }

    /**
     * Sets a new float <var>value</var> by specific <var>index</var>
     *
     * @param index The index which to retrieve the data
     * @param value The new value that should be set
     * @return {@code true} if new value has been set otherwise {@code false}
     * @throws RuntimeException if the {@link TypedArray} has already been recycled.
     * @see TypedArray#getFloat(int, float)
     */
    public boolean setFloat(@StyleableRes int index, final float value) {
        if(isRecycled())
            throw new RuntimeException("Cannot make calls to a recycled instance!");

        index *= Style.STYLE_NUM_ENTRIES;

        final int[] data = mData;
        final int type = data[index + Style.STYLE_TYPE];
        if(type == TypedValue.TYPE_NULL) {
            return false;
        } else if(type == TypedValue.TYPE_FLOAT) {
            data[index + Style.STYLE_DATA] = Float.floatToIntBits(value);
            return true;
        } else if(type >= TypedValue.TYPE_FIRST_INT
                && type <= TypedValue.TYPE_LAST_INT) {
            data[index + Style.STYLE_DATA] = (int) value;
            return true;
        }

        final TypedValue v = mTmpValue;
        if(getValueAt(index, v)) {
            final CharSequence strValue = v.coerceToString();
            final Float oldValue = ResourceUtils.convertValueToFLoat(strValue);
            if(oldValue != null) {
                data[index + Style.STYLE_DATA] = Float.floatToIntBits(value);
                return true;
            }
            return false;
        }

        // We already checked for TYPE_NULL. This should never happen.
        throw new RuntimeException("setFloat of bad type: 0x" + Integer.toHexString(type));
    }

    /**
     * Sets a new {@link ColorInt} <var>value</var> by specific <var>index</var>
     *
     * @param index The index which to retrieve the data
     * @param value The new value that should be set
     * @return {@code true} if new value has been set otherwise {@code false}
     * @throws RuntimeException if the {@link TypedArray} has already been recycled.
     * @see TypedArray#getColor(int, int)
     */
    public boolean setColor(@StyleableRes int index, @ColorInt final int value) {
        if(isRecycled())
            throw new RuntimeException("Cannot make calls to a recycled instance!");

        final int attrIndex = index;
        index *= Style.STYLE_NUM_ENTRIES;

        final int[] data = mData;
        final int type = data[index + Style.STYLE_TYPE];
        if(type == TypedValue.TYPE_NULL) {
            return false;
        } else if(type >= TypedValue.TYPE_FIRST_INT
                && type <= TypedValue.TYPE_LAST_INT) {
            data[index + Style.STYLE_DATA] = value;
            return true;
        } else if(type == TypedValue.TYPE_STRING) {
            final TypedValue v = mTmpValue;
            if(getValueAt(index, v)) {
                data[index + Style.STYLE_TYPE] = ResourceUtils.getColorType(value);
                data[index + Style.STYLE_DATA] = value;
                data[index + Style.STYLE_RESOURCE_ID] = 0;//nulls resource id

                return true;
            }
            return false;
        } else if(type == TypedValue.TYPE_ATTRIBUTE) {
            throw new UnsupportedOperationException(
                    "Failed to resolve attribute at index " + attrIndex);
        }

        throw new UnsupportedOperationException("Can't convert value at index " + attrIndex
                + " to color: type=0x" + Integer.toHexString(type));
    }

    /**
     * Sets a new {@link ColorRes} <var>value</var> by specific <var>index</var>
     *
     * @param index The index which to retrieve the data
     * @param value The new value that should be set
     * @return {@code true} if new value has been set otherwise {@code false}
     * @throws RuntimeException if the {@link TypedArray} has already been recycled.
     * @see TypedArray#getColorStateList(int)
     */
    public boolean setColorStateList(@StyleableRes int index, @ColorRes final int value) {
        if(isRecycled())
            throw new RuntimeException("Cannot make calls to a recycled instance!");

        final int attrIndex = index;
        index *= Style.STYLE_NUM_ENTRIES;

        final int[] data = mData;
        final TypedValue v = mTmpValue;
        if(getValueAt(index, v)) {
            if(v.type == TypedValue.TYPE_ATTRIBUTE)
                throw new UnsupportedOperationException(
                        "Failed to resolve attribute at index " + attrIndex + ": " + v);
            data[index + Style.STYLE_RESOURCE_ID] = value;
            return true;
        }
        return false;
    }

    /**
     * Sets a new integer <var>value</var> by specific <var>index</var>
     *
     * @param index The index which to retrieve the data
     * @param value The new value that should be set
     * @return {@code true} if new value has been set otherwise {@code false}
     * @throws RuntimeException if the {@link TypedArray} has already been recycled.
     * @see TypedArray#getInteger(int, int)
     */
    public boolean setInteger(@StyleableRes int index, final int value) {
        if(isRecycled())
            throw new RuntimeException("Cannot make calls to a recycled instance!");

        final int attrIndex = index;
        index *= Style.STYLE_NUM_ENTRIES;

        final int[] data = mData;
        final int type = data[index + Style.STYLE_TYPE];
        if(type == TypedValue.TYPE_NULL)
            return false;
        else if(type >= TypedValue.TYPE_FIRST_INT
                && type <= TypedValue.TYPE_LAST_INT) {
            data[index + Style.STYLE_DATA] = value;
            return true;
        } else if(type == TypedValue.TYPE_ATTRIBUTE)
            throw new UnsupportedOperationException(
                    "Failed to resolve attribute at index " + attrIndex + ": " + value);

        throw new UnsupportedOperationException("Can't convert value at index " + attrIndex
                + " to integer: type=0x" + Integer.toHexString(type));
    }

    /**
     * Sets a new dimensional <var>unit</var> attribute <var>value</var> by specific <var>index</var>
     *
     * @param index The index which to retrieve the data
     * @param value The new value that should be set
     * @return {@code true} if new value has been set otherwise {@code false}
     * @throws RuntimeException if the {@link TypedArray} has already been recycled.
     * @see TypedArray#getDimension(int, float)
     */
    public boolean setDimension(@StyleableRes int index, @ComplexDimensionUnit final int unit, @Dimension final float value) {
        if(isRecycled())
            throw new RuntimeException("Cannot make calls to a recycled instance!");

        final int attrIndex = index;
        index *= Style.STYLE_NUM_ENTRIES;

        final int[] data = mData;
        final int type = data[index + Style.STYLE_TYPE];
        if(type == TypedValue.TYPE_NULL)
            return false;
        else if(type == TypedValue.TYPE_DIMENSION) {
            data[index + Style.STYLE_DATA] = ResourceUtils.createComplexDimension(value, unit);
            return true;
        } else if(type == TypedValue.TYPE_ATTRIBUTE)
            throw new UnsupportedOperationException(
                    "Failed to resolve attribute at index " + attrIndex);

        throw new UnsupportedOperationException("Can't convert value at index " + attrIndex
                + " to dimension: type=0x" + Integer.toHexString(type));
    }

    /**
     * Sets a new dimensional <var>unit</var> attribute <var>value</var> by specific <var>index</var>
     *
     * @param index The index which to retrieve the data
     * @param value The new value that should be set
     * @return {@code true} if new value has been set otherwise {@code false}
     * @throws RuntimeException if the {@link TypedArray} has already been recycled.
     * @see TypedArray#getDimension(int, float)
     * @see TypedArray#getDimensionPixelOffset(int, int)
     */
    public boolean setDimensionPixelOffset(@StyleableRes int index, @ComplexDimensionUnit final int units, final int value) {
        if(isRecycled())
            throw new RuntimeException("Cannot make calls to a recycled instance!");

        final int attrIndex = index;
        index *= Style.STYLE_NUM_ENTRIES;

        final int[] data = mData;
        final int type = data[index + Style.STYLE_TYPE];
        if(type == TypedValue.TYPE_NULL)
            return false;
        else if(type == TypedValue.TYPE_DIMENSION) {
            data[index + Style.STYLE_DATA] = ResourceUtils.createComplexDimension(value, units);
            return true;
        } else if(type == TypedValue.TYPE_ATTRIBUTE)
            throw new UnsupportedOperationException(
                    "Failed to resolve attribute at index " + attrIndex + ": ");

        throw new UnsupportedOperationException("Can't convert value at index " + attrIndex
                + " to dimension: type=0x" + Integer.toHexString(type));
    }

    /**
     * Sets a new {@link ComplexDimensionUnit} <var>value</var> by specific <var>index</var>
     *
     * @param index The index which to retrieve the data
     * @param value The new value that should be set
     * @return {@code true} if new value has been set otherwise {@code false}
     * @throws RuntimeException if the {@link TypedArray} has already been recycled.
     * @see TypedArray#getDimensionPixelSize(int, int)
     */
    public boolean setDimensionPixelSize(@StyleableRes int index, @ComplexDimensionUnit final int value) {
        if(isRecycled())
            throw new RuntimeException("Cannot make calls to a recycled instance!");

        final int attrIndex = index;
        index *= Style.STYLE_NUM_ENTRIES;

        final int[] data = mData;
        final int type = data[index + Style.STYLE_TYPE];
        if(type == TypedValue.TYPE_NULL)
            return false;
        else if(type == TypedValue.TYPE_DIMENSION) {
            data[index + Style.STYLE_DATA] &= (value & TypedValue.COMPLEX_UNIT_MASK) << TypedValue.COMPLEX_UNIT_SHIFT;
            return true;
        } else if(type == TypedValue.TYPE_ATTRIBUTE)
            throw new UnsupportedOperationException(
                    "Failed to resolve attribute at index " + attrIndex + ": " + value);

        throw new UnsupportedOperationException("Can't convert value at index " + attrIndex
                + " to dimension: type=0x" + Integer.toHexString(type));
    }

    /**
     * Sets a new fraction <var>value</var> by specific <var>index</var>
     *
     * @param index The index which to retrieve the data
     * @param value The new value that should be set
     * @return {@code true} if new value has been set otherwise {@code false}
     * @throws RuntimeException if the {@link TypedArray} has already been recycled.
     * @see TypedArray#getColorStateList(int)
     */
    public boolean setFraction(@StyleableRes int index, final float value) {
        if(isRecycled())
            throw new RuntimeException("Cannot make calls to a recycled instance!");

        final int attrIndex = index;
        index *= Style.STYLE_NUM_ENTRIES;

        final int[] data = mData;
        final int type = data[index + Style.STYLE_TYPE];
        if(type == TypedValue.TYPE_NULL)
            return false;
        else if(type == TypedValue.TYPE_FRACTION) {
            data[index + Style.STYLE_DATA] = ResourceUtils.floatToComplex(value);
            return true;
        } else if(type == TypedValue.TYPE_ATTRIBUTE)
            throw new UnsupportedOperationException(
                    "Failed to resolve attribute at index " + attrIndex + ": " + value);

        throw new UnsupportedOperationException("Can't convert value at index " + attrIndex
                + " to fraction: type=0x" + Integer.toHexString(type));
    }

    /**
     * Sets a new resource identifier by specific <var>index</var>
     *
     * @param index The index which to retrieve the data
     * @param value The new resource identifier
     * @throws RuntimeException if the {@link TypedArray} has already been recycled.
     * @see TypedArray#getResourceId(int, int)
     */
    public void setResourceId(@StyleableRes int index, @AnyRes final int value) {
        if(isRecycled())
            throw new RuntimeException("Cannot make calls to a recycled instance!");

        index *= Style.STYLE_NUM_ENTRIES;

        final int[] data = mData;
        data[index + Style.STYLE_RESOURCE_ID] = value;
    }

    /**
     * Sets a new theme attribute identifier by specific <var>index</var>
     *
     * @param index The index which to retrieve the data
     * @param value The new theme attribute identifier
     * @throws RuntimeException if the {@link TypedArray} has already been recycled.
     * @see TypedArray#getResourceId(int, int)
     */
    public boolean setThemeAttributeId(@StyleableRes int index, final int value) {
        if(isRecycled())
            throw new RuntimeException("Cannot make calls to a recycled instance!");

        index *= Style.STYLE_NUM_ENTRIES;
        final int[] data = mData;
        final int type = data[index + Style.STYLE_TYPE];
        if(type == TypedValue.TYPE_ATTRIBUTE) {
            data[index + Style.STYLE_DATA] = value;
            return true;
        }
        return false;
    }

    @NonNull
    @Override
    public String toString() {
        return mTypedArray.toString();
    }

    // STYLE_ prefixed constants are offsets within the typed data array.
    // Keep this in sync with libs/androidfw/include/androidfw/AttributeResolution.h
    @Retention(RetentionPolicy.SOURCE)
    public @interface Style {
        int STYLE_NUM_ENTRIES = AndroidVersioning.isAtLeastQ() ? 7 : 6;
        int STYLE_TYPE = 0;
        int STYLE_DATA = 1;
        int STYLE_ASSET_COOKIE = 2;
        int STYLE_RESOURCE_ID = 3;
        int STYLE_CHANGING_CONFIGURATIONS = 4;
        int STYLE_DENSITY = 5;
        @RequiresApi(api = Build.VERSION_CODES.Q)
        int STYLE_SOURCE_RESOURCE_ID = 6;
    }
}
