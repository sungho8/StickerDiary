package com.baeksoo.stickerdiary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_option.*

class OptionActivity : AppCompatActivity() {
    companion object { lateinit var prefs: PreferenceUtil }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(MySharedReferences.prefs.getThemeId())
        setContentView(R.layout.activity_option)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);   // 상태바

        initRadio()
    }

    fun initRadio(){
        when(MySharedReferences.prefs.getThemeId()){
            R.style.Theme0 -> radio0.isChecked = true
            R.style.Theme1 -> radio1.isChecked = true
            R.style.Theme2 -> radio2.isChecked = true
            R.style.Theme3 -> radio3.isChecked = true
            R.style.Theme4 -> radio4.isChecked = true
            R.style.Theme5 -> radio5.isChecked = true
            R.style.Theme6 -> radio6.isChecked = true
            R.style.Theme7 -> radio7.isChecked = true
        }

        radioGroup.setOnCheckedChangeListener{radioGroup, checkedId->
            when(checkedId){
                R.id.radio0 -> MySharedReferences.prefs.setString("Theme","Theme0")
                R.id.radio1 -> MySharedReferences.prefs.setString("Theme","Theme1")
                R.id.radio2 -> MySharedReferences.prefs.setString("Theme","Theme2")
                R.id.radio3 -> MySharedReferences.prefs.setString("Theme","Theme3")
                R.id.radio4 -> MySharedReferences.prefs.setString("Theme","Theme4")
                R.id.radio5 -> MySharedReferences.prefs.setString("Theme","Theme5")
                R.id.radio6 -> MySharedReferences.prefs.setString("Theme","Theme6")
                R.id.radio7 -> MySharedReferences.prefs.setString("Theme","Theme7")
            }
        }
    }

    override fun onBackPressed() {
        val nextIntent = Intent(this, MainActivity::class.java)
        nextIntent.putExtra("uid",intent.getStringExtra("uid"))
        startActivity(nextIntent)
    }
}