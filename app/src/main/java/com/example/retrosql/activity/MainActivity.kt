package com.example.retrosql.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.retrosql.R
import com.example.retrosql.adapter.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    lateinit var tabLayout: TabLayout
    lateinit var viewPager: ViewPager2

    val tabsArray = arrayOf(
        "Home",
        "Favourite"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tabLayout = findViewById(R.id.tabLayout)
        viewPager = findViewById(R.id.viewPager)

        val adapter = ViewPagerAdapter(supportFragmentManager, lifecycle)

        viewPager.adapter = adapter
        tabLayout.let { tabLayout ->
            viewPager.let { viewPager ->
                TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                    tab.text = tabsArray[position]
                }.attach()
            }
        }
    }
}