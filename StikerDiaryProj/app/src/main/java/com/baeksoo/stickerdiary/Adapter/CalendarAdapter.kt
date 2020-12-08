package com.baeksoo.stickerdiary.Adapter

import android.content.Context
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.baeksoo.stickerdiary.*
import kotlin.collections.ArrayList

// 생성자에서 리스트 받아옴
class CalendarAdapter(val mainActivity: MainActivity, val context : Context, val list: ArrayList<Data>, val dateList : ArrayList<ArrayList<Schedule?>>,
                    val year : Int, val month : Int) : RecyclerView.Adapter<CalendarViewHolder>(){
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): CalendarViewHolder {
        // 팩토리함수를 이용한 뷰홀더 생성.
        return CalendarViewHolder.newInstance(viewGroup)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder : CalendarViewHolder, position: Int) {
        // 홀더에 정의된 함수로 뷰 그리기
        holder.onBindView(position, list)
        val slist : ArrayList<Schedule> = ArrayList()       // dialog에 리스트뷰 로 보내줄 일정
        val day = Integer.parseInt(holder.day.text.toString())

        // 간격 설정
        val layoutParams = holder.itemView.layoutParams
        //layoutParams.height = 250
        holder.itemView.requestLayout()

        // 현재 달이 아닌 날짜는 흐리게 표시
        if(position <= 6 &&  Integer.parseInt(holder.day.text.toString()) > 20){
            holder.day.alpha = 0.3f
        }else if(position >= 28 && Integer.parseInt(holder.day.text.toString()) < 15){
            holder.day.alpha = 0.3f
        }else{
            // 흐리지 않은날짜는 존재하는 일정스티커와 선을 띄운다.
            val cc = CalendarCalculator()
            val total = cc.indexDay(year, month, day).toInt()
            for(i in 0 until dateList[total].size){
                if(dateList[total][i] != null){
                    //Log.d("schedule ","day ${year}${month}${day} , Title ${dateList[total][i]!!.Title}")
                    holder.showSchedule(context,dateList[total][i], i)
                    slist.add(dateList[total][i]!!)
                }
            }

            holder.day.alpha = 1f
        }

        // 일요일 빨갛게
        if(position % 7 == 0) holder.day.setTextColor(ContextCompat.getColor(context, R.color.colorWeekend))

        // 아이템 클릭 리스너
        holder.itemView.setOnClickListener {
            val sadapter = ScheduleListAdapter(context, R.layout.clist_item, slist)

            val dialog = ScheduleDialog.CustomDialogBuilder()
                .setContext(context)
                .setMonth(month.toString())
                .setDay(holder.day.text.toString())
                .setScheduleList(sadapter).create()

            dialog.show(mainActivity.supportFragmentManager,dialog.tag)
        }
    }

//    // 0 = 일정없음 / 1 = 일정시작 / 2 = 일정종료 or 일정중
//    fun checkSchedule(holder: CalendarViewHolder, slist : ArrayList<Schedule>) : Int{
//        if(scheduleList.size == 0)
//            return 0
//
//        val day = Integer.parseInt(holder.day.text.toString())
//        var result = 0
//
//        for(schedule in scheduleList){
//            val startDay = schedule.StartDay
//
//            val syear = Integer.parseInt(startDay.substring(0,4))
//            val smonth = Integer.parseInt(startDay.substring(4,6))
//            val sday = Integer.parseInt(startDay.substring(6,8))
//
//            if(syear == year && smonth == month && sday == day){
//                holder.addList(schedule)
//                slist.add(schedule)
//                result = 1
//            }
//            else if(checkBetweenDate(schedule, year, month, day)){
//                holder.addList(schedule)
//                slist.add(schedule)
//                result = 2
//            }
//        }
//        return result
//    }
//
//    // 시작날짜와 끝날짜를 주면 중간날짜를 뽑아내는 함수 필요
//    fun checkBetweenDate(schedule : Schedule, year: Int, month: Int, day:Int) : Boolean{
//        var monthArr = arrayOf(31,28,31,30,31,30,31,31,30,31,30,31)
//        val startDay = schedule.StartDay
//        val endDay = schedule.EndDay
//
//        var syear = Integer.parseInt(startDay.substring(0,4))
//        var smonth = Integer.parseInt(startDay.substring(4,6))
//        var sday = Integer.parseInt(startDay.substring(6,8))
//
//        val eyear = Integer.parseInt(endDay.substring(0,4))
//        val emonth = Integer.parseInt(endDay.substring(4,6))
//        val eday = Integer.parseInt(endDay.substring(6,8))
//
//        while(!(syear == eyear && smonth == emonth && sday == eday)){
//            // 윤년체크
//            if(syear % 4 == 0 && syear % 100 != 0 || syear % 400 == 0)
//                monthArr[1] = 29
//            else
//                monthArr[1] = 28
//
//            // 현재일이 달을 넘어갈때
//            if(sday + 1 > monthArr[smonth - 1]){
//                sday = 1
//                smonth += 1
//                // 현재달이 12월을 넘어갈때
//                if(smonth > 12){
//                    smonth = 1
//                    syear += 1
//                }
//            }else{
//                sday += 1;
//            }
//
//            if(syear == year && smonth == month && sday == day){
//                return true;
//            }
//        }
//        return false;
//    }
}