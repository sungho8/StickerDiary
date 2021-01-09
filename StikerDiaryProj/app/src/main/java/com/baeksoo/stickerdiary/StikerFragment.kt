package com.baeksoo.stickerdiary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.baeksoo.stickerdiary.Adapter.StickerAdapter
import com.baeksoo.stickerdiary.Data.StickerData
import kotlinx.android.synthetic.main.calendar.view.*
import kotlinx.android.synthetic.main.fragment_tab.view.*

class StikerFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tab, container, false)
        //view.tvText.text = name
        var list : ArrayList<StickerData> = ArrayList()

        list.add(StickerData("스티커1"))

        view.stikerRecylerView.adapter = StickerAdapter(list)
        return view
    }


}