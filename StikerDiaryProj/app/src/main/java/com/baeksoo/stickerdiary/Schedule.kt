package com.baeksoo.stickerdiary

import android.util.Log
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class Schedule(var StartDay:String,var EndDay:String, var StartTime : String, var EndTime : String,
               var Title : String, var Sticker : String, var Content: String){
    // 데이터 쓰기
    fun UploadSchedule(userid : String){
        val database = Firebase.database
        val myRef = database.getReference(userid).child("Schedule")

        myRef.child("StartDay").setValue(StartDay)
        myRef.child("EndDay").setValue(EndDay)
        myRef.child("StartTime").setValue(StartTime)
        myRef.child("EndTime").setValue(EndTime)
        myRef.child("Title").setValue(Title)
        myRef.child("Sticker").setValue(Sticker)
        myRef.child("Content").setValue(Content)
    }
}