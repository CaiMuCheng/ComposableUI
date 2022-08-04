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

import android.content.res.Configuration
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.WindowInsets
import android.widget.FrameLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import com.mucheng.compose.activity.ComposeActivity
import com.mucheng.compose.instance.MaterialTheme
import com.mucheng.compose.instance.Store
import com.mucheng.compose.scope.ComposeScope
import com.mucheng.compose.ui.theme.Color
import com.mucheng.compose.ui.theme.Colors

fun ComposeActivity.compose() {
    Store.`Internal-bindActivity`(this)
    MaterialTheme.bindColors(
        if (isSystemInDarkTheme()) darkColors() else lightColors()
    )

    val container = FrameLayout(this).apply {
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
    }
    setContentView(container)

    val scope = ComposeScope(container)
    scope.onCompose()
}

inline fun ComposeScope.MaterialTheme(colors: Colors, onLayout: ComposeScope.() -> Unit) {
    MaterialTheme.bindColors(colors)
    onLayout()
}

fun isSystemInDarkTheme(): Boolean {
    val uiMode = Store.activity.resources.configuration.uiMode
    return (uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES
}

fun ComposeScope.statusBarColor(color: Color, isDark: Boolean = isSystemInDarkTheme()) {
    val activity = Store.activity
    val window = activity.window
    val decorView = window.decorView
    WindowCompat.setDecorFitsSystemWindows(window, true)
    val controller = WindowCompat.getInsetsController(window, decorView)
    if (controller != null) {
        controller.isAppearanceLightStatusBars = !isDark
        window.statusBarColor = color.hexColor
    }
}

fun darkColors(
    primary: Color = Color(0xFFBB86FC),
    primaryVariant: Color = Color(0xFF3700B3),
    secondary: Color = Color(0xFF03DAC6),
    secondaryVariant: Color = secondary,
    background: Color = Color(0xFF121212),
    surface: Color = Color(0xFF121212),
    error: Color = Color(0xFFCF6679),
    onPrimary: Color = Color.Black,
    onSecondary: Color = Color.Black,
    onBackground: Color = Color.White,
    onSurface: Color = Color.White,
    onError: Color = Color.Black
): Colors {
    return Colors(
        primary,
        primaryVariant,
        secondary,
        secondaryVariant,
        background,
        surface,
        error,
        onPrimary,
        onSecondary,
        onBackground,
        onSurface,
        onError,
        false
    )
}

fun lightColors(
    primary: Color = Color(0xFF6200EE),
    primaryVariant: Color = Color(0xFF3700B3),
    secondary: Color = Color(0xFF03DAC6),
    secondaryVariant: Color = Color(0xFF018786),
    background: Color = Color.White,
    surface: Color = Color.White,
    error: Color = Color(0xFFB00020),
    onPrimary: Color = Color.White,
    onSecondary: Color = Color.Black,
    onBackground: Color = Color.Black,
    onSurface: Color = Color.Black,
    onError: Color = Color.White
): Colors = Colors(
    primary,
    primaryVariant,
    secondary,
    secondaryVariant,
    background,
    surface,
    error,
    onPrimary,
    onSecondary,
    onBackground,
    onSurface,
    onError,
    true
)