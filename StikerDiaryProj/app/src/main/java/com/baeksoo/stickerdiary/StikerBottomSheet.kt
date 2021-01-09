package com.baeksoo.stickerdiary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.baeksoo.stickerdiary.R
import com.baeksoo.stickerdiary.StikerFragment
import com.baeksoo.stickerdiary.Adapter.StikerViewPagerAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.dialog_stiker.*

class StikerBottomSheet() : BottomSheetDialogFragment() {

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

//        view?.findViewById<Button>(R.id.btnChoose)?.setOnClickListener {
//            dismiss() //확인 버튼 누르면 다이얼로그 닫기
//        }
    }

    private fun initViewPager(){
        val searchFragment = StikerFragment()

        val cameraFragment = StikerFragment()

        val callFragment = StikerFragment()


        val adapter = StikerViewPagerAdapter(
            childFragmentManager
        ) // PagerAdapter 생성
        adapter.addItems(searchFragment)
        adapter.addItems(cameraFragment)
        adapter.addItems(callFragment)

        viewPager.adapter = adapter // 뷰페이저에 adapter 장착
        tabLayout.setupWithViewPager(viewPager) // 탭레이아웃과 뷰페이저를 연동

        tabLayout.getTabAt(0)?.setText("찾기")
        tabLayout.getTabAt(1)?.setText("사진")
        tabLayout.getTabAt(2)?.setText("전화")

//        main_tablayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
//            override fun onTabReselected(p0: TabLayout.Tab?) {}
//
//            override fun onTabUnselected(p0: TabLayout.Tab?) {}
//
//            override fun onTabSelected(p0: TabLayout.Tab?) {}
//        })

    }
}
