package com.example.movielist.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.movielist.views.GenreList
import com.example.movielist.views.HomeList

class MainPagerAdapter(private val fragment: Fragment): FragmentStateAdapter(fragment) {
    private val fragmentList = listOf<Fragment>(
        HomeList(),
        GenreList(),
    )
    override fun getItemCount(): Int = fragmentList.size

    override fun createFragment(position: Int): Fragment = fragmentList[position]
}