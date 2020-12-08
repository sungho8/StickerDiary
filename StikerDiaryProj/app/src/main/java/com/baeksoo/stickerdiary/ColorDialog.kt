package com.baeksoo.stickerdiary

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import androidx.fragment.app.DialogFragment

class ColorDialog : DialogFragment(){
    var editImageView : ImageView? = null
    lateinit var listener : ColorDialogClickedListener

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_color, container,false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        view?.apply {
            var colorArr : IntArray = resources.getIntArray(R.array.colorArr_Schedule)

            for(i in 0 until 12){
                val idstr = "btnColor" + i
                val idres = resources.getIdentifier(idstr, "id", context.packageName)

                val btn = findViewById<ImageView>(idres)
                btn.setColorFilter(colorArr[i])
                btn.setOnClickListener{
                    editImageView!!.setColorFilter(colorArr[i])
                    listener.onOKClicked(i)
                    dialog!!.cancel()
                }
            }
        }
    }

    fun setOnColorClickedListener(listener: (Int) -> Unit) {
        this.listener = object: ColorDialogClickedListener {
            override fun onOKClicked(content: Int) {
                listener(content)
            }
        }
    }

    class ColorDialogBuilder {
        private val dialog = ColorDialog()

        fun setColorImage(editImageView: ImageView): ColorDialog.ColorDialogBuilder {
            dialog.editImageView = editImageView
            return this
        }

        fun create(): ColorDialog {
            return dialog
        }
    }

    interface ColorDialogClickedListener {
        fun onOKClicked(content : Int)
    }
}