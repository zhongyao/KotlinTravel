package com.hongri.kotlin.chapter8.by

import kotlin.reflect.KProperty

/**
 * @author：hongri
 * @date：8/16/22
 * @description：
 */
class Delegate {
    var propValue: Any? = null
    operator fun getValue(myClass: MyClass, prop: KProperty<*>): Any? {
        return propValue
    }

    operator fun setValue(myClass: MyClass, prop: KProperty<*>, value: Any?) {
        propValue = value
    }
}