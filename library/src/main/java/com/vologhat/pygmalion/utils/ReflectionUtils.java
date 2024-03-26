package com.vologhat.pygmalion.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectionUtils {
    static public Field getField(final Class<?> clz, final String name) throws NoSuchFieldException {
        final Field fld = clz.getDeclaredField(name);
        fld.setAccessible(true);
        return fld;
    }

    static public Method getMethod(final Class<?> clz, final String name, final Class<?>... params) throws NoSuchMethodException {
        final Method mtd = clz.getDeclaredMethod(name, params);
        mtd.setAccessible(true);
        return mtd;
    }
}
