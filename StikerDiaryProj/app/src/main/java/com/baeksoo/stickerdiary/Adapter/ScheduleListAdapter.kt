package com.baeksoo.stickerdiary.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import com.baeksoo.stickerdiary.Data.Schedule
import com.baeksoo.stickerdiary.EditActivity
import com.baeksoo.stickerdiary.R


class ScheduleListAdapter(context: Context, resource: Int, item: ArrayList<Schedule>, uid : String) : ArrayAdapter<Schedule>(context,resource,item){
    private val mContext = context
    private val mItem = item
    private val mResource = resource
    private val muid = uid

    //리스트에 들어갈 View를 지정해줌.
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        lateinit var viewHolder : ViewHolder
        var view = convertView
        if (view == null){
            viewHolder = ViewHolder()
            view = LayoutInflater.from(mContext).inflate(mResource,parent,false)
            viewHolder.item = view.findViewById(R.id.citem)
            viewHolder.titleTxt = view.findViewById(R.id.ctitletxt)
            viewHolder.timeTxt = view.findViewById(R.id.ctimetxt)
            viewHolder.colorIv = view.findViewById(R.id.ivColor)

            view.tag = viewHolder

            // 텍스트
            viewHolder.titleTxt.text = mItem[position].Title
            viewHolder.timeTxt.text = mItem[position].StartTime + " ~ " + mItem[position].EndTime
            // 색상
            var colorArr : IntArray = mContext.resources.getIntArray(R.array.colorArr_Schedule)
            viewHolder.colorIv.setColorFilter(colorArr[mItem[position].ColorIndex])

            // 리스트 클릭 리스너
            viewHolder.item.setOnClickListener {
                val nextIntent = Intent(mContext, EditActivity::class.java)
                val schedule = mItem[position]
                nextIntent.putExtra("Schedule", schedule)
                nextIntent.putExtra("uid", muid)
                mContext.startActivity(nextIntent)
            }

            return view
        }else{
            viewHolder = view.tag as ViewHolder
        }
        return  view
    }

    //리스트뷰의 성능을 높히기 위해 사용
    inner class ViewHolder{
        lateinit var item : LinearLayout
        lateinit var titleTxt : TextView
        lateinit var timeTxt : TextView
        lateinit var colorIv : ImageView
    }
}