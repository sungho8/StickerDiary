package com.baeksoo.stickerdiary

class Schedule(){
    var StartDay : String = ""
    var EndDay : String = ""
    var StartTime : String = ""
    var EndTime : String = ""
    var Title : String = ""
    var Sticker : String = ""
    var Content: String = ""

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


}