package com.vologhat.pygmalion;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

interface BaseAndroidContextTest {
    default Context getTargetContext() {
        return InstrumentationRegistry.getInstrumentation().getTargetContext();
    }
}