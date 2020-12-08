package com.baeksoo.stickerdiary

import android.graphics.Color
import android.os.Parcel
import android.os.Parcelable

class Schedule(): Parcelable {
    var isStart : Boolean = false
    var ColorIndex : Int = 0
    var StartDay : String = ""
    var EndDay : String = ""
    var StartTime : String = ""
    var EndTime : String = ""
    var Title : String = ""
    var Content: String = ""

    constructor(parcel: Parcel) : this() {
        ColorIndex = parcel.readInt()
        StartDay = parcel.readString()
        EndDay = parcel.readString()
        StartTime = parcel.readString()
        EndTime = parcel.readString()
        Title = parcel.readString()
        Content = parcel.readString()
    }

    constructor(ColorIndex : Int, StartDay : String, EndDay : String, StartTime : String, EndTime : String,
                Title : String,  Content: String) : this(){
        this.ColorIndex = ColorIndex
        this.StartDay = StartDay
        this.EndDay = EndDay
        this.StartTime = StartTime
        this.EndTime = EndTime
        this.Title = Title
        this.Content = Content
    }

    fun copy(isStart : Boolean) : Schedule{
        val newSchedule = Schedule(this.ColorIndex,this.StartDay,this.EndDay,this.StartTime,this.EndTime,this.Title,this.Content)
        newSchedule.isStart = isStart
        return newSchedule
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(ColorIndex)
        parcel.writeString(StartDay)
        parcel.writeString(EndDay)
        parcel.writeString(StartTime)
        parcel.writeString(EndTime)
        parcel.writeString(Title)
        parcel.writeString(Content)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Schedule> {
        override fun createFromParcel(parcel: Parcel): Schedule {
            return Schedule(parcel)
        }

        override fun newArray(size: Int): Array<Schedule?> {
            return arrayOfNulls(size)
        }
    }
}