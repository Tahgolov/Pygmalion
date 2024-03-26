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
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.StyleableRes;

import com.vologhat.pygmalion.utils.AndroidVersioning;
import com.vologhat.pygmalion.utils.ReflectionUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

public class PygmalionTypedArray {
    final static private TypedValue TMP_VALUE = new TypedValue();

    final static private Field sDataFld;
    final static private Field sIndicesFld;
    final static private Method getValueAtMtd;
    final static private Method loadStringValueAtMtd;

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
        } catch (final Throwable cause) {
            throw new RuntimeException(cause);
        }
    }

    public PygmalionTypedArray(final TypedArray ta) {
        mTypedArray = ta;

        Resources res = ta.getResources();
        if (res instanceof PygmalionResources)
            res = ((PygmalionResources) res).getOriginalResources();
        mOrigResources = res;

        try {
            mData = (int[]) sDataFld.get(ta);
            mIndices = (int[]) sIndicesFld.get(ta);
        } catch (final Throwable cause) {
            throw new RuntimeException(cause);
        }
    }

    public TypedArray getTypedArray() {
        return mTypedArray;
    }

    public int[] getDataBuffer() {
        return mData;
    }

    public int[] getIndicesBuffer() {
        return mIndices;
    }

    public int length() {
        return mTypedArray.length();
    }

    public int getIndexCount() {
        return mTypedArray.getIndexCount();
    }

    public int getIndex(final int at) {
        return mTypedArray.getIndex(at);
    }

    public CharSequence getText(@StyleableRes final int index) {
        return mTypedArray.getText(index);
    }

    public String getString(@StyleableRes final int index) {
        return mTypedArray.getString(index);
    }

    public String getNonResourceString(@StyleableRes final int index) {
        return mTypedArray.getNonResourceString(index);
    }

    public boolean getBoolean(@StyleableRes int index, final boolean defValue) {
        return mTypedArray.getBoolean(index, defValue);
    }

    public int getInt(@StyleableRes final int index, final int defValue) {
        return mTypedArray.getInt(index, defValue);
    }

    public float getFloat(@StyleableRes final int index, final float defValue) {
        return mTypedArray.getFloat(index, defValue);
    }

    @ColorInt
    public int getColor(@StyleableRes final int index, @ColorInt final int defValue) {
        return mTypedArray.getColor(index, defValue);
    }

    @Nullable
    public ColorStateList getColorStateList(@StyleableRes final int index) {
        return mTypedArray.getColorStateList(index);
    }

    public int getInteger(@StyleableRes final int index, final int defValue) {
        return mTypedArray.getInt(index, defValue);
    }

    public float getDimension(@StyleableRes final int index, final float defValue) {
        return mTypedArray.getDimension(index, defValue);
    }

    public int getDimensionPixelOffset(@StyleableRes final int index, final int defValue) {
        return mTypedArray.getDimensionPixelOffset(index, defValue);
    }

    public int getDimensionPixelSize(@StyleableRes final int index, final int defValue) {
        return mTypedArray.getDimensionPixelSize(index, defValue);
    }

    public int getLayoutDimension(@StyleableRes final int index, final int defValue) {
        return mTypedArray.getLayoutDimension(index, defValue);
    }

    public float getFraction(@StyleableRes final int index, final int base, final int pbase, final float defValue) {
        return mTypedArray.getFraction(index, base, pbase, defValue);
    }

    @AnyRes
    public int getResourceId(@StyleableRes final int index, final int defValue) {
        return mTypedArray.getResourceId(index, defValue);
    }

    //own implementation
    public int getThemeAttributeId(@StyleableRes int index, final int defValue) {
        index *= Style.STYLE_NUM_ENTRIES;
        final int[] data = mData;
        if (data[index + Style.STYLE_TYPE] == TypedValue.TYPE_ATTRIBUTE)
            return data[index + Style.STYLE_DATA];
        return defValue;
    }

    @Nullable
    public Drawable getDrawable(@StyleableRes final int index) {
        return mTypedArray.getDrawable(index);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    public Typeface getFont(@StyleableRes final int index) {
        return mTypedArray.getFont(index);
    }

    public CharSequence[] getTextArray(@StyleableRes final int index) {
        return mTypedArray.getTextArray(index);
    }

    public boolean getValue(@StyleableRes final int index, final TypedValue outValue) {
        return mTypedArray.getValue(index, outValue);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public int getType(@StyleableRes final int index) {
        return mTypedArray.getType(index);
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @AnyRes
    public int getSourceResourceId(@StyleableRes final int index, @AnyRes final int defValue) {
        return mTypedArray.getSourceResourceId(index, defValue);
    }

    public boolean hasValue(@StyleableRes final int index) {
        return mTypedArray.hasValue(index);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    public boolean hasValueOrEmpty(@StyleableRes int index) {
        return mTypedArray.hasValueOrEmpty(index);
    }

    public TypedValue peekValue(@StyleableRes final int index) {
        return mTypedArray.peekValue(index);
    }

    public String getPositionDescription() {
        return mTypedArray.getPositionDescription();
    }

    //own implementation
    @Nullable
    public int[] extractThemeAttrs() {
        return extractThemeAttrs(null);
    }

    //own implementation
    @Nullable
    public int[] extractThemeAttrs(@Nullable final int[] scrap) {
        int[] attrs = null;

        final int[] data = mData;
        final int N = length();
        for (int i = 0; i < N; ++i) {
            final int index = Style.STYLE_NUM_ENTRIES;
            if (data[index + Style.STYLE_TYPE] != TypedValue.TYPE_ATTRIBUTE)// Not an attribute, ignore.
                continue;

            // Null the entry so that we can safely call getZzz().
            data[index + Style.STYLE_TYPE] = TypedValue.TYPE_NULL;

            final int attr = data[index + Style.STYLE_DATA];
            if (attr == 0)// Useless data, ignore.
                continue;

            // Ensure we have a usable attribute array.
            if (attrs == null)
                if (scrap != null && scrap.length == N) {
                    attrs = scrap;
                    Arrays.fill(attrs, 0);
                } else attrs = new int[N];

            attrs[i] = attr;
        }

        return attrs;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public int getChangingConfigurations() {
        return mTypedArray.getChangingConfigurations();
    }

    //own implementation
    public boolean getValueAt(@StyleableRes final int index, @NonNull final TypedValue outValue) {
        try {
            return (boolean) getValueAtMtd.invoke(mTypedArray, index, outValue);
        } catch (final Throwable ignored) {
            return false;
        }
    }

    //own implementation
    @Nullable
    public CharSequence loadStringValueAt(@StyleableRes final int index) {
        try {
            return (CharSequence) loadStringValueAtMtd.invoke(mTypedArray, index);
        } catch (final Throwable ignored) {
            return null;
        }
    }

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
