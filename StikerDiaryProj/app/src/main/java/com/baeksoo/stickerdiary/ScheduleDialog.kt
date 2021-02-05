package com.baeksoo.stickerdiary

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.baeksoo.stickerdiary.Adapter.ScheduleListAdapter
import com.baeksoo.stickerdiary.Data.Schedule
import com.baeksoo.stickerdiary.Data.StickerData
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.android.synthetic.main.dialog_schedule.*

class ScheduleDialog : DialogFragment(){
    lateinit var preStickerData : StickerData
    lateinit var uid : String

    var curStickerData : StickerData? = null

    var year : Int? = null
    var month : String? = null
    var day : String? = null
    var adapter : ScheduleListAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_schedule, container,false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        view?.apply {
            if(preStickerData != null){
                var pakName = MySharedReferences.ApplicationContext().packageName
                var resName = preStickerData!!.sticker
                var imgRes = MySharedReferences.ApplicationContext().resources.getIdentifier(resName, "drawable", pakName)
                findViewById<ImageView>(R.id.ivSticker)?.setImageResource(imgRes)
            }

            findViewById<TextView>(R.id.tvDay)?.text = month + "월 " + day +"일"
            findViewById<ListView>(R.id.list)?.adapter = adapter

            findViewById<ImageView>(R.id.ivSticker)?.setOnClickListener {

                val bottomSheet = StikerBottomSheet(this@ScheduleDialog)
                bottomSheet.show(childFragmentManager, bottomSheet.tag)
            }

            findViewById<FloatingActionButton>(R.id.btnAdd)?.setOnClickListener{
                val nextIntent = Intent(context, EditActivity::class.java)
                if(month?.length == 1)
                    month = "0" + month
                if(day?.length == 1)
                    day = "0" + day

                val seday = year.toString() + month + day
                val schedule = Schedule("",0,seday, seday, "", "",  "", "")
                nextIntent.putExtra("Schedule", schedule)
                nextIntent.putExtra("uid", uid)
                startActivity(nextIntent)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if(preStickerData.sticker.equals("none")){
            if(curStickerData != null){
                FirebaseController(uid).UploadSticker(curStickerData!!)          // 새로운 스티커를 생성

                val nextIntent = Intent(context, MainActivity::class.java)
                nextIntent.putExtra("uid", uid)
                startActivity(nextIntent)
            }
        }else{
            if(curStickerData != null){

                if(curStickerData!!.sticker.equals("none"))
                    FirebaseController(uid).RemoveSticker(preStickerData.key)   // 기존 스티커 삭제
                else
                    FirebaseController(uid).UpdateSticker(preStickerData.key, curStickerData!!)      // 기존 스티커 수정

                val nextIntent = Intent(context, MainActivity::class.java)
                nextIntent.putExtra("uid", uid)
                startActivity(nextIntent)
            }

        }
    }

    fun recieveData(stickerData : StickerData){
        curStickerData = stickerData
        curStickerData!!.day = CalendarCalculator().transformDay(year!!, month!!.toInt(), day!!.toInt())

        var pakName = MySharedReferences.ApplicationContext().packageName
        var imgRes = MySharedReferences.ApplicationContext().resources.getIdentifier(stickerData.sticker, "drawable", pakName)
        ivSticker.setImageResource(imgRes)
    }

    class CustomDialogBuilder {
        private val dialog = ScheduleDialog()

        fun setContext(context : Context) : CustomDialogBuilder{
            return this
        }

        fun setuid(uid : String) : CustomDialogBuilder{
            dialog.uid = uid
            return this
        }

        fun setSticker(sticker : StickerData) : CustomDialogBuilder{
            dialog.preStickerData = sticker
            return this
        }

        fun setYear(year : Int) : CustomDialogBuilder{
            dialog.year = year
            return this
        }

        fun setMonth(month : String) : CustomDialogBuilder {
            dialog.month = month
            return this
        }

        fun setDay(day : String) : CustomDialogBuilder {
            dialog.day = day
            return this
        }

        fun setScheduleList(sadapter : ScheduleListAdapter): CustomDialogBuilder {
            dialog.adapter = sadapter
            return this
        }

        fun create(): ScheduleDialog {
            return dialog
        }
    }
}