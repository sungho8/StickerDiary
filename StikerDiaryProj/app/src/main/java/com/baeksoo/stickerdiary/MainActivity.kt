package com.baeksoo.stickerdiary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    val instance = Calendar.getInstance();
    var year = instance.get(Calendar.YEAR).toInt()
    var month = instance.get(Calendar.MONTH).toInt()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dateCalculator = DateCalculator()

        recyclerView.adapter = CalendarAdapter(dateCalculator.setData(year,month))
    }
}
