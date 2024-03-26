package com.vologhat.pygmalion.iface;

import android.app.Activity;
import android.app.Application.ActivityLifecycleCallbacks;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public interface IActivityLifecycleHooks extends ActivityLifecycleCallbacks {
    @Override
    default void onActivityPreCreated(@NonNull final Activity activity, @Nullable final Bundle savedInstanceState) {
    }

    @Override
    default void onActivityCreated(@NonNull final Activity activity, @Nullable final Bundle savedInstanceState) {
    }

    @Override
    default void onActivityPostCreated(@NonNull final Activity activity, @Nullable final Bundle savedInstanceState) {
    }

    @Override
    default void onActivityPreStarted(@NonNull final Activity activity) {
    }

    @Override
    default void onActivityStarted(@NonNull final Activity activity) {
    }

    @Override
    default void onActivityPostStarted(@NonNull final Activity activity) {
    }

    @Override
    default void onActivityPreResumed(@NonNull final Activity activity) {
    }

    @Override
    default void onActivityResumed(@NonNull final Activity activity) {
    }

    @Override
    default void onActivityPostResumed(@NonNull final Activity activity) {
    }

    @Override
    default void onActivityPrePaused(@NonNull final Activity activity) {
    }

    @Override
    default void onActivityPaused(@NonNull final Activity activity) {
    }

    @Override
    default void onActivityPostPaused(@NonNull final Activity activity) {
    }

    @Override
    default void onActivityPreStopped(@NonNull final Activity activity) {
    }

    @Override
    default void onActivityStopped(@NonNull final Activity activity) {
    }

    @Override
    default void onActivityPostStopped(@NonNull final Activity activity) {
    }

    @Override
    default void onActivityPreSaveInstanceState(@NonNull final Activity activity, @Nullable final Bundle outState) {
    }

    @Override
    default void onActivitySaveInstanceState(@NonNull final Activity activity, @Nullable final Bundle outState) {
    }

    @Override
    default void onActivityPostSaveInstanceState(@NonNull final Activity activity, @Nullable final Bundle outState) {
    }

    @Override
    default void onActivityPreDestroyed(@NonNull final Activity activity) {
    }

    @Override
    default void onActivityDestroyed(@NonNull final Activity activity) {
    }

    @Override
    default void onActivityPostDestroyed(@NonNull final Activity activity) {
    }
}
