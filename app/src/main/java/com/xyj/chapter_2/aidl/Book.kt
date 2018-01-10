package com.xyj.chapter_2.aidl

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by xuyongjun on 2018/1/10.
 */
class Book() : Parcelable {
    override fun writeToParcel(parcel: Parcel?, flags: Int) {
        parcel?.writeString(bookName)
        parcel?.writeInt(bookId!!)
    }

    override fun describeContents(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    var bookId: Int? = null
    var bookName: String? = null

    constructor(parcel: Parcel) : this() {
        bookId = parcel.readValue(Int::class.java.classLoader) as? Int
        bookName = parcel.readString()
    }

    companion object CREATOR : Parcelable.Creator<Book> {
        override fun createFromParcel(parcel: Parcel): Book {
            return Book(parcel)
        }

        override fun newArray(size: Int): Array<Book?> {
            return arrayOfNulls(size)
        }
    }
}