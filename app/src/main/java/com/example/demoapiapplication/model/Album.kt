package com.example.demoapiapplication.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Album() : Parcelable {
    @SerializedName("title")
    @Expose
    var title: String? = null
    @SerializedName("image")
    @Expose
    var image: String? = null
    @SerializedName("songs")
    @Expose
    var songs: List<Song>? = null
    @SerializedName("description")
    @Expose
    var description: String? = null

    constructor(parcel: Parcel) : this() {
        title = parcel.readString()
        image = parcel.readString()
        description = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(image)
        parcel.writeString(description)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Album> {
        override fun createFromParcel(parcel: Parcel): Album {
            return Album(parcel)
        }

        override fun newArray(size: Int): Array<Album?> {
            return arrayOfNulls(size)
        }
    }
}