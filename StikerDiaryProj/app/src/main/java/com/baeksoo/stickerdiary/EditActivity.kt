package com.baeksoo.stickerdiary

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_edit.*
import java.util.*

class EditActivity : AppCompatActivity() {

    private var cal = Calendar.getInstance()

    var syear = cal.get(Calendar.YEAR)
    var smonth = cal.get(Calendar.MONTH)
    var sday = cal.get(Calendar.DAY_OF_MONTH)
    var shour = cal.get(Calendar.HOUR)
    var sminute = cal.get(Calendar.MINUTE)

    var eyear = cal.get(Calendar.YEAR)
    var emonth = cal.get(Calendar.MONTH)
    var eday = cal.get(Calendar.DAY_OF_MONTH)
    var ehour = cal.get(Calendar.HOUR)
    var eminute = cal.get(Calendar.MINUTE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);   // 상태바

        initView()

        ivSticker.setOnClickListener {
            val bottomSheet = StikerBottomSheet(supportFragmentManager)
            bottomSheet.show(supportFragmentManager, bottomSheet.tag) }

        btnOK.setOnClickListener{
            val startday = transformDay(syear,smonth,sday)
            val endday =transformDay(eyear,emonth,eday)
            val starttime = transformTime(shour,sminute)
            val endtime = transformTime(ehour,eminute)

            val title = tvEditTitle.text.toString()
            val content = edtMemo.text.toString()

            val schedule = Schedule(startday,endday,starttime,endtime,title,"sticker2",content)
            FirebaseController("sungho0830").UploadSchedule(schedule)
        }
    }

    fun initView(){
        if(intent.hasExtra("Schedule")){
            var schedule = intent.getParcelableExtra<Schedule>("Schedule")

            val m = schedule.StartDay.substring(0,2)
            val d = schedule.StartDay.substring(2,4)

            smonth = Integer.parseInt(m)
            emonth = Integer.parseInt(m)
            sday = Integer.parseInt(d)
            eday = Integer.parseInt(d)
        }else{

        }
        tvStartDate.text = "${smonth}월 ${sday}일"
        tvStartTime.text = "${shour}시 ${sminute}분"
        tvEndDate.text = "${smonth}월 ${sday}일"
        tvEndTime.text = "${shour}시 ${sminute}분"

        tvStartDate.setOnClickListener {
            var listener = DatePickerDialog.OnDateSetListener { datePicker, i, i2, i3 ->
                // i2월 i3일
                syear = i
                smonth = i2
                sday = i3
                tvStartDate.text = "${i2 + 1}월 ${i3}일"
            }

            var datepPicker = DatePickerDialog(this,DatePickerDialog.THEME_HOLO_LIGHT,listener, syear, smonth, sday)
            datepPicker.show()
        }

        tvStartTime.setOnClickListener{
            var listener = TimePickerDialog.OnTimeSetListener { timePicker, i, i2 ->
                shour = i
                sminute = i2
                tvStartTime.text = "${i}시 ${i2}분"
            }
            //R.style.Theme_Material_Light_Dialog_Alert.
            var timePicker = TimePickerDialog(this,3, listener, shour, sminute, false ) // true하면 24시간 제
            timePicker.show()
        }

        tvEndDate.setOnClickListener {
            var listener = DatePickerDialog.OnDateSetListener { datePicker, i, i2, i3 ->
                // i2월 i3일
                eyear = i
                ehour = i2
                eday = i3
                tvEndDate.text = "${i2 + 1}월 ${i3}일"
            }

            var datepPicker = DatePickerDialog(this,DatePickerDialog.THEME_HOLO_LIGHT,listener, eyear, emonth, eday)
            datepPicker.show()
        }

        tvEndTime.setOnClickListener{
            var listener = TimePickerDialog.OnTimeSetListener { timePicker, i, i2 ->
                ehour = i
                eminute = i2
                tvEndTime.text = "${i}시 ${i2}분"
            }
            //R.style.Theme_Material_Light_Dialog_Alert.
            var timePicker = TimePickerDialog(this,3, listener, ehour, eminute, false ) // true하면 24시간 제
            timePicker.show()
        }
    }
    fun transformDay(y : Int, m : Int, d : Int) : String{
        var yy = "" + y
        var mm = "" + m
        var dd = "" + d

        if(mm.length < 2)
            mm = "0" + mm
        if(dd.length < 2)
            dd = "0" + dd

        return yy + mm + dd
    }

    fun transformTime(h : Int, m : Int) : String{
        var hh = "" + h;
        var mm = "" + m;

        if(hh.length < 2)
            hh = "0" + hh
        if(mm.length < 2)
            mm = "0" + mm

        return hh + mm
    }
}