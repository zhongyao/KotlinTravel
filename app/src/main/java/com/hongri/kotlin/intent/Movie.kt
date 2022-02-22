package com.hongri.kotlin.intent

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * 使用Intent来传递对象方式三：
 * Kotlin给我们提供了一种非常简便的使用Parcelable传递对象的方式，
 * 前提是要传递的数据都必须封装在对象的主构造函数中才行
 */
@Parcelize
class Movie(var movieName: String, var time: Int) : Parcelable