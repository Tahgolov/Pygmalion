package com.vologhat.pygmaliondemo.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.vologhat.pygmaliondemo.R
import com.vologhat.pygmaliondemo.ui.fragment.FragmentMain

class MainActivity
    : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null)
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<FragmentMain>(R.id.fragment_container)
            }
    }
}