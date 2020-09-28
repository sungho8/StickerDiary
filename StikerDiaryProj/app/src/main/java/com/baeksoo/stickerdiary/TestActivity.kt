package com.baeksoo.stickerdiary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.activity_test.*
import java.util.*
import kotlin.collections.ArrayList

class TestActivity : AppCompatActivity() {
    private val dateCalculator = DateCalculator()

    // 페이지에 사용되는 뷰, 텍스트
    private var view_list = ArrayList<View>()
    private var month_list = ArrayList<String>()

    // 현재 날짜
    private val instance = Calendar.getInstance()
    private var year = instance.get(Calendar.YEAR)
    private var month = instance.get(Calendar.MONTH)

    // 페이지가 보여주는 날짜
    private var pageYear = year;
    private var pageMonth = month;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        var count = 10 * 12 // 10년치
        for(i in -1 * count .. count){
            val currentView  = layoutInflater.inflate(R.layout.activity_main,null)

            // 년도, 월 계산
            var y = year + ((i + month) / 12)
            var m = (i + month) % 12
            if(i + month < 0){
                y = year - 1 + (i + month + 1) / 12
                m = (12 + (i + month) % 12) % 12
            }

            currentView.recyclerView.adapter = CalendarAdapter(dateCalculator.setData(y, m))
            month_list.add("${y} 년 ${m + 1} 월")     // 상단 텍스트뷰
            view_list.add(currentView)                // 하단 리사이클러뷰
        }

        pager.adapter = CustomAdapter()
        pager.setCurrentItem(view_list.count() / 2)     // 시작 위치를 현재로

        pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            // 스크롤 상태가 변경되었을 때
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                var currentPage = position - view_list.count() / 2

                pageMonth = (month + currentPage) % 12
                pageYear = year + (month + currentPage) /12

                Log.e("현재 페이지 : ",currentPage.toString() );
                tv1.text = month_list[position]
            }

            // 클릭했을 때
            override fun onPageSelected(position: Int) {

            }
        })
    }

    inner class CustomAdapter : PagerAdapter() {
        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view == `object` // 3. 받은 이 두개가 일치할때만 반환.
        }

        override fun getCount(): Int {
            return view_list.size
        }

        // 항목을 구성하기 위해서 호출
        // 보여주고자 하는 뷰를 페이저 객체에 집어 넣고 반환하면 된다.
        override fun instantiateItem(container: ViewGroup, position: Int): Any { // position : 항목의 인덱스
            pager.addView(view_list[position]) // 1. 얘가 isViewFromObject의 view로 들어오고

            return view_list[position] // 2. 얘가 isViewFromObject의 `object`로 들어온다.
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            pager.removeView(`object` as View?)
        }
    }
}