package com.baeksoo.stickerdiary

import com.baeksoo.stickerdiary.Data.Data
import java.util.*
import kotlin.collections.ArrayList

class CalendarCalculator{
    //data 생성
    fun setData(_year : Int, _month : Int): ArrayList<Data> {
        val list = arrayListOf<Data>()

        val instance = Calendar.getInstance()
        val cells : ArrayList<Date> = ArrayList()
        val calendar : Calendar = instance.clone() as Calendar

        calendar.set(Calendar.YEAR, _year)
        calendar.set(Calendar.MONTH, _month)

        //오늘 날짜를 1일로 변경
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        val firstDay = calendar.get(Calendar.DAY_OF_WEEK) - 1 //일 : 1 ~ 토 : 7

        //달력에서 저번달을 표시해주기 위해
        calendar.add(Calendar.DAY_OF_MONTH, - firstDay)

        //날짜 채우기
        while (cells.size < 7 * 6){
            cells.add(calendar.time)
            list.add(Data(calendar.get(Calendar.DAY_OF_MONTH).toString()))
            calendar.add(Calendar.DAY_OF_MONTH,1) //저번달부터 +1하면서 add
        }
        return list
    }

    // 입력받은 날짜 - 현재날짜로부터 10년전 1월 1일 날짜를 뺀값을 반환
    fun indexDay(y: Int, m: Int, d: Int) : Long{
        var currentYear = Calendar.getInstance().get(Calendar.YEAR)
        return totalDay(y,m,d) - totalDay(currentYear - 10,1,1)
    }

    // 날짜끼리 서로 비교하기위한 날짜합
    fun totalDay(y : Int, m:Int, d:Int) : Long
    {
        var monthArr = arrayOf(31,28,31,30,31,30,31,31,30,31,30,31)
        var total = (y - 1) * 365L + (y - 1) / 4 - (y - 1) / 100 + (y - 1) / 400;

        if((y % 4 == 0 && y % 100 != 0) || y % 400 == 0)
            monthArr[1] = 29
        else
            monthArr[1] = 28

        for(i in 0 .. m-1)
            total += monthArr[i];

        total += d;

        return total;
    }

}