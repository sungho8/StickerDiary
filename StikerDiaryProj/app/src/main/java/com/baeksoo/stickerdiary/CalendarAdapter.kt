package com.baeksoo.stickerdiary

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

//생성자에서 리스트 받아옴
class CalendarAdapter(val list: ArrayList<Data>) : RecyclerView.Adapter<CalendarViewHolder>(){
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CalendarViewHolder {
        //팩토리함수를 이용한 뷰홀더 생성.
        return CalendarViewHolder.newInstance(p0)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        //간격 설정
        val layoutParams = holder.itemView.layoutParams
        layoutParams.height = 250
        holder.itemView.requestLayout()

        // 홀더에 정의된 함수로 뷰 그리기
        holder.onBindView(position, list)
    }
}