package com.baeksoo.stickerdiary

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
import kotlinx.android.synthetic.main.dialog_schedule.*

class CustomDialog : DialogFragment(){
    var title: String? = null
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
            findViewById<TextView>(R.id.tvDay)?.text = title
            findViewById<ListView>(R.id.list)?.adapter = adapter

//            findViewById<Button>(R.id.btn_negative)?.setOnClickListener {
//                dismiss()
//                listener?.onClickPositiveBtn()
//            }
        }
    }

    class CustomDialogBuilder {

        private val dialog = CustomDialog()

        fun setTitle(title: String): CustomDialogBuilder {
            dialog.title = title
            return this
        }
        fun setScheduleList(sadapter: ScheduleListAdapter): CustomDialogBuilder {
            dialog.adapter = sadapter
            return this
        }
//
//        fun setPositiveBtnText(text: String): CustomDialogBuilder {
//            dialog.positiveBtnText = text
//            return this
//        }
//
//        fun setNegativeBtnText(text: String): CustomDialogBuilder {
//            dialog.negativeBtnText = text
//            return this
//        }
//
//        fun setBtnClickListener(listener: CustomDialogListener): CustomDialogBuilder {
//            dialog.listener = listener
//            return this
//        }

        fun create(): CustomDialog {
            return dialog
        }
    }
}