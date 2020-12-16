package com.baeksoo.stickerdiary.Data

import android.os.Parcel
import android.os.Parcelable

class Option(): Parcelable {
    var mColor : Int = 0

    constructor(parcel: Parcel) : this() {
        mColor = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(mColor)
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