package com.vologhat.pygmaliondemo.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.vologhat.pygmaliondemo.R
import com.vologhat.pygmaliondemo.databinding.FragmentMainBinding

class FragmentMain
:Fragment()
{
    lateinit var binding:FragmentMainBinding

    override fun onCreateView(
        inflater:LayoutInflater,
        container:ViewGroup?,
        savedInstanceState:Bundle?
    ):View
    {
        binding=FragmentMainBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view:View,savedInstanceState:Bundle?)
    {
        super.onViewCreated(view,savedInstanceState)

        binding.sampleText.text=getString(R.string.hello_world)
    }
}