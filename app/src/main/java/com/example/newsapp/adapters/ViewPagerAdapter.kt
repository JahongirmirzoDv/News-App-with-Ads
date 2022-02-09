package com.example.newsapp.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.newsapp.ui.ViewFragment

class ViewPagerAdapter(frm: FragmentManager, var titlle: List<String>) : FragmentPagerAdapter(
    frm
) {
    override fun getCount(): Int = titlle.size

    override fun getItem(position: Int): Fragment {
        return ViewFragment.newInstance(titlle[position])
    }
}