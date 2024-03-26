package com.vologhat.pygmalion.components;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.annotation.ArrayRes;
import androidx.annotation.NonNull;
import androidx.annotation.StyleableRes;

public class PygmalionResources
        extends Resources {
    final Resources mOrigResources;

    public PygmalionResources(final Resources src) {
        super(src.getAssets(), src.getDisplayMetrics(), src.getConfiguration());
        mOrigResources = src;
    }

    @Override
    public int getColor(int id) throws NotFoundException {
        return super.getColor(id);
    }

    @Override
    public TypedArray obtainAttributes(AttributeSet set, @StyleableRes int[] attrs) {
        final PygmalionTypedArray pda = new PygmalionTypedArray(super.obtainAttributes(set, attrs));
        return pda.getTypedArray();
    }

    @NonNull
    @Override
    public TypedArray obtainTypedArray(@ArrayRes int id) throws NotFoundException {
        final PygmalionTypedArray pta = new PygmalionTypedArray(super.obtainTypedArray(id));
        return pta.getTypedArray();
    }

    public Resources getOriginalResources() {
        return mOrigResources;
    }
}
