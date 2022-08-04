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

package com.mucheng.compose.instance

object Controller {

    private val map: MutableMap<Enum<*>, Any?> = HashMap()

    operator fun set(key: Enum<*>, value: Any?) {
        map[key] = value
    }

    @Suppress("UNCHECKED_CAST")
    operator fun <T> get(key: Enum<*>): T {
        return map[key]!! as T
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> getOrNull(key: Enum<*>): T? {
        val value = map[key]
        if (value == null) {
            return value
        }
        return value as T
    }

    fun <T : Any> getOrDefault(key: Enum<*>, default: T): T {
        val value = getOrNull<T>(key)
        return value ?: default
    }

    fun clear() {
        map.clear()
    }

}