package com.vologhat.pygmaliondemo.components

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentManager.FragmentLifecycleCallbacks

class PygmalionFragmentLifecycleHooks(
    private val fragmentHooks: Iterable<FragmentLifecycleCallbacks>//for compatibility with each collection type
) : FragmentLifecycleCallbacks() {
    override fun onFragmentPreAttached(
        fm: FragmentManager,
        f: Fragment,
        context: Context
    ) = fragmentHooks.forEach { hook ->
        hook.onFragmentPreAttached(fm, f, context)
    }

    override fun onFragmentAttached(
        fm: FragmentManager,
        f: Fragment,
        context: Context
    ) = fragmentHooks.forEach { hook ->
        hook.onFragmentAttached(fm, f, context)
    }

    override fun onFragmentPreCreated(
        fm: FragmentManager,
        f: Fragment,
        savedInstanceState: Bundle?
    ) = fragmentHooks.forEach { hook ->
        hook.onFragmentPreCreated(fm, f, savedInstanceState)
    }

    override fun onFragmentCreated(
        fm: FragmentManager,
        f: Fragment,
        savedInstanceState: Bundle?
    ) = fragmentHooks.forEach { hook ->
        hook.onFragmentCreated(fm, f, savedInstanceState)
    }

    override fun onFragmentActivityCreated(
        fm: FragmentManager,
        f: Fragment,
        savedInstanceState: Bundle?
    ) = fragmentHooks.forEach { hook ->
        hook.onFragmentActivityCreated(fm, f, savedInstanceState)
    }

    override fun onFragmentViewCreated(
        fm: FragmentManager,
        f: Fragment,
        v: View,
        savedInstanceState: Bundle?
    ) = fragmentHooks.forEach { hook ->
        hook.onFragmentViewCreated(fm, f, v, savedInstanceState)
    }

    override fun onFragmentStarted(
        fm: FragmentManager,
        f: Fragment
    ) = fragmentHooks.forEach { hook ->
        hook.onFragmentStarted(fm, f)
    }

    override fun onFragmentResumed(
        fm: FragmentManager,
        f: Fragment
    ) = fragmentHooks.forEach { hook ->
        hook.onFragmentResumed(fm, f)
    }

    override fun onFragmentPaused(
        fm: FragmentManager,
        f: Fragment
    ) = fragmentHooks.forEach { hook ->
        hook.onFragmentPaused(fm, f)
    }

    override fun onFragmentStopped(
        fm: FragmentManager,
        f: Fragment
    ) = fragmentHooks.forEach { hook ->
        hook.onFragmentStopped(fm, f)
    }

    override fun onFragmentSaveInstanceState(
        fm: FragmentManager,
        f: Fragment,
        outState: Bundle
    ) = fragmentHooks.forEach { hook ->
        hook.onFragmentSaveInstanceState(fm, f, outState)
    }

    override fun onFragmentViewDestroyed(
        fm: FragmentManager,
        f: Fragment
    ) = fragmentHooks.forEach { hook ->
        hook.onFragmentViewDestroyed(fm, f)
    }

    override fun onFragmentDestroyed(
        fm: FragmentManager,
        f: Fragment
    ) = fragmentHooks.forEach { hook ->
        hook.onFragmentDestroyed(fm, f)
    }

    override fun onFragmentDetached(
        fm: FragmentManager,
        f: Fragment
    ) = fragmentHooks.forEach { hook ->
        hook.onFragmentDetached(fm, f)
    }
}