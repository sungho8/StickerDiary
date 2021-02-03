package com.baeksoo.stickerdiary.Adapter

import android.content.Context
import android.media.Image
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.baeksoo.stickerdiary.Data.Data
import com.baeksoo.stickerdiary.Data.Schedule
import com.baeksoo.stickerdiary.Data.StickerData
import com.baeksoo.stickerdiary.EditActivity
import com.baeksoo.stickerdiary.MainActivity
import com.baeksoo.stickerdiary.MySharedReferences
import com.baeksoo.stickerdiary.R

//생성자 지정 , 상위클래스 view 넘겨줌.  super(calendarView)
class StickerViewHolder(stickerView: View) : RecyclerView.ViewHolder(stickerView) {
    var ivSticker : ImageView
    //초기화
    init {
        ivSticker = stickerView.findViewById(R.id.ivStickerImg)
    }

    /**팩토리 함수 */
    companion object {
        fun newInstance(viewGroup: ViewGroup): StickerViewHolder {
            val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.sticker, viewGroup, false)
            return StickerViewHolder(view)
        }
    }

    fun onBindView(position: Int, list: ArrayList<StickerData>) {
        var pakName = MySharedReferences.ApplicationContext().packageName
        var resName = list[position].sticker

        var imgRes = MySharedReferences.ApplicationContext().resources.getIdentifier(resName, "drawable", pakName)
        ivSticker.setImageResource(imgRes)
    }
}