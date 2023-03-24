package com.hongri.kotlin.chapter2

/**
 * 当在一个类前面生命了data关键字，就表明你希望这个类是数据类，Kotlin会根据主构造函数中的参数
 * 帮你将equals()、hashCode()、toString()等固定且无实际逻辑意义的方法自动生成，从而大大减少了开发工作量
 *
 * data class不能被继承:
 * data class被编译成了final class，所以是不能被继承的。
 */
data class CellPhone(val brand: String, val price: Double)

data class CellPhone2(val brand: String, val price: Double = 3000.0)