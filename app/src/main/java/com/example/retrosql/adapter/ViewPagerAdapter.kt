package com.example.retrosql.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.retrosql.fragment.FavouriteFragment
import com.example.retrosql.fragment.HomeFragment

open class ViewPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return 2
    }


    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return HomeFragment()
            1 -> return FavouriteFragment()
        }
        return HomeFragment()
    }
}