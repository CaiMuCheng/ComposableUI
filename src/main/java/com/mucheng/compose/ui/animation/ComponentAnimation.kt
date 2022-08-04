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

package com.mucheng.compose.ui.animation

import android.animation.Animator
import android.animation.ValueAnimator
import android.view.View
import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator
import java.util.*
import kotlin.collections.HashMap

object ComponentAnimation {

    private val map: MutableMap<String, MutableList<Animator>> = HashMap()

    fun cancelFirst(key: String) {
        map[key]!!.first().end()
    }

    fun cancelAll(key: String) {
        map[key]!!.forEach {
            it.end()
        }
    }

    fun newAnimation(
        key: String,
        animator: ValueAnimator,
        duration: Long,
        interpolator: Interpolator = LinearInterpolator(),
        onStart: (Animator) -> Unit = {},
        onUpdate: (ValueAnimator) -> Unit
    ): ValueAnimator {
        var value = map[key]
        if (value == null) {
            map[key] = LinkedList()
            value = map[key]!!
        }

        animator.duration = duration
        animator.interpolator = interpolator
        animator.addUpdateListener {
            onUpdate(it)
        }
        animator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {
                onStart(animator)
            }

            override fun onAnimationEnd(animation: Animator?) {
                value.remove(animator)
                if (value.isEmpty()) {
                    map.remove(key)
                    return
                }

                value.first().start()
            }

            override fun onAnimationCancel(animation: Animator?) {

            }

            override fun onAnimationRepeat(animation: Animator?) {

            }
        })
        value.add(animator)
        return animator
    }

    fun startGroup(key: String) {
        map[key]!!.first().start()
    }

}

fun CrossFadeAnimation(
    key: String,
    view: View,
    start: Boolean = true,
    duration: Long,
    onCrossing: (Animator) -> Unit,
) {
    CrossFadeAnimation(key, view, start, duration, duration, onCrossing)
}

fun CrossFadeAnimation(
    key: String,
    view: View,
    start: Boolean = true,
    firstDuration: Long,
    secondDuration: Long,
    onCrossing: (Animator) -> Unit,
) {
    ComponentAnimation.newAnimation(
        key,
        ValueAnimator.ofFloat(1f, 0f),
        duration = firstDuration
    ) {
        view.alpha = it.animatedValue as Float
    }

    ComponentAnimation.newAnimation(
        key,
        ValueAnimator.ofFloat(0f, 1f),
        duration = secondDuration,
        onStart = onCrossing
    ) {
        view.alpha = it.animatedValue as Float
    }

    if (start) {
        ComponentAnimation.startGroup(key)
    }
}

fun InterpolatedAnimation(
    key: String,
    duration: Long,
    start: Boolean = true,
    animator: ValueAnimator,
    onUpdate: (ValueAnimator) -> Unit,
) {
    ComponentAnimation.newAnimation(
        key = key,
        animator = animator,
        duration = duration
    ) {
        onUpdate(it)
    }

    if (start) {
        ComponentAnimation.startGroup(key)
    }
}