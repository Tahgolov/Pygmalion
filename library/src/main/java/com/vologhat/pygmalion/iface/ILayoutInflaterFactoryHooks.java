package com.vologhat.pygmalion.iface;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public interface ILayoutInflaterFactoryHooks
{
    @Nullable
    default View onCreateView(@NonNull final String name,@NonNull final Context context,@Nullable final AttributeSet attrs)
    { return null; }

    default void onViewCreated(@NonNull final View targetView,@NonNull final String name,
                               @NonNull final Context context,@Nullable final AttributeSet attrs)
    { }
}
