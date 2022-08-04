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

package com.mucheng.compose.ui.component

import android.view.Gravity
import androidx.appcompat.widget.LinearLayoutCompat
import com.google.android.material.card.MaterialCardView
import com.mucheng.annotations.mark.Inline
import com.mucheng.compose.extension.fillMaxSize
import com.mucheng.compose.extension.gravity
import com.mucheng.compose.extension.onClick
import com.mucheng.compose.extension.paddings
import com.mucheng.compose.instance.Controller
import com.mucheng.compose.instance.MaterialTheme
import com.mucheng.compose.scope.ComposeScope
import com.mucheng.compose.ui.container.Card
import com.mucheng.compose.ui.container.Linear
import com.mucheng.compose.ui.container.LinearContainerScope
import com.mucheng.compose.ui.container.LinearOrientation
import com.mucheng.compose.ui.theme.Color

@Inline
inline fun ComposeScope.Button(
    noinline onClick: () -> Unit,
    controller: Controller = Controller,
    backgroundColor: Color = MaterialTheme.colors.primary,
    elevation: Float = 2.dp,
    radius: Float = 4.dp,
    crossinline onReceive: MaterialCardView.() -> Unit = {},
    crossinline onLayout: LinearContainerScope.() -> Unit
) {
    Card(
        controller.onClick(onClick),
        backgroundColor = backgroundColor,
        elevation = elevation,
        radius = radius,
        onReceive = onReceive
    ) {
        Linear(
            controller = controller.fillMaxSize().paddings(
                left = 16.dp, top = 8.dp, right = 16.dp, bottom = 8.dp
            ), orientation = LinearOrientation.HORIZONTAL
        ) {
            onLayout()
        }
    }
}

@Inline
inline fun ComposeScope.TextButton(
    text: CharSequence,
    textColor: Color = MaterialTheme.colors.onPrimary,
    textSize: Float = 16f,
    backgroundColor: Color = MaterialTheme.colors.primary,
    noinline onClick: () -> Unit,
    controller: Controller = Controller,
    elevation: Float = 2.dp,
    radius: Float = 4.dp,
    crossinline onReceive: MaterialCardView.() -> Unit = {},
    crossinline onControl: Controller.() -> Controller = { this },
) {
    Button(
        onClick = onClick,
        backgroundColor = backgroundColor,
        controller = controller,
        elevation = elevation,
        radius = radius,
        onReceive = onReceive
    ) {
        Text(
            text,
            textColor,
            textSize,
            controller
                .fillMaxSize()
                .gravity(Gravity.CENTER)
                .onControl()
        )
    }
}