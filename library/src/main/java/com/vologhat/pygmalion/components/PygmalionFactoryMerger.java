package com.vologhat.pygmalion.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.vologhat.pygmalion.iface.ILayoutInflaterFactoryHooks;

import java.util.List;

/**
 * The modified copy of FactoryMerger from {@link android.view.LayoutInflater}
 * made for using own hooks with original factories
 */
public class PygmalionFactoryMerger
implements LayoutInflater.Factory2
{
    private final LayoutInflater.Factory mF1,mF2;
    private final LayoutInflater.Factory2 mF12,mF22;

    private List< ILayoutInflaterFactoryHooks > mViewHandlers;

    public PygmalionFactoryMerger(final LayoutInflater.Factory f1,final LayoutInflater.Factory2 f12,
                                  final LayoutInflater.Factory f2,final LayoutInflater.Factory2 f22)
    {
        mF1=f1;
        mF2=f2;
        mF12=f12;
        mF22=f22;
    }

    public void setViewHandlers(final List< ILayoutInflaterFactoryHooks > viewHandlers)
    { mViewHandlers=viewHandlers; }

    @Override
    public View onCreateView(final String name,final Context context,final AttributeSet attrs)
    {
        View v=createView(name,context,attrs);
        if(v!=null)return v;
        v=mF1.onCreateView(name,context,attrs);
        if(v==null)v=mF2.onCreateView(name,context,attrs);
        if(v!=null)processCreatedView(v,name,context,attrs);
        return v;
    }

    @Override
    public View onCreateView(final View parent,final String name,
                             final Context context,final AttributeSet attrs)
    {
        View v=createView(name,context,attrs);
        if(v==null)
            v=mF12!=null
                ?mF12.onCreateView(parent,name,context,attrs)
                :mF1.onCreateView(name,context,attrs);
        if(v==null)
            v=mF22!=null
                ?mF22.onCreateView(parent,name,context,attrs)
                :mF2.onCreateView(name, context,attrs);
        if(v!=null)processCreatedView(v,name,context,attrs);
        return v;
    }

    private View createView(final String name,final Context context,final AttributeSet attrs)
    {
        View v;
        for(ILayoutInflaterFactoryHooks handler:mViewHandlers)
            if((v=handler.onCreateView(name,context,attrs))!=null)
                return v;
        return null;
    }

    private void processCreatedView(final View targetView,final String name,final Context context,final AttributeSet attrs)
    {
        for(ILayoutInflaterFactoryHooks handler:mViewHandlers)
            handler.onViewCreated(targetView,name,context,attrs);
    }
}