package com.hongri.kotlin.util

/**
 * @author：hongri
 * @date：8/7/22
 * @description：只希望让类中的某一个方法变成静态方法的调用方式 companion object
 */
class Util2 {
    fun doAction1() {
        println("do action1")
    }
    companion object {
        //Java中调用，需添加该注解【JvmStatic注解只能加在单例类或companion object中的方法上】
        @JvmStatic
        fun doAction2() {
            println("do action2")
        }
    }
}