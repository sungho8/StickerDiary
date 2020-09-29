package com.baeksoo.stickerdiary

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

//생성자에서 리스트 받아옴
class CalendarAdapter(val context : Context, val list: ArrayList<Data>) : RecyclerView.Adapter<CalendarViewHolder>(){

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): CalendarViewHolder {
        return CalendarViewHolder.newInstance(viewGroup)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        // 홀더에 정의된 함수로 뷰 그리기
        holder.onBindView(position, list)

        //간격 설정
        val layoutParams = holder.itemView.layoutParams
        layoutParams.height = 350
        holder.itemView.requestLayout()

        //일요일 빨갛게 빨갛게 물들었네
        if(position%7 == 0) holder.day.setTextColor(Color.parseColor("#ff1200"))

        //아이템 클릭
//        holder.itemView.setOnClickListener {
//            Log.i("클릭","클릭"+ holder.day.text.toString())
//
//        }

    }


}