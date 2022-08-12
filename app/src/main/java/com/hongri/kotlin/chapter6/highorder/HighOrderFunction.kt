package com.hongri.kotlin.chapter6.highorder

/**
 * @author：hongri
 * @date：8/12/22
 * @description：
 */

/**
 * 高阶函数：
 * 如果一个函数接收另一个函数作为参数，或者返回值的类型是另一个函数，那么该函数就称为高阶函数。
 */
//fun num1AndNum2(num1: Int, num2: Int, operation: (Int, Int) -> Int): Int {
//    val result = operation(num1, num2)
//    return result
//}

/**
 * 内联函数【inline】：内联函数的优势是不存在运行时开销
 */
inline fun num1AndNum2(num1: Int, num2: Int, operation: (Int, Int) -> Int): Int {
    val result = operation(num1, num2)
    return result
}

/**
 * noinline关键字 表示不会对引用的Lambda表达式进行内联
 *
 * 注：
 * 非内联的函数类型参数可以自由地传递给其他任何函数，因为他就是一个真实的参数；
 * 而内联的函数类型参数只允许传递给另一个内联函数，这也是它最大的局限性。
 *
 * 内联函数和非内联函数还有一个重要的区别，那就是内联函数所引用的Lambda表达式中是可以
 * 使用return关键字来进行函数返回的，而非内联函数只能进行局部返回。
 *
 */
inline fun inlineTest(block1: () -> Unit, noinline block2: () -> Unit) {

}


fun plus(num1: Int, num2: Int): Int {
    return num1 + num2
}

fun minus(num1: Int, num2: Int): Int {
    return num1 - num2
}

fun main() {
    val num1 = 100
    val num2 = 20
    //::plus 表示函数引用方式的写法
    val result1 = num1AndNum2(num1, num2, ::plus)
    val result2 = num1AndNum2(num1, num2, ::minus)
    println("result1 is $result1")
    println("result2 is $result2")
}

/**
 * Lambda表达式是最常用也是最普遍的高阶函数调用方式
 */
fun main1() {
    val num1 = 100
    val num2 = 20
    val result1 = num1AndNum2(num1, num2) {n1, n2 -> n1 + n2}
    val result2 = num1AndNum2(num1, num2) {n1, n2 -> n1 - n2}
    println("result1 is $result1")
    println("result2 is $result2")
}

/**
 * crossinline 关键字就像一个契约，它用于保证在内联函数的Lambda表达式中一定不会用到return关键字。
 * 但是仍然可以使用return@runRunnable的写法进行局部返回。
 * 总体来说，除了在return关键字的使用上有所区别外，crossinline保留了内联函数的其他所有特性
 */
inline fun runRunnable(crossinline block: () -> Unit) {
    val runnable = Runnable {
        block()
    }

    runnable.run()
}

