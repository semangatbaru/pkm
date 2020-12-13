package com.staydev.seani

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.PagerTabStrip
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.staydev.seani.adapter.AdapterPager

class ActivityNotification : AppCompatActivity() {
    private lateinit var mViewPager:ViewPager
    private lateinit var mViewPagerTab:TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

        mViewPager = findViewById(R.id.viewpager_main)
        mViewPagerTab = findViewById(R.id.tabs_main)
        mViewPager.adapter = AdapterPager(supportFragmentManager)
        mViewPagerTab.setupWithViewPager(mViewPager)
    }
}