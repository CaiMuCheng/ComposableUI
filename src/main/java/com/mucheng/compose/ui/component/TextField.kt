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

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import com.google.android.material.textfield.TextInputEditText
import com.mucheng.annotations.mark.Inline
import com.mucheng.compose.R
import com.mucheng.compose.extension.backgroundColor
import com.mucheng.compose.extension.controlAttributes
import com.mucheng.compose.extension.fillMaxSize
import com.mucheng.compose.extension.margins
import com.mucheng.compose.instance.Controller
import com.mucheng.compose.instance.MaterialTheme
import com.mucheng.compose.instance.Store
import com.mucheng.compose.scope.ComposeScope
import com.mucheng.compose.ui.container.Card
import com.mucheng.compose.ui.theme.Color

@Inline
inline fun ComposeScope.MaterialTextField(
    controller: Controller = Controller,
    text: CharSequence = "",
    hint: CharSequence = "",
    textSize: Float = 16f,
    textColor: Color = MaterialTheme.colors.onSurface,
    hintColor: Color = Color.Gray,
    backgroundColor: Color = Color(0xE0E5E5E5),
    radius: Float = 4.dp,
    crossinline onTextInput: TextInputEditText.(String) -> Unit = {},
    onReceive: TextInputEditText.() -> Unit = {}
) {
    Card(
        controller,
        backgroundColor = backgroundColor,
        elevation = 0f,
        radius = radius
    ) {
        BasicTextField(
            controller = controller.fillMaxSize().backgroundColor(Color.Transparent).margins(
                left = 12.dp, top = 2.dp, right = 12.dp, bottom = 2.dp
            ),
            text = text,
            hint = hint,
            textSize = textSize,
            textColor = textColor,
            hintColor = hintColor,
            onTextInput = onTextInput,
            onReceive = onReceive
        )
    }
}

@Inline
inline fun ComposeScope.BasicTextField(
    controller: Controller = Controller,
    text: CharSequence = "",
    hint: CharSequence = "",
    textSize: Float = 16f,
    textColor: Color = MaterialTheme.colors.onSurface,
    hintColor: Color = Color.Gray,
    textCursorColor: Color = MaterialTheme.colors.primary,
    selectTextHandleColor: Color = MaterialTheme.colors.primary,
    crossinline onTextInput: TextInputEditText.(text: String) -> Unit = {},
    onReceive: TextInputEditText.() -> Unit = {}
) {
    with(TextInputEditText(Store.activity)) {
        controller.controlAttributes(Container, this)
        setText(text)
        setHint(hint)
        setTextSize(textSize)
        setTextColor(textColor.hexColor)
        setHintTextColor(hintColor.hexColor)
        addTextChangedListener(onTextChanged = { text, _, _, _ ->
            onTextInput(text.toString())
        })

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val drawable = ContextCompat.getDrawable(
                Store.activity,
                R.drawable.composable_text_cursor_drawable
            )!!
            drawable.colorFilter =
                PorterDuffColorFilter(
                    textCursorColor.hexColor,
                    PorterDuff.Mode.SRC_ATOP
                );
            textCursorDrawable = drawable
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val drawable = ContextCompat.getDrawable(
                Store.activity,
                R.drawable.composable_text_select_handle
            )!!
            drawable.colorFilter =
                PorterDuffColorFilter(
                    selectTextHandleColor.hexColor,
                    PorterDuff.Mode.SRC_ATOP
                );
            setTextSelectHandle(drawable)
        }

        addChild(this)
        onReceive()
    }

    controller.clear()
}