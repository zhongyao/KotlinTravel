package com.hongri.kotlin.intent

import android.os.Parcel
import android.os.Parcelable

/**
 * 使用Intent来传递对象方式二：
 * Parcelable
 */
class Book : Parcelable {
    var bookName = ""
    var pages = 0

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(bookName)
        parcel.writeInt(pages)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Book> {
        override fun createFromParcel(parcel: Parcel): Book {
            val book = Book()
            book.bookName = parcel.readString() ?: ""
            book.pages = parcel.readInt()
            return book
        }

        override fun newArray(size: Int): Array<Book?> {
            return arrayOfNulls(size)
        }
    }
}