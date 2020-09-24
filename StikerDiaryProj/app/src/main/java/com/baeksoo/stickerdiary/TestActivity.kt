package com.baeksoo.stickerdiary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.activity_test.*
import java.util.*
import kotlin.collections.ArrayList

class TestActivity : AppCompatActivity() {
    private val dateCalculator = DateCalculator()
    private var view_list = ArrayList<View>()
    private val instance = Calendar.getInstance();
    private var year = instance.get(Calendar.YEAR).toInt()
    private var month = instance.get(Calendar.MONTH).toInt()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        val preView = layoutInflater.inflate(R.layout.activity_main,null)
        val currentView  = layoutInflater.inflate(R.layout.activity_main,null)
        val nextView = layoutInflater.inflate(R.layout.activity_main,null)

        preView.recyclerView.adapter = CalendarAdapter(dateCalculator.setData(year,month-1))
        currentView.recyclerView.adapter = CalendarAdapter(dateCalculator.setData(year,month))
        nextView.recyclerView.adapter = CalendarAdapter(dateCalculator.setData(year,month+1))


        view_list.add(preView)
        view_list.add(currentView)
        view_list.add(nextView)

        pager.adapter = CustomAdapter()
        pager.setCurrentItem(1)

        pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            // 스크롤 상태가 변경되었을 때
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                tv1.text = "${year} 년 ${month + position} 월"
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