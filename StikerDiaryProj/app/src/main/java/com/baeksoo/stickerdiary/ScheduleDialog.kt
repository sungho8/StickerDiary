package com.baeksoo.stickerdiary

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.baeksoo.stickerdiary.Adapter.ScheduleListAdapter
import com.baeksoo.stickerdiary.Data.Schedule
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ScheduleDialog : DialogFragment(){
    var uid : String? = null
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
            findViewById<TextView>(R.id.tvDay)?.text = month + "월 " + day +"일"
            findViewById<ListView>(R.id.list)?.adapter = adapter

            findViewById<FloatingActionButton>(R.id.btnAdd)?.setOnClickListener{
                val nextIntent = Intent(context, EditActivity::class.java)
                if(month?.length == 1)
                    month = "0" + month
                if(day?.length == 1)
                    day = "0" + day

                val seday = year.toString() + month + day
                val schedule = Schedule(0,seday, seday, "", "",  "", "")
                nextIntent.putExtra("Schedule", schedule)
                nextIntent.putExtra("uid", uid)
                startActivity(nextIntent)
            }
        }
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