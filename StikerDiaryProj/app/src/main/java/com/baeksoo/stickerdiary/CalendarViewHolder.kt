package com.baeksoo.stickerdiary

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

//생성자 지정 , 상위클래스 view 넘겨줌.  super(calendarView)
class CalendarViewHolder(calendarView: View) : RecyclerView.ViewHolder(calendarView) {

    // 뷰 홀더를 상속 받고나면 생성자에서 상위 홀더에 view 를 전달.
    val day : TextView
    val sticker : ImageView
    val line1 : View
    val line2 : View
    val lineTxt1 : TextView
    val lineTxt2 : TextView

    //초기화
    init {
        this.day = calendarView.findViewById(R.id.tvDay)
        this.sticker = calendarView.findViewById(R.id.ivDay)
        this.line1 = calendarView.findViewById(R.id.line1)
        this.line2 = calendarView.findViewById(R.id.line2)
        this.lineTxt1 = calendarView.findViewById(R.id.lineTxt1)
        this.lineTxt2 = calendarView.findViewById(R.id.lineTxt2)
    }

    /**팩토리 함수 */
    companion object {
        fun newInstance(viewGroup: ViewGroup): CalendarViewHolder {
            val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.cell, viewGroup, false)
            return CalendarViewHolder(view)
        }
    }

    fun onBindView(position: Int, list: ArrayList<Data>) {
        // 데이터를 화면에 그리기.
        day.text = list[position].day
    }

    fun showSchedule(context : Context, schedule: Schedule?, i : Int){
        if (schedule == null)
            return

        if(i == 0){
            if(schedule.isStart)
                lineTxt1.text = schedule.Title
            line1.setBackgroundColor(context.getColor(R.color.colorHighLight))
        }
        else if(i == 1){
            if(schedule.isStart)
                lineTxt2.text = schedule.Title
            line2.setBackgroundColor(context.getColor(R.color.colorWeekend))
        }
    }
//
//    fun setSticker(context : Context){
//        sticker.setImageResource(R.drawable.teststicker)
//        val count = slist.size - 1
//        for(i in 0 .. count){
//            if(i == 2)
//                break
//
//            if(i == 0 && !slist[i].isLong){
//                lineTxt1.text = slist[i].Title
//            }else if(i == 1 && !slist[i].isLong){
//                lineTxt2.text = slist[i].Title
//            }
//        }
//
//        setLine(context, slist)
//    }
//

}