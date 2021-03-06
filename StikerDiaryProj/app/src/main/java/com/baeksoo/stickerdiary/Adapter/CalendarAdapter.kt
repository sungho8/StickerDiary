package com.baeksoo.stickerdiary.Adapter

import android.content.Context
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.baeksoo.stickerdiary.CalendarCalculator
import com.baeksoo.stickerdiary.Data.Data
import com.baeksoo.stickerdiary.Data.Schedule
import com.baeksoo.stickerdiary.Data.StickerData
import com.baeksoo.stickerdiary.MainActivity
import com.baeksoo.stickerdiary.R
import com.baeksoo.stickerdiary.ScheduleDialog
import kotlinx.android.synthetic.main.cell.view.*
import java.util.*
import kotlin.collections.ArrayList

// 생성자에서 리스트 받아옴
class CalendarAdapter(
    val mainActivity: MainActivity,
    val context: Context,
    val uid: String,
    val list: ArrayList<Data>,
    val scheduleDateList: ArrayList<ArrayList<Schedule?>>,
    val stickerDateList: ArrayList<StickerData?>,
    val year: Int,
    val month: Int
) : RecyclerView.Adapter<CalendarViewHolder>(){
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): CalendarViewHolder {
        // 팩토리함수를 이용한 뷰홀더 생성.
        return CalendarViewHolder.newInstance(viewGroup)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        // 홀더에 정의된 함수로 뷰 그리기
        holder.onBindView(position, list)
        val slist : ArrayList<Schedule> = ArrayList()       // dialog에 리스트뷰 로 보내줄 일정
        val day = Integer.parseInt(holder.day.text.toString())

        // 간격 설정
//        val layoutParams = holder.itemView.layoutParams
//        //layoutParams.height = 250

        holder.itemView.requestLayout()

        // 현재 달이 아닌 날짜는 흐리게 표시
        if(position <= 6 &&  Integer.parseInt(holder.day.text.toString()) > 20){
            holder.day.alpha = 0.3f
        }else if(position >= 28 && Integer.parseInt(holder.day.text.toString()) < 15){
            holder.day.alpha = 0.3f
        }else{
            holder.day.alpha = 1f

            // 흐리지 않은날짜는 존재하는 일정스티커와 선을 띄운다.
            val cc = CalendarCalculator()
            val total = cc.indexDay(year, month, day).toInt()
            var sticker = StickerData("","none", "")

            // 일정
            for(i in 0 until scheduleDateList[total].size){
                if(scheduleDateList[total][i] != null){
                    holder.showSchedule(scheduleDateList[total][i], i)
                    slist.add(scheduleDateList[total][i]!!)
                }
            }
            // 스티커
            if(stickerDateList[total] != null){
                sticker = stickerDateList[total]!!
                holder.showSticker(stickerDateList[total])
            }

            // 일요일 빨갛게, 오늘 구분선
            if(position % 7 == 0) holder.day.setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.colorWeekend
                )
            )
            if(isToday(holder)) holder.itemView.relativeLayout.setBackgroundResource(R.drawable.divider_today)

            // 아이템 클릭 리스너
            holder.itemView.setOnClickListener {
                val sadapter = ScheduleListAdapter(context, R.layout.clist_item, slist, uid)

                val dialog = ScheduleDialog.CustomDialogBuilder()
                    .setContext(context)
                    .setuid(uid)
                    .setSticker(sticker)
                    .setYear(year)
                    .setMonth(month.toString())
                    .setDay(holder.day.text.toString())
                    .setScheduleList(sadapter).create()

                dialog.show(mainActivity.supportFragmentManager, dialog.tag)
            }
        }
    }

    fun isToday(holder: CalendarViewHolder) : Boolean {
        if(Calendar.getInstance().get(Calendar.YEAR) == year && Calendar.getInstance().get(Calendar.MONTH) + 1 == month &&
            Calendar.getInstance().get(Calendar.DAY_OF_MONTH) == Integer.parseInt(holder.day.text.toString()))
            return true
        return false
    }
}