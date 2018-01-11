package com.xyj.chapter_2.model

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

/**
 * Created by xuyongjun on 2018/1/11.
 */

class User(var name: String, var age: Int) : Parcelable, Serializable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readInt()) {
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeInt(age)
        dest?.writeString(name)
    }

    override fun describeContents(): Int = 0

    override fun toString(): String {
        return "User(name='$name', age=$age)"
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}
