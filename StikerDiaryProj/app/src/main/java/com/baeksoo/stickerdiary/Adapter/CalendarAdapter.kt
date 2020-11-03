package com.baeksoo.stickerdiary.Adapter

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.baeksoo.stickerdiary.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList

// 생성자에서 리스트 받아옴
class CalendarAdapter(val mainActivity: MainActivity, val context : Context, val list: ArrayList<Data>, val scheduleList : ArrayList<Schedule>,
                    val year : Int, val month : Int) : RecyclerView.Adapter<CalendarViewHolder>(){
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): CalendarViewHolder {
        // 팩토리함수를 이용한 뷰홀더 생성.
        return CalendarViewHolder.newInstance(
            viewGroup
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        // 홀더에 정의된 함수로 뷰 그리기
        holder.onBindView(position, list)
        checkSchedule(holder,position)
        // 간격 설정
        val layoutParams = holder.itemView.layoutParams
        layoutParams.height = 250
        holder.itemView.requestLayout()

        // 현재 달이 아닌 날짜는 흐리게 표시
        if(position <= 6 &&  Integer.parseInt(holder.day.text.toString()) > 20){
                holder.day.alpha = 0.3f
        }else if(position >= 28 && Integer.parseInt(holder.day.text.toString()) < 15){
            holder.day.alpha = 0.3f
        }else{
            holder.day.alpha = 1f
        }

        // 일요일 빨갛게
        if(position % 7 == 0) holder.day.setTextColor(Color.parseColor("#ff1200"))

        // 아이템 클릭 리스너
        holder.itemView.setOnClickListener {
            val item = Array(10,{ i -> "$i + list" })
            val sadapter = ScheduleListAdapter(context, R.layout.clist_item,item)

            val dialog = CustomDialog.CustomDialogBuilder()
                .setTitle(mainActivity.monthtxt.text.toString().substring(6) +" "+ holder.day.text.toString()+" 일")
                .setScheduleList(sadapter).create()

            dialog.show(mainActivity.supportFragmentManager,dialog.tag)
        }
    }

    fun checkSchedule(holder: CalendarViewHolder, position: Int){
        if(scheduleList.size == 0)
            return

        val day = Integer.parseInt(holder.day.text.toString())

        for(schedule in scheduleList){
            val startday = schedule.StartDay

            val syear = Integer.parseInt(startday.substring(0,4))
            val smonth = Integer.parseInt(startday.substring(4,6))
            val sday = Integer.parseInt(startday.substring(6))

            if(syear == year && smonth == month && sday == day){
                holder.sticker.setImageResource(R.drawable.teststicker)
            }
        }
    }
}