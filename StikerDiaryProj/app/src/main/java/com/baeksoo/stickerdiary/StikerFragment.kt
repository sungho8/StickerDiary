package com.baeksoo.stickerdiary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.baeksoo.stickerdiary.Adapter.StickerAdapter
import com.baeksoo.stickerdiary.Data.StickerData
import kotlinx.android.synthetic.main.fragment_tab.view.*

class StikerFragment(val index: Int, val count: Int, val scheduleDialog : ScheduleDialog?) : Fragment() {
    var name = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tab, container, false)
        var list : ArrayList<StickerData> = ArrayList()

        list.add(StickerData("","none", ""))   // 스티커 취소용
        for(i in 0 until count)
            list.add(StickerData("","sticker" + index + "_" + i, ""))

        val adapter = StickerAdapter(list)
        // adapter listener
        adapter.setItemClickListener(object : StickerAdapter.OnItemClickListener {
            override fun onClick(v: View, position: Int) {
                val sticker = list[position]

                if (scheduleDialog == null) {
                    val editActivity = activity as EditActivity
                    editActivity.receiveData(sticker)
                } else {
                    val args = Bundle()
                    args.putString("sticker", sticker.sticker)
                    val dialogFragment = scheduleDialog
                    dialogFragment.recieveData(sticker)
                }
            }
        })

        view.stikerRecylerView.adapter = adapter

        return view
    }
}