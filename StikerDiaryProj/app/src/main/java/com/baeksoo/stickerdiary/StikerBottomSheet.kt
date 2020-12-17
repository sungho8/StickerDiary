package com.baeksoo.stickerdiary

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.dialog_stiker.*
import kotlinx.android.synthetic.main.tab_button.view.*

class StikerBottomSheet(fragmentManager: FragmentManager) : BottomSheetDialogFragment() {
    private lateinit var mContext : Context

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

        initViewPager()

        //화면의 텍스를 누르면 다이얼로그 꺼짐
        view?.findViewById<Button>(R.id.tvText)?.setOnClickListener {
            dismiss()
        }
    }

    private fun createView(tabName: String): View {
        var tabView = LayoutInflater.from(mContext).inflate(R.layout.tab_button, null)

        tabView.tvName.text = tabName
        when (tabName) {
            "찾기" -> {
                tabView.ivLogo.setImageResource(android.R.drawable.ic_menu_search)
                return tabView
            }
            "사진" -> {
                tabView.ivLogo.setImageResource(android.R.drawable.ic_menu_camera)
                return tabView
            }
            "전화" -> {
                tabView.ivLogo.setImageResource(android.R.drawable.ic_menu_call)
                return tabView
            }
            else -> {
                return tabView
            }
        }
    }

    private fun initViewPager(){
        val searchFragment = StikerFragment()
        searchFragment.name = "찾기 창"
        val cameraFragment = StikerFragment()
        cameraFragment.name = "사진 창"
        val callFragment = StikerFragment()
        callFragment.name = "전화 창"

        val adapter = fragmentManager?.let { StikerViewPagerAdapter(it) } // PageAdapter 생성
        adapter!!.addItems(searchFragment)
        adapter!!.addItems(cameraFragment)
        adapter!!.addItems(callFragment)

        viewPager.adapter = adapter // 뷰페이저에 adapter 장착
        tabLayout.setupWithViewPager(viewPager) // 탭레이아웃과 뷰페이저를 연동

        tabLayout.getTabAt(0)?.setCustomView(createView("찾기"))
        tabLayout.getTabAt(1)?.setCustomView(createView("사진"))
        tabLayout.getTabAt(2)?.setCustomView(createView("전화"))

    }
}