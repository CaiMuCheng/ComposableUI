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

package com.mucheng.compose.ui.theme

import com.mucheng.annotations.mark.Inline

@Suppress("unused")
@Inline
@JvmInline
value class Color(val hexColor: Int) {

    constructor(color: Long) : this(color.toInt())

    constructor(hexString: String) : this(android.graphics.Color.parseColor(hexString))

    companion object {
        val Black = Color(0xFF000000)
        val DarkGray = Color(0xFF444444)
        val Gray = Color(0xFF888888)
        val LightGray = Color(0xFFCCCCCC)
        val White = Color(0xFFFFFFFF)
        val Red = Color(0xFFFF0000)
        val Green = Color(0xFF00FF00)
        val Blue = Color(0xFF0000FF)
        val Yellow = Color(0xFFFFFF00)
        val Cyan = Color(0xFF00FFFF)
        val Magenta = Color(0xFFFF00FF)
        val Transparent = Color(0x00000000)
    }

}
