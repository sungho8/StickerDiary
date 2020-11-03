package com.baeksoo.stickerdiary

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.DatePicker
import android.widget.Spinner
import kotlinx.android.synthetic.main.activity_edit.*
import java.text.SimpleDateFormat
import java.util.*

class EditActivity : AppCompatActivity() {

    private var cal = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);   // 상태바

        tvStartDate.setOnClickListener {
            var year = cal.get(Calendar.YEAR)
            var month = cal.get(Calendar.MONTH)
            var day = cal.get(Calendar.DAY_OF_MONTH)

            var listener = DatePickerDialog.OnDateSetListener { datePicker, i, i2, i3 ->
                // i2월 i3일
                tvStartDate.text = "${i2 + 1}월 ${i3}일"
            }

            var datepPicker = DatePickerDialog(this,DatePickerDialog.THEME_HOLO_LIGHT,listener, year, month, day)
            datepPicker.show()
        }

        tvStartTime.setOnClickListener{
            var hour = cal.get(Calendar.HOUR)
            var minute = cal.get(Calendar.MINUTE)

            var listener = TimePickerDialog.OnTimeSetListener { timePicker, i, i2 ->
                tvStartTime.text = "${i}시 ${i2}분"
            }


            //R.style.Theme_Material_Light_Dialog_Alert.
            var timePicker = TimePickerDialog(this,3, listener, hour, minute, false ) // true하면 24시간 제
            timePicker.show()
        }

        tvEndDate.setOnClickListener {
            var year = cal.get(Calendar.YEAR)
            var month = cal.get(Calendar.MONTH)
            var day = cal.get(Calendar.DAY_OF_MONTH)

            var listener = DatePickerDialog.OnDateSetListener { datePicker, i, i2, i3 ->
                // i2월 i3일
                tvStartDate.text = "${i2 + 1}월 ${i3}일"
            }

            var datepPicker = DatePickerDialog(this,DatePickerDialog.THEME_HOLO_LIGHT,listener, year, month, day)
            datepPicker.show()
        }

        tvEndTime.setOnClickListener{
            var hour = cal.get(Calendar.HOUR)
            var minute = cal.get(Calendar.MINUTE)

            var listener = TimePickerDialog.OnTimeSetListener { timePicker, i, i2 ->
                tvStartTime.text = "${i}시 ${i2}분"
            }


            //R.style.Theme_Material_Light_Dialog_Alert.
            var timePicker = TimePickerDialog(this,3, listener, hour, minute, false ) // true하면 24시간 제
            timePicker.show()
        }
    }
}