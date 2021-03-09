package com.baeksoo.stickerdiary

import android.content.Context
import android.util.Log
import android.widget.ImageView
import com.baeksoo.stickerdiary.Data.Schedule
import com.baeksoo.stickerdiary.Data.StickerData
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class FirebaseController(var userid : String){
    // 스티커 데이터 쓰기
    fun UploadSticker(sticker : StickerData){
        val dbRef = Firebase.database.getReference(userid)
        dbRef.child("Sticker").push().setValue(sticker)
    }

    // 스티커 데이터 수정
    fun UpdateSticker(key : String, sticker : StickerData){
        RemoveSticker(key)
        UploadSticker(sticker)
    }

    // 스티커 데이터 삭제
    fun RemoveSticker(key : String){
        val dbRef = Firebase.database.getReference(userid).child("Sticker").child(key)
        dbRef.removeValue()
    }

    // 스케쥴 데이터 쓰기
    fun UploadSchedule(schedule: Schedule){
        val dbRef = Firebase.database.getReference(userid)
        dbRef.child("Schedule").push().setValue(schedule)
    }

    // 스케쥴 데이터 수정
    fun UpdateSchedule(schedule : Schedule){
        RemoveSchedule(schedule.key)
        UploadSchedule(schedule)
    }

    // 스케줄 데이터 삭제
    fun RemoveSchedule(key : String){
        val dbRef = Firebase.database.getReference(userid).child("Schedule").child(key)
        dbRef.removeValue()
    }
}