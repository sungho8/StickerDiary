package com.baeksoo.stickerdiary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.adapter = CalendarAdapter(setData())
    }

    //data 생성
    fun setData(): ArrayList<Data> {
        val list = arrayListOf<Data>()
        for (i in 1 until 90) {
            list.add(Data("$i day"))
        }
        list.forEach {
            Log.e("log", "$it")
        }
        return list
    }

}
