package com.baeksoo.stickerdiary

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.baeksoo.stickerdiary.Adapter.StickerViewHolder
import com.baeksoo.stickerdiary.Data.Schedule
import com.baeksoo.stickerdiary.Data.StickerData
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.android.synthetic.main.sticker.*
import java.util.*


class EditActivity : AppCompatActivity() {
    private var cal = Calendar.getInstance()

    lateinit var preSticker : StickerData
    lateinit var curSticker : StickerData
    var uid = ""

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

    var colorIndex = 0
    var preScheduleKey = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(MySharedReferences.prefs.getThemeId())
        setContentView(R.layout.activity_edit)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);   // 상태바

        initView()

        btnSticker.setOnClickListener {
            val bottomSheet = StikerBottomSheet(null)

            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
        }

        btnColor.setOnClickListener {
            val dialog : ColorDialog = ColorDialog.ColorDialogBuilder()
                .setColorImage(ivColor)
                .create()

            dialog.setOnColorClickedListener{ content ->
                colorIndex = content
            }
            dialog.show(this.supportFragmentManager,dialog.tag)
        }

        btnOK.setOnClickListener{
            // Sticker
            if(curSticker != null && !curSticker.sticker.equals("none")){
                curSticker.day = CalendarCalculator().transformDay(syear,smonth,sday)
                FirebaseController(uid).UploadSticker(curSticker)
            }

            // Schedule
            val startday = CalendarCalculator().transformDay(syear,smonth,sday)
            val endday = CalendarCalculator().transformDay(eyear,emonth,eday)
            val starttime = CalendarCalculator().transformTime(shour,sminute)
            val endtime = CalendarCalculator().transformTime(ehour,eminute)

            val title = tvEditTitle.text.toString()
            val content = edtMemo.text.toString()

            val schedule = Schedule("", colorIndex, startday, endday, starttime, endtime, title, content)

            // key값이 존재한다면 일정 업데이트, 아니면 새로운 일정
            if(preScheduleKey.length > 1){
                schedule.key = preScheduleKey
                FirebaseController(uid).UpdateSchedule(schedule)
            }
            else{
                FirebaseController(uid).UploadSchedule(schedule)
            }

            val nextIntent = Intent(this, MainActivity::class.java)
            nextIntent.putExtra("uid", uid)
            nextIntent.putExtra("date", startday)
            startActivity(nextIntent)
        }

        btnCancel.setOnClickListener{
            val nextIntent = Intent(this, MainActivity::class.java)
            nextIntent.putExtra("uid", uid)
            startActivity(nextIntent)
        }
    }

    fun initView(){
        curSticker = StickerData("","none","")

        if(intent.hasExtra("uid")){
            uid = intent.getStringExtra("uid")
        }

        if(intent.hasExtra("Schedule")){
            var schedule = intent.getParcelableExtra<Schedule>("Schedule")
            val y = schedule.StartDay.substring(0,4)
            val m = schedule.StartDay.substring(4,6)
            val d = schedule.StartDay.substring(6,8)

            syear = Integer.parseInt(y)
            eyear = Integer.parseInt(y)
            smonth = Integer.parseInt(m)
            emonth = Integer.parseInt(m)
            sday = Integer.parseInt(d)
            eday = Integer.parseInt(d)
            colorIndex = schedule.ColorIndex
            preScheduleKey = schedule.key

            if(schedule.StartTime.length > 1){
                val sh = schedule.StartTime.substring(0,2)
                val sm = schedule.StartTime.substring(2,4)
                val eh = schedule.EndTime.substring(0,2)
                val em = schedule.EndTime.substring(2,4)
                shour = Integer.parseInt(sh)
                sminute = Integer.parseInt(sm)
                ehour = Integer.parseInt(eh)
                eminute = Integer.parseInt(em)
            }

            tvEditTitle.setText(schedule.Title)
            ivColor.setColorFilter(resources.getIntArray(R.array.colorArr_Schedule)[schedule.ColorIndex])
            edtMemo.setText(schedule.Content)
        }

        tvStartDate.text = "${smonth}월 ${sday}일"
        tvStartTime.text = "${shour}시 ${sminute}분"
        tvEndDate.text = "${smonth}월 ${sday}일"
        tvEndTime.text = "${shour}시 ${sminute}분"

        tvStartDate.setOnClickListener {
            var listener = DatePickerDialog.OnDateSetListener { datePicker, i, i2, i3 ->
                // i2월 i3일
                syear = i
                smonth = i2 + 1
                sday = i3
                tvStartDate.text = "${i2 + 1}월 ${i3}일"
            }

            var datepPicker = DatePickerDialog(this,DatePickerDialog.THEME_HOLO_LIGHT, listener, syear, smonth - 1, sday)
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
                emonth = i2 + 1
                eday = i3
                tvEndDate.text = "${i2 + 1}월 ${i3}일"
            }

            var datepPicker = DatePickerDialog(this,DatePickerDialog.THEME_HOLO_LIGHT, listener, eyear, emonth - 1, eday)
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

    fun receiveData(stickerData : StickerData){
        curSticker = stickerData

        var pakName = MySharedReferences.ApplicationContext().packageName
        var imgRes = MySharedReferences.ApplicationContext().resources.getIdentifier(curSticker.sticker, "drawable", pakName)
        btnSticker.setImageResource(imgRes)
    }
}