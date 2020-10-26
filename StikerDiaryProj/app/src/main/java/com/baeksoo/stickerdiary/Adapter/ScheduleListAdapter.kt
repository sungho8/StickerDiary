package com.baeksoo.stickerdiary.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.baeksoo.stickerdiary.R

class ScheduleListAdapter(context: Context, resource: Int, item: Array<String>) : ArrayAdapter<String>(context,resource,item){
    private val mContext = context
    private val mItem = item
    private val mResource = resource

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
            view.tag = viewHolder

            viewHolder.titleTxt.text = mItem[position]

            // 리스트 클릭 리스너
            viewHolder.item.setOnClickListener {
                // 수정페이지로 넘어가면됨
                Log.d("dd" , viewHolder.titleTxt.text.toString())
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
    }
}