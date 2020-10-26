package com.baeksoo.stickerdiary

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class FirebaseController(var userid : String){

    // 데이터 쓰기
    fun UploadSchedule(schedule: Schedule){
        val myRef = Firebase.database.getReference(userid)
        myRef.push().setValue(schedule);
    }

    // 데이터 하나 읽기
    fun ReadSchedule(){
        val myRef = Firebase.database.getReference(userid)

        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
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

    fun ReadAllSchedule() : ArrayList<Schedule>{
        val myRef = Firebase.database.getReference(userid)
        var scheduleList = ArrayList<Schedule>()

        myRef.addValueEventListener(object  : ValueEventListener{
            override fun onDataChange(data: DataSnapshot) {
                for (snapshot in data.children) {
                    val schedule = snapshot.getValue(Schedule :: class.java)
                    Log.d("ttt", schedule.toString())
                    if (schedule != null) {

                        scheduleList.add(schedule)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        return scheduleList
    }
}