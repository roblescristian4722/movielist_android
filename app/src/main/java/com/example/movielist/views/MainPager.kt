package com.example.movielist.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.movielist.R
import com.example.movielist.adapters.MainPagerAdapter
import com.example.movielist.databinding.FragmentMainPagerBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainPager : Fragment() {
    private lateinit var binding: FragmentMainPagerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_pager, container, false)
        binding.vpMain.adapter = MainPagerAdapter(this)

        TabLayoutMediator(binding.tbMain, binding.vpMain) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.tab_0)
                1 -> tab.text = getString(R.string.tab_1)
            }
        }.attach()

        return binding.root
    }
}