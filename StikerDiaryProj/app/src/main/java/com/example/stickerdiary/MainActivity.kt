package com.example.stickerdiary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.adapter = CalendarAdapter(setData())
    }

    //data 생성
    fun setData(): ArrayList<Data> {
        val list = arrayListOf<Data>()

        val instance = Calendar.getInstance()
        val cells : ArrayList<Date> = ArrayList()
        val calendar : Calendar = instance.clone() as Calendar

        //마지막날짜가 몇째주인지 구하기
        val lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        Log.i("월의 마지막 날",lastDay.toString())
        calendar.set(Calendar.DAY_OF_MONTH, lastDay)
        val lastWeek = calendar.get(Calendar.WEEK_OF_MONTH)
        Log.i("마지막주",lastWeek.toString())

        //오늘 날짜를 1일로 변경
        calendar.set(Calendar.DAY_OF_MONTH,1)
        val firstDay = calendar.get(Calendar.DAY_OF_WEEK) - 1 //일 : 1 ~ 토 : 7
        Log.i("1일이 무슨 요일",firstDay.toString())

        //달력에서 저번달을 표시해주기 위해
        calendar.add(Calendar.DAY_OF_MONTH, -firstDay)

        //날짜 채우기
        while (cells.size < 7*lastWeek){
            cells.add(calendar.time)
            list.add(Data(calendar.get(Calendar.DAY_OF_MONTH).toString()))
            calendar.add(Calendar.DAY_OF_MONTH,1) //저번달부터 +1하면서 add
        }
//        list.forEach {
//            Log.e("log", "$it")
//        }
        return list
    }

}