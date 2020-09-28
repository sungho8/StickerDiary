package com.baeksoo.stickerdiary

import android.util.Log
import java.util.*
import kotlin.collections.ArrayList

class DateCalculator{

    //data 생성
    fun setData(_year : Int, _month : Int): ArrayList<Data> {
        val list = arrayListOf<Data>()

        val instance = Calendar.getInstance()
        val cells : ArrayList<Date> = ArrayList()
        val calendar : Calendar = instance.clone() as Calendar

        calendar.set(Calendar.YEAR, _year)
        calendar.set(Calendar.MONTH, _month)

        //마지막날짜가 몇째주인지 구하기
        val lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        Log.i("월의 마지막 날", lastDay.toString())
        calendar.set(Calendar.DAY_OF_MONTH, lastDay)
        val lastWeek = calendar.get(Calendar.WEEK_OF_MONTH)
        Log.i("마지막주", lastWeek.toString())

        //오늘 날짜를 1일로 변경
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        val firstDay = calendar.get(Calendar.DAY_OF_WEEK) - 1 //일 : 1 ~ 토 : 7
        Log.i("1일이 무슨 요일", firstDay.toString())

        //달력에서 저번달을 표시해주기 위해
        calendar.add(Calendar.DAY_OF_MONTH, - firstDay)

        //날짜 채우기
        while (cells.size < 7 * lastWeek){
            cells.add(calendar.time)
            list.add(Data(calendar.get(Calendar.DAY_OF_MONTH).toString()))
            calendar.add(Calendar.DAY_OF_MONTH,1) //저번달부터 +1하면서 add
        }

        return list
    }

}