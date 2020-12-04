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

    private var month_list = ArrayList<String>()
    private var view_list = ArrayList<View>()

    private val instance = Calendar.getInstance()
    private var year = instance.get(Calendar.YEAR)
    private var month = instance.get(Calendar.MONTH)

    private var pageYear = year;
    private var pageMonth = month;

    private var dateList = ArrayList<ArrayList<Schedule?>>()
    private var scheduleList = ArrayList<Schedule>()

    private val countCalendar = 10  // 현재날짜로부터 전후 몇년의 달력을 만들지

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);   // 상태바

        ReadAllSchedule()
    }

    fun ReadAllSchedule(){
        val myRef = Firebase.database.getReference("sungho0830").child("Schedule")

        myRef.addValueEventListener(object  : ValueEventListener {
            override fun onDataChange(data: DataSnapshot) {
                for (snapshot in data.children) {
                    val schedule = snapshot.getValue(Schedule :: class.java)
                    if (schedule != null) {
                        scheduleList.add(schedule)
                    }
                }
                initDateList()
                arrangeSchedule()
                makeCalendar()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    // 각 일정들의 막대들을 배치하기위해 초기화 작업
    fun initDateList(){
        // year
        for(i in -1 * countCalendar .. countCalendar){
            var monthArr = arrayOf(31,28,31,30,31,30,31,31,30,31,30,31)

            if(((i + year) % 4 == 0 && (i + year) % 100 != 0) || (i + year) % 400 == 0)
                monthArr[1] = 29
            else
                monthArr[1] = 28


            // month
            for(j in 0 .. 11){
                val m = j % 12    // 0 ~ 11
                // day
                for(k in 1 .. monthArr[m]){
                    var temp = ArrayList<Schedule?>()
                    dateList.add(temp)
                }
            }
        }
    }

    // 각 일정의 막대들을 알맞게 배치한다.
    fun arrangeSchedule(){
        // 1. 시작날짜순으로 정렬
        val sortedList = ArrayList(scheduleList.sortedBy { it.StartDay })

        for(schedule in sortedList){
            var syear = Integer.parseInt(schedule.StartDay.substring(0,4))
            var smonth = Integer.parseInt(schedule.StartDay.substring(4,6))
            var sday = Integer.parseInt(schedule.StartDay.substring(6,8))

            var eyear = Integer.parseInt(schedule.EndDay.substring(0,4))
            var emonth = Integer.parseInt(schedule.EndDay.substring(4,6))
            var eday = Integer.parseInt(schedule.EndDay.substring(6,8))

            val cc = CalendarCalculator()
            val stotal = (cc.indexDay(syear,smonth,sday)).toInt()
            val etotal = (cc.indexDay(eyear,emonth,eday)).toInt()

            var scheduleLayer = 0
            for(i in stotal .. etotal){
                var isStart = false
                if(i == stotal){
                    scheduleLayer = getScheduleLayer(i)
                    isStart = true
                }else{
                    while(scheduleLayer > dateList[i].size){
                        dateList[i].add(null)
                    }
                }

                val temp = schedule.copy(isStart)
                dateList[i].add(scheduleLayer, temp)
            }
        }
    }

    fun getScheduleLayer(index : Int) : Int{
        for(i in 0 until dateList[index].size){
            if(dateList[index][i] == null){
                return i
            }
        }
        return dateList[index].size
    }

    fun makeCalendar(){
        val count = countCalendar * 12
        for(i in -1 * count .. count){
            val currentView  = layoutInflater.inflate(R.layout.calendar,null)

            // 년도, 월 계산
            var y = year + (i / 12)
            var m = i % 12
            if(i  < 0){
                y = year - 1 + (i  + 1) / 12
                m = (12 + i  % 12) % 12
            }
            currentView.recyclerView.adapter = CalendarAdapter(this, this, dateCalculator.setData(y, m),
                dateList, y, m + 1)

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
        pager.setCurrentItem(view_list.count() / 2 + month)     // 시작 위치를 현재로

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