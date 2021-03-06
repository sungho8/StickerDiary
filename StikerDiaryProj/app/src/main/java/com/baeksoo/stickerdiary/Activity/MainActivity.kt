package com.baeksoo.stickerdiary

import android.app.DatePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialog
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.baeksoo.stickerdiary.Adapter.CalendarAdapter
import com.baeksoo.stickerdiary.Data.Schedule
import com.baeksoo.stickerdiary.Data.StickerData
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
    private lateinit var progressDialog : AppCompatDialog

    private val dateCalculator = CalendarCalculator()

    private var month_list = ArrayList<String>()
    private var view_list = ArrayList<View>()

    private val instance = Calendar.getInstance()
    private var year = instance.get(Calendar.YEAR)
    private var month = instance.get(Calendar.MONTH)
    private var day = instance.get((Calendar.DATE))

    private var pageYear = year;
    private var pageMonth = month;

    private var scheduleDateList = ArrayList<ArrayList<Schedule?>>()
    private var stickerDateList = ArrayList<StickerData?>()
    private var scheduleList = ArrayList<Schedule>()
    private var stickerList = ArrayList<StickerData>()

    private val countCalendar = 10  // 현재날짜로부터 전후 몇년의 달력을 만들지

    private var uid = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(MySharedReferences.prefs.getThemeId())
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);   // 상태바
        setContentView(R.layout.activity_main)

        if(intent.hasExtra("uid"))
            uid = intent.getStringExtra("uid")

        ReadAllSticker()

        ivmOption.setOnClickListener{
            val nextIntent = Intent(this,OptionActivity::class.java)
            nextIntent.putExtra("uid",uid)
            startActivity(nextIntent)
        }

        // 오늘로 뷰페이저 이동
        ivmToday.setOnClickListener {
            pager.setCurrentItem(view_list.count() / 2 + month)
        }

        //지정한 년,달로 이동
        monthtxt.setOnClickListener {
            var listener = DatePickerDialog.OnDateSetListener { datePicker, i, i2, i3 ->
                // i2월 i3일
                pageYear = i
                pageMonth = i2

                var distancePage = (year - pageYear) * 12 + (month - pageMonth)

                pager.setCurrentItem(view_list.count() / 2 + month - distancePage)

                monthtxt.text = "${i} 년 ${i2 + 1} 월"
            }

            var datepPicker = DatePickerDialog(this,DatePickerDialog.THEME_HOLO_LIGHT, listener, pageYear, pageMonth - 1 , 1)
            datepPicker.show()

        }
    }

    // 앱을 종료하시겟습니까?
    override fun onBackPressed() {
        var builder = AlertDialog.Builder(this)
        builder.setTitle("종료")
        builder.setMessage("종료하시겠습니까?")

        var listener = object : DialogInterface.OnClickListener {
            override fun onClick(p0: DialogInterface?, p1: Int) {
                when (p1) {
                    DialogInterface.BUTTON_POSITIVE ->
                        quit()
                }
            }
        }
        builder.setPositiveButton("확인", listener)
        builder.setNegativeButton("취소", listener)
        builder.show()
    }

    fun ReadAllSticker(){
        val myRef = Firebase.database.getReference(uid).child("Sticker")

        progressON()
        myRef.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(data : DataSnapshot) {
                for(snapshot in data.children){
                    val sticker = snapshot.getValue(StickerData :: class.java)
                    if(sticker != null){
                        sticker.key = snapshot.key.toString()
                        stickerList.add(sticker)
                    }
                }
                ReadAllSchedule()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Read All Sticker Error", "스티커 불러오기 실패")
            }
        })
    }

    fun ReadAllSchedule(){
        val myRef = Firebase.database.getReference(uid).child("Schedule")

        myRef.addListenerForSingleValueEvent(object  : ValueEventListener {
            override fun onDataChange(data: DataSnapshot) {
                for (snapshot in data.children) {
                    val schedule = snapshot.getValue(Schedule :: class.java)
                    if (schedule != null) {
                        schedule.key = snapshot.key.toString()
                        scheduleList.add(schedule)
                    }
                }
                initscheduleDateList()  // 날짜배열 생성
                arrangeSchedule()       // 일정 정렬
                arrangeSticker()        // 스티커 정렬
                makeCalendar()          // calandar adapter 생성
                progressOFF()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Read All Schedule Error", "일정 불러오기 실패")
            }
        })
    }

    // 각 일정들의 막대들을 배치하기위해 초기화 작업
    fun initscheduleDateList(){
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
                    scheduleDateList.add(temp)
                    stickerDateList.add(null)
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
            val stotal = (cc.indexDay( syear, smonth, sday)).toInt()
            val etotal = (cc.indexDay( eyear, emonth, eday)).toInt()
            var scheduleLayer = 0
            for(i in stotal .. etotal){
                var isStart = false
                if(i == stotal){
                    scheduleLayer = getScheduleLayer(i)
                    isStart = true
                }else{
                    while(scheduleLayer > scheduleDateList[i].size){
                        scheduleDateList[i].add(null)
                    }
                }

                val temp = schedule.copy(isStart)
                scheduleDateList[i].add(scheduleLayer, temp)
            }
        }
    }

    fun getScheduleLayer(index : Int) : Int{
        var count = 0
        for(i in 0 until scheduleDateList[index].size){
            if(scheduleDateList[index][i] != null){
                count ++
            }
        }

        if(count >= 2)
            return scheduleDateList[index].size

        for(i in 0 until scheduleDateList[index].size){
            if(scheduleDateList[index][i] == null){
                return i
            }
        }
        return scheduleDateList[index].size
    }

    // 각 스티커를 알맞게 배치한다.
    fun arrangeSticker(){
        val sortedList = ArrayList(stickerList.sortedBy { it.day })
        for(sticker in sortedList){
            var syear = Integer.parseInt(sticker.day.substring(0,4))
            var smonth = Integer.parseInt(sticker.day.substring(4,6))
            var sday = Integer.parseInt(sticker.day.substring(6,8))

            val cc = CalendarCalculator()
            val total = cc.indexDay(syear, smonth, sday).toInt()

            stickerDateList[total] = sticker
        }
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
            currentView.recyclerView.adapter = CalendarAdapter(this, this, uid , dateCalculator.setData(y, m),
                scheduleDateList,stickerDateList, y, m + 1)

            // 구분선
            val dividerItemDecoration = DividerItemDecoration(currentView.recyclerView.context, LinearLayoutManager.VERTICAL)
            val dividerItemDecoration2 = DividerItemDecoration(currentView.recyclerView.context, LinearLayoutManager.HORIZONTAL)
            dividerItemDecoration.setDrawable(ContextCompat.getDrawable(applicationContext,R.drawable.divider)!!)
            dividerItemDecoration2.setDrawable(ContextCompat.getDrawable(applicationContext,R.drawable.divider)!!)
            currentView.recyclerView.addItemDecoration(dividerItemDecoration)
            currentView.recyclerView.addItemDecoration(dividerItemDecoration2)

            month_list.add("${y} 년 ${m + 1} 월")     // 상단 텍스트뷰
            view_list.add(currentView)                // 하단 리사이클러뷰
        }

        pager.adapter = CustomPagerAdapter()

        // edit -> main 인경우 입력한 스케쥴 날짜로
        if(intent.hasExtra("date"))
        {
            val y = 12 * (Integer.parseInt(intent.getStringExtra("date").substring(0,4)) - year)
            val m = Integer.parseInt(intent.getStringExtra("date").substring(4,6)) - (month + 1)

            pager.setCurrentItem(view_list.count() / 2 + month + (y + m))
        }
        // 시작 위치를 현재로
        else
        {
            pager.setCurrentItem(view_list.count() / 2 + month)
        }


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

    fun progressON(){
        progressDialog = AppCompatDialog(this)
        progressDialog.setCancelable(false)
        progressDialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        progressDialog.getWindow()?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        progressDialog.setContentView(R.layout.dialog_loading)
        progressDialog.show()
        var img_loading_framge = progressDialog.findViewById<ImageView>(R.id.iv_frame_loading)
        var frameAnimation = img_loading_framge?.getBackground() as AnimationDrawable

        img_loading_framge?.post(object : Runnable{
            override fun run() {
                frameAnimation.start()
            }
        })
    }

    fun progressOFF(){
        if(progressDialog != null && progressDialog.isShowing()){
            progressDialog.dismiss()
        }
    }

    fun quit(){
        moveTaskToBack(true);
        finish();
        android.os.Process.killProcess(android.os.Process.myPid());
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