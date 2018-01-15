package com.xyj.chapter_2.manualbinder

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by xuyongjun on 2018/1/14.
 */
class Book(private var bookId: Int, private var bookName: String) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(bookId)
        parcel.writeString(bookName)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "Book(bookId=$bookId, bookName='$bookName')"
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