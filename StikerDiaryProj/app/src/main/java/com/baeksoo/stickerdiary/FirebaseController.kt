package com.baeksoo.stickerdiary

import android.content.Context
import android.util.Log
import android.widget.ImageView
import com.baeksoo.stickerdiary.Data.Schedule
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class FirebaseController(var userid : String){

    // 데이터 쓰기
    fun UploadSchedule(schedule: Schedule){
        val dbRef = Firebase.database.getReference(userid)

        dbRef.child("Schedule").push().setValue(schedule);
    }

    // 데이터 하나 읽기
    fun ReadSchedule(){
        val dbRef = Firebase.database.getReference(userid)
        dbRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(data: DataSnapshot) {
                for (snapshot in data.children) {
                    for(schedule in snapshot.children){
                        Log.d("Firebase Check", schedule.key.toString()+""+schedule.value.toString())
                    }
                }
            }
            override fun onCancelled(p0: DatabaseError) {
            }
        })
    }

    fun ReadSticker(imageView : ImageView, context : Context){
        val storageRef = Firebase.storage.getReference("sticker1.png")

        Glide.with(context)
            .load(storageRef)
            .override(50, 50)
            .error(R.drawable.teststicker)
            .into(imageView)
    }
}