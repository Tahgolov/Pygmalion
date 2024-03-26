package com.vologhat.pygmaliondemo

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentManager.FragmentLifecycleCallbacks
import com.vologhat.pygmalion.Pygmalion
import com.vologhat.pygmalion.iface.IActivityLifecycleHooks
import com.vologhat.pygmalion.iface.ILayoutInflaterFactoryHooks
import com.vologhat.pygmaliondemo.components.PygmalionFragmentLifecycleHooks
import com.vologhat.pygmaliondemo.ui.fragment.FragmentMain

class EntryPoint
:Application()
{
    override fun onCreate()
    {
        super.onCreate()

        //demonstrate activity hooks
        Pygmalion.addActivityHooks(
            object:IActivityLifecycleHooks//hook to debug activity lifecycle
            {
                override fun onActivityCreated(activity:Activity,savedInstanceState:Bundle?)
                {

                }
            },
            null
        )
        addHookForFragments()

        //demonstrate fragment hooks
        addFragmentHook(
            object:FragmentLifecycleCallbacks()//hook to debug fragment lifecycle
            {
                override fun onFragmentViewCreated(
                    fm:FragmentManager,
                    f:Fragment,
                    v:View,
                    savedInstanceState:Bundle?
                )
                {

                }
            }
        )

        //demonstrate LayoutInflater factory hooks
        Pygmalion.addLayoutInflaterFactoryHooks(
            object:ILayoutInflaterFactoryHooks//hook to modify any View while it's creating or created.
            {
                override fun onCreateView(
                    name:String,
                    context:Context,
                    attrs:AttributeSet?
                ):View?
                {
                    return null
                }
            }
        )

        //init Pygmalion
        Pygmalion.load()
    }

    private companion object
    {
        const val TAG="PygmalionDemo"

        private val fragmentHooks=mutableListOf< FragmentLifecycleCallbacks >()

        fun addFragmentHook(fragmentHook:FragmentLifecycleCallbacks)=fragmentHooks.add(fragmentHook)

        fun addHookForFragments()=
            Pygmalion.addActivityHooks(
                object:IActivityLifecycleHooks//non-library hook to provide possibility of Fragments hooking
                {
                    override fun onActivityPreCreated(activity:Activity,savedInstanceState:Bundle?)
                    {
                        if(activity is FragmentActivity)
                        {
                            activity.supportFragmentManager.registerFragmentLifecycleCallbacks(
                                PygmalionFragmentLifecycleHooks(fragmentHooks),
                                false
                            )
                        }
                    }
                }
            )
    }
}