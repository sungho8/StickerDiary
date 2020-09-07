package com.example.stickerdiary

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

//생성자에서 리스트 받아옴
class CalendarAdapter(val list: ArrayList<Data>) : RecyclerView.Adapter<ViewHolder>(){
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        //팩토리함수를 이용한 뷰홀더 생성.
        return ViewHolder.newInstance(p0)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        // 홀더에 정의된 함수로 뷰 그리기
        p0.onBindView(p1, list)
    }
}