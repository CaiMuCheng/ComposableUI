/*
 * Project: 沐盒
 * Author: 苏沐橙
 * GitHub: https://github.com/CaiMuCheng
 * QQ: 3578557729
 * Email: 3578557729@qq.com
 * Copyright © 2022 - 2022 SuMuCheng
 *
 * 本项目使用 MPL 2.0 协议开源，你不得做出任何侵权行为
 * 若需要集成此项目，请保留协议并标注作者信息
 * 如果你的项目是 Android 项目，请在关于中引用此项目
 * 若你不遵顼以上条例，我们将向起诉你
 *
 * This project is open sourced under the MPL 2.0 protocol, and you are not allowed to do any infringements.
 * If you need to integrate this project, please keep the agreement and mark the author information.
 * If your project is an Android project, please reference this project in About.
 * If you do not comply with the above regulations, we will sue you.
 */

package com.mucheng.compose.extension

import com.mucheng.compose.scope.ComposeScope
import java.util.*
import kotlin.NoSuchElementException
import kotlin.collections.HashMap
import kotlin.reflect.KProperty

object ProxyPool {

    private val map: MutableMap<String, ValueDelegation<*>> = HashMap()

    fun <T : Any> createValueDelegation(key: String, value: T): ValueDelegation<T> {
        val delegation = ValueDelegation(value)
        map[key] = delegation
        return delegation
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : Any> observe(key: String, block: (T) -> Unit) {
        val value = map[key]
            ?: throw NoSuchElementException("Cannot find map value with key \"$key\" in map pool.")
        value.addObserver(block as (Any) -> Unit)
    }

}

open class ValueDelegation<T : Any>(private var value: T) {

    private val observers: MutableList<(T) -> Unit> = LinkedList()

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return value
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        if (this.value != value) {
            this.value = value
            observers.forEach {
                it(value)
            }
        }
    }

    fun addObserver(observer: (T) -> Unit) {
        observers.add(observer)
    }

    fun removeObserver(observer: (T) -> Unit) {
        observers.remove(observer)
    }

}

fun <T : Any> observe(key: String, block: (T) -> Unit) {
    ProxyPool.observe(key, block)
}

fun <T : Any> mutableStateOf(key: String, value: T): ValueDelegation<T> {
    return ProxyPool.createValueDelegation(key, value)
}