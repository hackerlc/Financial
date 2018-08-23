package com.boxin.financial.entity

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * class info
 * @author joker
 * Email:812405389@qq.com
 * @version 2018/5/21
 */
class NameAndText() : Parcelable{
    /**
     * 英文
     */
    @SerializedName("name")
    var name: String? = null
    /**
     * 中文
     */
    @SerializedName("text")
    var text: String? = null

    constructor(parcel: Parcel) : this() {
        name = parcel.readString()
        text = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(text)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NameAndText> {
        override fun createFromParcel(parcel: Parcel): NameAndText {
            return NameAndText(parcel)
        }

        override fun newArray(size: Int): Array<NameAndText?> {
            return arrayOfNulls(size)
        }
    }

}