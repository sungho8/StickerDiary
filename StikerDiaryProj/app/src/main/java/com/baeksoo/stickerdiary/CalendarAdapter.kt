package com.baeksoo.stickerdiary

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.collections.ArrayList

//생성자에서 리스트 받아옴
class CalendarAdapter(val context : Context, val list: ArrayList<Data>) : RecyclerView.Adapter<CalendarViewHolder>(){

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): CalendarViewHolder {
        //팩토리함수를 이용한 뷰홀더 생성.
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
        layoutParams.height = 250
        holder.itemView.requestLayout()

        if(position <= 6 &&  Integer.parseInt(holder.day.text.toString()) > 20){
                Log.i("1일보다 전",holder.day.text.toString())
                holder.day.alpha = 0.3f
        }else if(position >= 28 && Integer.parseInt(holder.day.text.toString()) < 15){
            Log.i("31일보다 후",holder.day.text.toString())
            holder.day.alpha = 0.3f
        }else{
            holder.day.alpha = 1f
        }

        //일요일 빨갛게 빨갛게 물들었네
        if(position%7 == 0) holder.day.setTextColor(Color.parseColor("#ff1200"))

        //아이템 클릭
        holder.itemView.setOnClickListener {

            //다이얼로그
            val builder = AlertDialog.Builder(context)
            val popupView = LayoutInflater.from(context).inflate(R.layout.popup_schedule,null)
            val tvTitle = popupView.findViewById<TextView>(R.id.tvTitle)
            val btnEdit = popupView.findViewById<Button>(R.id.btnEdit)
            builder.setView(popupView).show()

        }

    }



}