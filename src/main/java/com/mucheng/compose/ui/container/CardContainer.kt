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

package com.mucheng.compose.ui.container

import android.graphics.drawable.Drawable
import com.google.android.material.card.MaterialCardView
import com.mucheng.annotations.mark.Inline
import com.mucheng.compose.extension.CommonUiKey
import com.mucheng.compose.extension.controlAttributes
import com.mucheng.compose.instance.Controller
import com.mucheng.compose.instance.MaterialTheme
import com.mucheng.compose.instance.Store
import com.mucheng.compose.scope.ComposeScope
import com.mucheng.compose.ui.theme.Color

@Inline
inline fun ComposeScope.Card(
    controller: Controller = Controller,
    backgroundColor: Color = MaterialTheme.colors.surface,
    elevation: Float = 2.dp,
    radius: Float = 4.dp,
    onReceive: MaterialCardView.() -> Unit = {},
    onLayout: CardScope.() -> Unit
) {
    with(MaterialCardView(Store.activity)) {
        controller.controlAttributes(Container, this)
        controller.getOrNull<Drawable>(CommonUiKey.BACKGROUND) ?: setCardBackgroundColor(
            backgroundColor.hexColor
        )

        cardElevation = elevation
        setRadius(radius)

        addChild(this)
        onReceive()

        val scope = CardScope(this)
        scope.onLayout()
    }
}

open class CardScope(Container: MaterialCardView) : StackScope(Container) {

}