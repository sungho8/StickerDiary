package com.baeksoo.stickerdiary

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

//생성자 지정 , 상위클래스 view 넘겨줌.  super(calendarView)
class ViewHolder(calendarView: View) : RecyclerView.ViewHolder(calendarView) {

    // 뷰 홀더를 상속 받고나면 생성자에서 상위 홀더에 view 를 전달.
    val day: TextView

    //초기화
    init {
        this.day = calendarView.findViewById(R.id.tvDay)
    }

    /**팩토리 함수 */
    companion object {
        fun newInstance(viewGroup: ViewGroup): ViewHolder {
            val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.calendar, viewGroup, false)
            return ViewHolder(view)
        }
    }

    fun onBindView(position: Int, list: ArrayList<Data>) {
        // 데이터를 화면에 그리기.
        day.text = list[position].day
    }
}