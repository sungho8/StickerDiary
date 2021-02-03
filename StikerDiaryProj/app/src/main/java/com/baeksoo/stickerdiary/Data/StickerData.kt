package com.baeksoo.stickerdiary.Data

import android.os.Parcel
import android.os.Parcelable

class StickerData () : Parcelable{
    var sticker : String = ""
    var day : String = ""
    constructor(parcel: Parcel) : this() {
        sticker = parcel.readString()
        day = parcel.readString()
    }

    constructor(sticker : String, day : String) : this() {
        this.sticker = sticker
        this.day = day
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(sticker)
        parcel.writeString(day)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<StickerData> {
        override fun createFromParcel(parcel: Parcel): StickerData {
            return StickerData(parcel)
        }

        override fun newArray(size: Int): Array<StickerData?> {
            return arrayOfNulls(size)
        }
    }

}