package com.hongri.kotlin.function

/**
 * @author：zhongyao
 * @date：2023/5/12
 * @description：
 */

fun main() {

    withFunc()

    letFunc()

    runFunc()

    applyFunc()

    alsoFunc()
}


/**
 * with函数是一个内联函数，它把传入的对象作为接受者，在该函数内可以使用this指代该对象来访问其
 * 公有的属性和方法，该函数的返回值为函数最后一行或指定的return表达式。
 */
fun withFunc() {
    val person = Person("Jack", 33)
    val result = with(person) {
        this.name = "Kobe"
        this.age = 90
        this.work(10)
//        return@with "哈哈"
    }
    println("result: $result")
}

/**
 * let()函数是一个扩展对象函数，它可以对被扩展的对象做统一的判空处理，在函数块内使用it来指代该对象，
 * 可以访问对象的公有属性和方法。let()函数的返回值和with()函数一样，为函数块最后一行或指定的return表示式。
 */
fun letFunc() {
    val person = Person("Jack-let", 34)
    val result = person.let {
        it.name = "Kobe"
        it.age = 44
        it.eat()
        it.work(9)
    }
    println("result:$result")
}

/**
 * run()函数是with()和let()函数的结合体，它可以像with()函数一样直接在函数块中使用this指代该对象，
 * 也可以像let()函数一样为对象做统一的判空处理。
 */
fun runFunc() {
    val person = Person("Jack-run", 35)
    val result = person.run {
        this.name = "James"
        this.age = 38
        eat()
        work(12)
    }
    println("result:$result")
}

/**
 * apply()函数和run()函数相似，不同的是，run()函数是以闭包形式返回最后一行代码的值，
 * 而apply()函数返回的是传入的对象本身。
 */
fun applyFunc() {
    val person = Person("Jack-apply", 36)
    val result = person.apply {
        name = "Wade"
        age = 39
        eat()
        work(11)
    }
    println("result:$result")
}

/**
 * also()函数和apply()函数相似，不同的是，also()函数在函数块中使用it指代该对象，
 * 而apply()函数在函数块中使用this指代该对象。
 */
fun alsoFunc() {
    val person = Person("Jack-also", 37)
    val result = person.also {
        it.name = "Tim"
        it.age = 44
        it.eat()
        it.work(7)
    }
    println("result:$result")
}


