package com.baeksoo.stickerdiary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.baeksoo.stickerdiary.Adapter.CalendarAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.calendar.view.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    private val dateCalculator = CalendarCalculator()

    private var view_list = ArrayList<View>()
    private var month_list = ArrayList<String>()

    private val instance = Calendar.getInstance()
    private var year = instance.get(Calendar.YEAR)
    private var month = instance.get(Calendar.MONTH)

    private var pageYear = year;
    private var pageMonth = month;

    private var scheduleList = ArrayList<Schedule>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);   // 상태바

        ReadAllSchedule()
    }

    fun ReadAllSchedule(){
        val myRef = Firebase.database.getReference("sungho0830")

        myRef.addValueEventListener(object  : ValueEventListener {
            override fun onDataChange(data: DataSnapshot) {
                for (snapshot in data.children) {
                    val schedule = snapshot.getValue(Schedule :: class.java)
                    if (schedule != null) {
                        scheduleList.add(schedule)
                    }
                }
                makeCalendar(10*12)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    fun makeCalendar(count : Int){
        for(i in -1 * count .. count){
            val currentView  = layoutInflater.inflate(R.layout.calendar,null)

            // 년도, 월 계산
            var y = year + ((i + month) / 12)
            var m = (i + month) % 12
            if(i + month < 0){
                y = year - 1 + (i + month + 1) / 12
                m = (12 + (i + month) % 12) % 12
            }

            currentView.recyclerView.adapter = CalendarAdapter(this, this, dateCalculator.setData(y, m), scheduleList, y, m+1)

            // 구분선
            val dividerItemDecoration = DividerItemDecoration(currentView.recyclerView.context, LinearLayoutManager.VERTICAL)
            val dividerItemDecoration2 = DividerItemDecoration(currentView.recyclerView.context, LinearLayoutManager.HORIZONTAL)
            dividerItemDecoration.setDrawable(getDrawable(R.drawable.divider))
            dividerItemDecoration2.setDrawable(getDrawable(R.drawable.divider))
            currentView.recyclerView.addItemDecoration(dividerItemDecoration)
            currentView.recyclerView.addItemDecoration(dividerItemDecoration2)

            month_list.add("${y} 년 ${m + 1} 월")     // 상단 텍스트뷰
            view_list.add(currentView)                // 하단 리사이클러뷰
        }

        pager.adapter = CustomPagerAdapter()
        pager.setCurrentItem(view_list.count() / 2)     // 시작 위치를 현재로

        pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                var currentPage = position - view_list.count() / 2

                pageMonth = (month + currentPage) % 12
                pageYear = year + (month + currentPage) /12
                monthtxt.text = month_list[position]
            }
            override fun onPageSelected(position: Int) {
            }
        })

        pre.setOnClickListener(View.OnClickListener {
            pager.setCurrentItem(--pager.currentItem,true);
        })
        next.setOnClickListener(View.OnClickListener {
            pager.setCurrentItem(++pager.currentItem,true);
        })
    }

    inner class CustomPagerAdapter : PagerAdapter() {
        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view == `object`
        }

        override fun getCount(): Int {
            return view_list.size
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            pager.addView(view_list[position])

            return view_list[position]
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            pager.removeView(`object` as View?)
        }
    }
}