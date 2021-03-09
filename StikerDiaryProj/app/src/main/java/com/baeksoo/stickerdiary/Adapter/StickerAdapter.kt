package com.baeksoo.stickerdiary.Adapter

import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.baeksoo.stickerdiary.CalendarCalculator
import com.baeksoo.stickerdiary.Data.Schedule
import com.baeksoo.stickerdiary.Data.StickerData
import com.baeksoo.stickerdiary.StikerFragment
import kotlin.collections.ArrayList

class StickerAdapter(val list: ArrayList<StickerData>) : RecyclerView.Adapter<StickerViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): StickerViewHolder {
        // 팩토리함수를 이용한 뷰홀더 생성.
        return StickerViewHolder.newInstance(viewGroup)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder : StickerViewHolder, position: Int) {
        // 홀더에 정의된 함수로 뷰 그리기

        holder.onBindView(position, list)
        holder.itemView.requestLayout()
        holder.itemView.setOnClickListener{
            itemClickListener.onClick(it, position)
        }
    }

    //ClickListener
    interface OnItemClickListener {
        fun onClick(v: View, position: Int)
    }
    private lateinit var itemClickListener : OnItemClickListener

    fun setItemClickListener(itemClickListener: OnItemClickListener) {
        this.itemClickListener = itemClickListener
    }
}
