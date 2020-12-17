package com.baeksoo.stickerdiary

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class StikerViewPagerAdapter(fm : FragmentManager) : FragmentStatePagerAdapter(fm) {
    private var fragments: ArrayList<StikerFragment> = ArrayList()

    override fun getItem(position: Int): Fragment = fragments[position]

    override fun getCount(): Int = fragments.size

    fun addItems(fragment: StikerFragment) {
        fragments.add(fragment)
    }
}