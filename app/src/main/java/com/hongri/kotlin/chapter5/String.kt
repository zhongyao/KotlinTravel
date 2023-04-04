package com.hongri.kotlin.chapter5

/**
 * @author：zhongyao
 * @date：2023/4/4
 * @description：『扩展函数』最好将其定义成顶层方法，这样可以让扩展函数拥有全局的访问域
 * @reference:https://doc.devio.org/as/book/docs/Part1/Android%E5%BC%80%E5%8F%91%E5%BF%85%E5%A4%87Kotlin%E6%A0%B8%E5%BF%83%E6%8A%80%E6%9C%AF/KotlinExtensions.html
 */

/**
 * 将lettersCount方法定义成立String类的扩展函数
 */
fun String.lettersCount(): Int {
    var count = 0
    for (char in this) {
        if (char.isLetter()) {
            count++;
        }
    }
    return count
}