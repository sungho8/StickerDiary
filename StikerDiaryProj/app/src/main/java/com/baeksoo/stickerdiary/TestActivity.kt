package com.baeksoo.stickerdiary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.activity_test.*
import java.util.*
import kotlin.collections.ArrayList

class TestActivity : AppCompatActivity() {
    private val dateCalculator = DateCalculator()

    // ?�이지???�용?�는 �? ?�스??
    private var view_list = ArrayList<View>()
    private var month_list = ArrayList<String>()

    // ?�재 ?�짜
    private val instance = Calendar.getInstance()
    private var year = instance.get(Calendar.YEAR)
    private var month = instance.get(Calendar.MONTH)

    // ?�이지가 보여주는 ?�짜
    private var pageYear = year;
    private var pageMonth = month;


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

        //구분??
        val dividerItemDecoration = DividerItemDecoration(currentView.recyclerView.context, LinearLayoutManager.VERTICAL)
        dividerItemDecoration.setDrawable(getDrawable(R.drawable.divider));
        currentView.recyclerView.addItemDecoration(dividerItemDecoration)

        val dividerItemDecoration2 = DividerItemDecoration(currentView.recyclerView.context, LinearLayoutManager.HORIZONTAL)
        dividerItemDecoration2.setDrawable(getDrawable(R.drawable.divider));
        currentView.recyclerView.addItemDecoration(dividerItemDecoration2)


        view_list.add(preView)
        view_list.add(currentView)
        view_list.add(nextView)

        pager.adapter = CustomAdapter()

        pager.setCurrentItem(1)

        pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            // ?�크�??�태가 변경되?�을 ??
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                tv1.text = "${year} ??${month + position} ??
            }

            // ?�릭?�을 ??
            override fun onPageSelected(position: Int) {

            }
        })
    }

    inner class CustomAdapter : PagerAdapter() {
        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view == `object` // 3. 받�? ???�개가 ?�치?�때�?반환.
        }

        override fun getCount(): Int {
            return view_list.size
        }

        // ??��??구성?�기 ?�해???�출
        // 보여주고???�는 뷰�? ?�이?� 객체??집어 ?�고 반환?�면 ?�다.
        override fun instantiateItem(container: ViewGroup, position: Int): Any { // position : ??��???�덱??
            pager.addView(view_list[position]) // 1. ?��? isViewFromObject??view�??�어?�고

            return view_list[position] // 2. ?��? isViewFromObject??`object`�??�어?�다.
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            pager.removeView(`object` as View?)
        }
    }
}