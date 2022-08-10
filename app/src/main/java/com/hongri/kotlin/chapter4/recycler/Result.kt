package com.hongri.kotlin.chapter4.recycler

import java.lang.Exception

/**
 * @author：hongri
 * @date：8/10/22
 * @description：密封类的关键字时sealed class
 * [密封类及其所有子类只能定义在同一个文件的顶层位置，不能嵌套在其他类中]
 */
sealed class Result
class Success(val msg: String) : Result()
class Failure(val error: Exception) : Result()

fun getResultMsg(result: Result) = when (result) {
    is Success -> result.msg
    is Failure -> result.error.message
}


//interface Result
//class Success(val msg: String) : Result
//class Failure(val error: Exception) : Result
//
//fun getResultMsg(result: Result) = when (result) {
//    is Success -> result.msg
//    is Failure -> result.error.message
//    else -> throw IllegalArgumentException()
//}

