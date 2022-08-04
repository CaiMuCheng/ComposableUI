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

@file:Suppress("unused")

package com.mucheng.compose.extension

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.PopupWindow
import android.widget.RelativeLayout
import com.mucheng.compose.instance.Controller
import com.mucheng.compose.instance.Store
import com.mucheng.compose.ui.container.ComplexContainerKey
import com.mucheng.compose.ui.theme.Color
import java.lang.reflect.Field
import java.lang.reflect.Method

fun Controller.width(width: Float): Controller {
    set(CommonUiKey.WIDTH, width)
    return this
}

fun Controller.height(height: Float): Controller {
    set(CommonUiKey.HEIGHT, height)
    return this
}

fun Controller.size(size: Float): Controller {
    width(size)
    height(size)
    return this
}

fun Controller.fillMaxWidth(): Controller {
    width(ViewGroup.LayoutParams.MATCH_PARENT.toFloat())
    return this
}

fun Controller.fillMaxHeight(): Controller {
    height(ViewGroup.LayoutParams.MATCH_PARENT.toFloat())
    return this
}

fun Controller.fillMaxSize(): Controller {
    fillMaxWidth()
    fillMaxHeight()
    return this
}

fun Controller.wrapContentWidth(): Controller {
    width(ViewGroup.LayoutParams.WRAP_CONTENT.toFloat())
    return this
}

fun Controller.wrapContentHeight(): Controller {
    height(ViewGroup.LayoutParams.WRAP_CONTENT.toFloat())
    return this
}

fun Controller.wrapContentSize(): Controller {
    wrapContentWidth()
    wrapContentHeight()
    return this
}

inline fun Controller.widthTransform(operate: (deviceWidth: Float) -> Float): Controller {
    width(operate(Store.deviceWidth))
    return this
}

inline fun Controller.heightTransform(operate: (deviceHeight: Float) -> Float): Controller {
    height(operate(Store.deviceHeight))
    return this
}

fun Controller.margins(
    left: Float = 0f,
    top: Float = 0f,
    right: Float = 0f,
    bottom: Float = 0f
): Controller {
    set(CommonUiKey.MARGINS, intArrayOf(left.toInt(), top.toInt(), right.toInt(), bottom.toInt()))
    return this
}

fun Controller.marginAll(margins: Float): Controller {
    val value = margins.toInt()
    set(CommonUiKey.MARGINS, intArrayOf(value, value, value, value))
    return this
}

fun Controller.paddings(
    left: Float = 0f,
    top: Float = 0f,
    right: Float = 0f,
    bottom: Float = 0f
): Controller {
    set(CommonUiKey.PADDINGS, intArrayOf(left.toInt(), top.toInt(), right.toInt(), bottom.toInt()))
    return this
}

fun Controller.paddingAll(paddings: Float): Controller {
    val value = paddings.toInt()
    set(CommonUiKey.PADDINGS, intArrayOf(value, value, value, value))
    return this
}

fun Controller.layoutGravity(gravity: Int): Controller {
    set(CommonUiKey.LAYOUT_GRAVITY, gravity)
    return this
}

fun Controller.gravity(gravity: Int): Controller {
    set(CommonUiKey.GRAVITY, gravity)
    return this
}

fun Controller.weight(weight: Float): Controller {
    set(CommonUiKey.WEIGHT, weight)
    return this
}

fun Controller.id(id: Int): Controller {
    set(CommonUiKey.ID, id)
    return this
}

fun Controller.onClick(onClick: () -> Unit): Controller {
    set(CommonUiKey.ON_CLICK, onClick)
    return this
}

fun Controller.backgroundColor(color: Color): Controller {
    set(CommonUiKey.BACKGROUND, ColorDrawable(color.hexColor))
    return this
}

fun Controller.background(background: Drawable): Controller {
    set(CommonUiKey.BACKGROUND, background)
    return this
}

fun Controller.enabled(enabled: Boolean): Controller {
    set(CommonUiKey.ENABLED, enabled)
    return this
}

fun Controller.focusable(focusable: Boolean): Controller {
    set(CommonUiKey.FOCUSABLE, focusable)
    return this
}

fun Controller.focusableInTouchMode(focusableInTouchMode: Boolean): Controller {
    set(CommonUiKey.FOCUSABLE_IN_TOUCHABLE_MODE, focusableInTouchMode)
    return this
}

fun Controller.visibility(visibility: Int): Controller {
    set(CommonUiKey.VISIBILITY, visibility)
    return this
}

fun Controller.controlAttributes(container: View, view: View) {
    val id = getOrNull<Int>(CommonUiKey.ID)
    val width = getOrDefault(CommonUiKey.WIDTH, ViewGroup.LayoutParams.WRAP_CONTENT)
    val height = getOrDefault(CommonUiKey.HEIGHT, ViewGroup.LayoutParams.WRAP_CONTENT)
    val margins = getOrNull<IntArray>(CommonUiKey.MARGINS)
    val paddings = getOrNull<IntArray>(CommonUiKey.PADDINGS)
    val gravity = getOrDefault(CommonUiKey.GRAVITY, Gravity.NO_GRAVITY)
    val layoutGravity = getOrDefault(CommonUiKey.LAYOUT_GRAVITY, Gravity.NO_GRAVITY)
    val weight = getOrDefault(CommonUiKey.WEIGHT, 0f)
    val onClicked = getOrNull<() -> Unit>(CommonUiKey.ON_CLICK)
    val background = getOrNull<Drawable>(CommonUiKey.BACKGROUND)
    val enabled = getOrDefault(CommonUiKey.ENABLED, true)
    val focusable = getOrNull<Boolean>(CommonUiKey.FOCUSABLE)
    val focusableInTouchMode = getOrNull<Boolean>(CommonUiKey.FOCUSABLE_IN_TOUCHABLE_MODE)
    val visibility = getOrDefault(CommonUiKey.VISIBILITY, View.VISIBLE)
    val ruleList = getOrNull<List<IntArray>>(ComplexContainerKey.RULE)

    with(view) {
        if (id != null) {
            this.id = id
        }

        if (onClicked != null) {
            this.setOnClickListener { onClicked() }
        }

        if (background != null) {
            setBackground(background)
        }

        if (focusable != null) {
            isFocusable = focusable
        }

        if (focusableInTouchMode != null) {
            isFocusableInTouchMode = focusableInTouchMode
        }

        isEnabled = enabled
        setVisibility(visibility)
        setViewGravity(this, gravity)

        if (paddings != null) {
            setPadding(paddings[0], paddings[1], paddings[2], paddings[3])
        }

        layoutParams = createLayoutParams(
            container = container,
            width = width,
            height = height,
            margins = margins,
            gravity = layoutGravity,
            weight = weight,
            ruleList
        )
    }
}

private fun createLayoutParams(
    container: View,
    width: Int,
    height: Int,
    margins: IntArray?,
    gravity: Int,
    weight: Float,
    ruleList: List<IntArray>?
): ViewGroup.LayoutParams {
    return instanceLayoutParams(container, width, height).apply {
        if (this is ViewGroup.MarginLayoutParams && margins != null) {
            setMargins(margins[0], margins[1], margins[2], margins[3])
        }

        if (ruleList != null && this is RelativeLayout.LayoutParams) {
            ruleList.forEach { rule ->
                addRule(rule[0], rule[1])
            }
        }

        setLayoutGravity(this, gravity)
        setLayoutWeight(this, weight)
    }

}

private fun instanceLayoutParams(
    container: View,
    width: Int,
    height: Int
): ViewGroup.LayoutParams {
    val paramClass = requireLayoutParamsClass(container)
    return paramClass.getConstructor(Int::class.java, Int::class.java)
        .newInstance(width, height)
}

private fun setLayoutGravity(params: ViewGroup.LayoutParams, gravity: Int) {
    requireLayoutGravityField(params)?.setInt(params, gravity)
}

private fun setViewGravity(view: View, gravity: Int) {
    requireViewGravityMethod(view)?.invoke(view, gravity)
}

private fun setLayoutWeight(params: ViewGroup.LayoutParams, weight: Float) {
    requireLayoutWeightField(params)?.setFloat(params, weight)
}

private fun requireLayoutGravityField(params: ViewGroup.LayoutParams): Field? {
    val paramsClass = params::class.java
    return try {
        paramsClass.getField("gravity")
    } catch (e: Exception) {
        null
    }
}

private fun requireViewGravityMethod(view: View): Method? {
    val viewClass = view::class.java
    return try {
        viewClass.getMethod("setGravity", Int::class.java)
    } catch (e: Exception) {
        null
    }
}

private fun requireLayoutWeightField(params: ViewGroup.LayoutParams): Field? {
    val paramsClass = params::class.java
    return try {
        paramsClass.getField("weight")
    } catch (e: Exception) {
        null
    }
}

@Suppress("UNCHECKED_CAST")
private fun requireLayoutParamsClass(container: View): Class<ViewGroup.LayoutParams> {
    val viewClass = container::class.java

    return try {
        Class.forName("${viewClass.name}\$LayoutParams") as Class<ViewGroup.LayoutParams>
    } catch (e: Exception) {
        FrameLayout.LayoutParams::class.java as Class<ViewGroup.LayoutParams>
    }
}

fun Controller.controlPopupUIAttributes(popupWindow: PopupWindow) {
    val width = getOrDefault(CommonUiKey.WIDTH, WindowManager.LayoutParams.WRAP_CONTENT)
    val height = getOrDefault(CommonUiKey.WIDTH, WindowManager.LayoutParams.WRAP_CONTENT)
    val isFocusable = getOrDefault(CommonUiKey.FOCUSABLE, true)
    val isTouchable = getOrDefault(CommonUiKey.TOUCHABLE, true)
    val background = getOrNull<Drawable>(CommonUiKey.BACKGROUND)


    with(popupWindow) {
        setWidth(width)
        setHeight(height)
        setFocusable(isFocusable)
        setTouchable(isTouchable)
        if (background != null) {
            setBackgroundDrawable(background)
        }
    }
}

enum class CommonUiKey {
    WIDTH, HEIGHT, MARGINS, PADDINGS, WEIGHT,
    GRAVITY, LAYOUT_GRAVITY, ID,
    ON_CLICK, BACKGROUND, FOCUSABLE, FOCUSABLE_IN_TOUCHABLE_MODE, ENABLED,
    VISIBILITY, TOUCHABLE
}