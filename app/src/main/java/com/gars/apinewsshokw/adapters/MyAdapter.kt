package com.gars.apinewsshokw.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.gars.apinewsshokw.fragments.HomeFragment
import com.gars.apinewsshokw.fragments.NewsProfileFragment

internal class MyAdapter (var context: Context,
                          fm: FragmentManager,
                          var totalTabs: Int): FragmentPagerAdapter(fm){


    override fun getItem(position: Int): Fragment {
        return when(position){

            0 -> { HomeFragment() }

            1 -> { NewsProfileFragment()  }

            else -> getItem(position)
        }
    }

    override fun getCount(): Int {
        return totalTabs
    }

}