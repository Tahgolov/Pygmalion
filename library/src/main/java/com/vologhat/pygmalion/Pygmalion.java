package com.vologhat.pygmalion;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.vologhat.pygmalion.components.PygmalionFactoryMerger;
import com.vologhat.pygmalion.components.PygmalionResources;
import com.vologhat.pygmalion.iface.IActivityLifecycleHooks;
import com.vologhat.pygmalion.iface.ILayoutInflaterFactoryHooks;
import com.vologhat.pygmalion.utils.AndroidVersioning;
import com.vologhat.pygmalion.utils.ReflectionUtils;

import org.jetbrains.annotations.NotNull;
import org.lsposed.hiddenapibypass.HiddenApiBypass;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressLint("PrivateApi")
public class Pygmalion {
    //reflection
    final static private Field sResourcesFld;
    final static private Field sFactorySetFld;

    @SuppressLint("StaticFieldLeak")//application context is safe
    final static private Application sApplication;

    //hook lists
    final static private List<ActivityLifecycleCallbacks> sActivityHooks = new ArrayList<>();
    final static private List<ILayoutInflaterFactoryHooks> sLayoutInflaterFactoryHooks = new ArrayList<>();

    static private boolean sLoaded = false;

    static {
        //get application context via reflection to initialize it from anywhere
        try {
            final Class<?> appGlobalsClz = Class.forName("android.app.AppGlobals");
            final Method getInitialApplication = ReflectionUtils.getMethod(appGlobalsClz, "getInitialApplication");

            sApplication = (Application) getInitialApplication.invoke(null);
        } catch(final Throwable cause) {
            throw new RuntimeException("Failed to get application", cause);
        }

        //bypass restrictions for Android P+ (thx Android Team)
        if(AndroidVersioning.isAtLeastPie())
            HiddenApiBypass.addHiddenApiExemptions(Constants.EXEMPTIONS);

        //init reflection stuff for using it in hooks
        try {
            sResourcesFld = ReflectionUtils.getField(ContextThemeWrapper.class, "mResources");
            sFactorySetFld = ReflectionUtils.getField(LayoutInflater.class, "mFactorySet");
        } catch(final NoSuchFieldException cause) {
            throw new RuntimeException("Failed to initialize reflection", cause);
        }

        //register own activity hook to attach Pygmalion to every creating Activities and their members
        addActivityHooks(
                new IActivityLifecycleHooks() {
                    @Override
                    public void onActivityPreCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
                        attachToResources(activity);
                    }

                    @Override
                    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
                        attachToLayoutInflaterFactory(activity);
                    }
                }
        );
    }

    static private void attachToResources(final Activity activity)//hook for modifying all possible resources during getting
    {
        if(activity.getResources() instanceof PygmalionResources) return;

        try {
            sResourcesFld.set(activity, new PygmalionResources(activity.getResources()));
        } catch(final Throwable cause) {
            Log.e(Constants.TAG, "Failed to attach resources:", cause);
        }
    }

    static private void attachToLayoutInflaterFactory(final Activity activity)//hook for changing views created by LayoutInflater
    {
        final LayoutInflater inflater = activity.getLayoutInflater();

        final LayoutInflater.Factory factory = inflater.getFactory();
        final LayoutInflater.Factory2 factory2 = inflater.getFactory2();
        if(factory2 instanceof PygmalionFactoryMerger) return;

        try {
            final PygmalionFactoryMerger merger = new PygmalionFactoryMerger(factory, factory2, factory, factory2);
            merger.setViewHandlers(sLayoutInflaterFactoryHooks);

            //disable factory setting check if the inflater already has factory
            if(factory != null || factory2 != null) sFactorySetFld.set(inflater, Boolean.FALSE);

            inflater.setFactory2(merger);
        } catch(final Throwable cause) {
            Log.e(Constants.TAG, "Failed to attach layout inflater:", cause);
        }
    }

    static public boolean isLoaded() {
        return sLoaded;
    }

    static public Application getApplication() {
        return sApplication;
    }

    static public void addActivityHooks(@NotNull final ActivityLifecycleCallbacks... hooks) {
        addAllHooks(sActivityHooks, hooks);
    }

    static public void addLayoutInflaterFactoryHooks(@NotNull final ILayoutInflaterFactoryHooks... hooks) {
        addAllHooks(sLayoutInflaterFactoryHooks, hooks);
    }

    //aux function to help with filling hook lists
    static private <H> void addAllHooks(final List<H> hookList, final H[] hooks) {
        hookList.addAll(Arrays.asList(hooks));
    }

    static public void load() {
        for(ActivityLifecycleCallbacks callbacks : sActivityHooks)
            sApplication.registerActivityLifecycleCallbacks(callbacks);

        sLoaded = true;
    }

    @Retention(RetentionPolicy.SOURCE)
    @interface Constants {
        String TAG = "Pygmalion";
        //interfaces set that should be exempted from non-SDK restrictions
        String[] EXEMPTIONS = {
                "Landroid/app/Activity;",
                "Landroid/content/res/TypedArray;",
                "Landroid/view/LayoutInflater;",
        };
    }
}