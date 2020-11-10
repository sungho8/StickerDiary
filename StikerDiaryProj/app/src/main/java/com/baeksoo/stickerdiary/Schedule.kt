package com.baeksoo.stickerdiary

import android.os.Parcel
import android.os.Parcelable

class Schedule(): Parcelable {
    var StartDay : String = ""
    var EndDay : String = ""
    var StartTime : String = ""
    var EndTime : String = ""
    var Title : String = ""
    var Sticker : String = ""
    var Content: String = ""

    constructor(parcel: Parcel) : this() {
        StartDay = parcel.readString()
        EndDay = parcel.readString()
        StartTime = parcel.readString()
        EndTime = parcel.readString()
        Title = parcel.readString()
        Sticker = parcel.readString()
        Content = parcel.readString()
    }

    constructor(StartDay : String, EndDay : String, StartTime : String, EndTime : String,
                Title : String, Sticker : String, Content: String) : this(){
        this.StartDay = StartDay
        this.EndDay = EndDay
        this.StartTime = StartTime
        this.EndTime = EndTime
        this.Title = Title
        this.Sticker = Sticker
        this.Content = Content
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(StartDay)
        parcel.writeString(EndDay)
        parcel.writeString(StartTime)
        parcel.writeString(EndTime)
        parcel.writeString(Title)
        parcel.writeString(Sticker)
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