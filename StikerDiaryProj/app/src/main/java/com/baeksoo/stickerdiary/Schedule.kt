package com.baeksoo.stickerdiary

import android.util.Log

class Schedule(var Check : Boolean, var StartDay:String,var EndDay:String,
               var StartTime : String, var EndTime : String, var Title : String,
               var Category : String, var Content: String){
    fun print(){
        Log.e("MainActivity", StartDay + " ~ " + EndDay)
    }
}


