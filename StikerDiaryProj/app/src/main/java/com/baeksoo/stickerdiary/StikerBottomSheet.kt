package com.baeksoo.stickerdiary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.baeksoo.stickerdiary.R
import com.baeksoo.stickerdiary.StikerFragment
import com.baeksoo.stickerdiary.Adapter.StikerViewPagerAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.dialog_stiker.*

class StikerBottomSheet(val scheduleDialog : ScheduleDialog?) : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.dialog_stiker, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initViewPager() // 뷰페이저와 어댑터 장착
    }

    private fun initViewPager(){
        val Fragment0 = StikerFragment(0,2, scheduleDialog)
        val Fragment1 = StikerFragment(1,0, scheduleDialog)
        val Fragment2 = StikerFragment(2,0, scheduleDialog)
        val Fragment3 = StikerFragment(3,0, scheduleDialog)
        val Fragment4 = StikerFragment(4,0, scheduleDialog)

        val adapter = StikerViewPagerAdapter(childFragmentManager)

        adapter.addItems(Fragment0)
        adapter.addItems(Fragment1)
        adapter.addItems(Fragment2)
        adapter.addItems(Fragment3)
        adapter.addItems(Fragment4)

        viewPager.adapter = adapter // 뷰페이저에 adapter 장착
        tabLayout.setupWithViewPager(viewPager) // 탭레이아웃과 뷰페이저를 연동

        tabLayout.getTabAt(0)?.setText("음식")
        tabLayout.getTabAt(1)?.setText("감정")
        tabLayout.getTabAt(2)?.setText("날씨")
        tabLayout.getTabAt(3)?.setText("사물")
        tabLayout.getTabAt(4)?.setText("동물")

//        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
//            override fun onTabReselected(p0: TabLayout.Tab?) {}
//
//            override fun onTabUnselected(p0: TabLayout.Tab?) {}
//
//            override fun onTabSelected(p0: TabLayout.Tab?) {}
//        })
    }
}
