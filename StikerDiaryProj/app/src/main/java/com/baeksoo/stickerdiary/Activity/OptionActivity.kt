package com.baeksoo.stickerdiary

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_option.*
import kotlinx.android.synthetic.main.activity_option.btnCancel

class OptionActivity : AppCompatActivity() {
    lateinit var firebaseAuth : FirebaseAuth
    lateinit var googleSignInClient : GoogleSignInClient

    var uid = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(MySharedReferences.prefs.getThemeId())
        setContentView(R.layout.activity_option)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);   // 상태바

        init()
    }

    fun init(){
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        firebaseAuth = FirebaseAuth.getInstance()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        if(intent.hasExtra("uid")){
            uid = intent.getStringExtra("uid")
        }

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

        btnCancel.setOnClickListener{
            val nextIntent = Intent(this, MainActivity::class.java)
            nextIntent.putExtra("uid", uid)
            startActivity(nextIntent)
        }

        btnLogout.setOnClickListener {
            var builder = AlertDialog.Builder(this)
            builder.setTitle("로그 아웃")
            builder.setMessage("구글계정만 일정이 유지됩니다")

            var listener = object : DialogInterface.OnClickListener {
                override fun onClick(p0: DialogInterface?, p1: Int) {
                    when (p1) {
                        DialogInterface.BUTTON_POSITIVE ->
                            signOut()
                    }
                }
            }
            builder.setPositiveButton("확인", listener)
            builder.setNegativeButton("취소", listener)
            builder.show()
        }

        btnWithdrawal.setOnClickListener {
            var builder = AlertDialog.Builder(this)
            builder.setTitle("로그 아웃")
            builder.setMessage("구글계정만 일정이 유지됩니다")

            var listener = object : DialogInterface.OnClickListener {
                override fun onClick(p0: DialogInterface?, p1: Int) {
                    when (p1) {
                        DialogInterface.BUTTON_POSITIVE ->
                            revokeAccess()
                    }
                }
            }
            builder.setPositiveButton("확인", listener)
            builder.setNegativeButton("취소", listener)
            builder.show()
        }
    }

    override fun onBackPressed() {
        val nextIntent = Intent(this, MainActivity::class.java)
        nextIntent.putExtra("uid",intent.getStringExtra("uid"))
        startActivity(nextIntent)
    }

    private fun signOut() { // 로그아웃
        // Firebase sign out
        firebaseAuth.signOut()

        // Google sign out
        googleSignInClient.signOut().addOnCompleteListener(this) {
            val nextIntent = Intent(this, LoginActivity::class.java)
            startActivity(nextIntent)
        }
    }

    private fun revokeAccess() { //회원탈퇴 ? 왜안되징
        // Firebase sign out
        firebaseAuth.signOut()

        googleSignInClient.revokeAccess().addOnCompleteListener(this) {
            val nextIntent = Intent(this, LoginActivity::class.java)
            startActivity(nextIntent)
        }
    }
}