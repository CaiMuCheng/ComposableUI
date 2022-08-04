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

package com.mucheng.compose.ui.component.nativeUI

import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.PopupWindow
import com.mucheng.compose.extension.controlPopupUIAttributes
import com.mucheng.compose.instance.Controller
import com.mucheng.compose.instance.Store
import com.mucheng.compose.scope.ComposeScope
import com.mucheng.compose.ui.container.StackScope

inline fun ComposeScope.PopupUI(
    controller: Controller = Controller,
    type: PopupType = PopupType.None,
    animationStyle: Int = -1,
    onReceive: PopupWindow .() -> Unit = {},
    onLayout: PopupUIScope.() -> Unit
) {
    val activity = Store.activity
    with(PopupWindow(activity)) {
        controller.controlPopupUIAttributes(this)
        if (animationStyle > 0) {
            setAnimationStyle(animationStyle)
        }

        val container = FrameLayout(activity).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }
        val scope = PopupUIScope(container)
        scope.onLayout()

        contentView = container

        if (type is PopupType.AsDrop) {
            val view = type.view
            view.post {
                showAtLocation(view, type.gravity, type.x, type.y)
            }
        } else if (type is PopupType.AsLocation) {
            val decorView = activity.window.decorView
            decorView.post {
                showAtLocation(decorView, type.gravity, type.x, type.y)
            }
        }
        onReceive()
    }

    controller.clear()
}

open class PopupUIScope(Container: FrameLayout) : StackScope(Container) {


}

@Suppress("unused")
sealed class PopupType {

    data class AsDrop(
        val view: View,
        val gravity: Int = Gravity.NO_GRAVITY,
        val x: Int = 0,
        val y: Int = 0
    ) : PopupType()

    data class AsLocation(val gravity: Int = Gravity.NO_GRAVITY, val x: Int = 0, val y: Int = 0) :
        PopupType()

    object None : PopupType()

}