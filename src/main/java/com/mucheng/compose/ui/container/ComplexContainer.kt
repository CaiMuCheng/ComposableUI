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

import android.view.View
import android.widget.RelativeLayout
import com.mucheng.compose.extension.controlAttributes
import com.mucheng.compose.instance.Controller
import com.mucheng.compose.instance.Store
import com.mucheng.compose.scope.ComposeScope
import java.util.*

fun ComposeScope.ComplexContainer(
    controller: Controller = Controller,
    onReceive: RelativeLayout.() -> Unit = {},
    onLayout: ComplexContainerScope.() -> Unit
) {
    with(RelativeLayout(Store.activity)) {
        controller.controlAttributes(Container, this)

        addChild(this)
        onReceive()

        val scope = ComplexContainerScope(this)
        scope.onLayout()
    }
}

open class ComplexContainerScope(Container: RelativeLayout) : ComposeScope(Container) {

    @Suppress("PropertyName")
    val COMPLEX_TRUE = RelativeLayout.TRUE

    /**
     * Add complex layout rule.
     * @param verb Rule type.
     * @param subject Value.
     * */
    @Suppress("MemberVisibilityCanBePrivate")
    fun Controller.addRule(verb: Int, subject: Int): Controller {
        val ruleList = getOrDefault<MutableList<IntArray>>(ComplexContainerKey.RULE, LinkedList())
        ruleList.add(intArrayOf(verb, subject))
        set(ComplexContainerKey.RULE, ruleList)
        return this
    }

    /**
     * Make the current component on top of the target component.
     *
     * @param id Target component id.
     * */
    fun Controller.above(id: Int): Controller {
        addRule(RelativeLayout.ABOVE, id)
        return this
    }

    /**
     * Make the current component under the target component.
     *
     * @param id Target component id.
     * */
    @Suppress("MemberVisibilityCanBePrivate")
    fun Controller.below(id: Int): Controller {
        addRule(RelativeLayout.BELOW, id)
        return this
    }

    fun Controller.above(view: View): Controller {
        return above(view.id)
    }

    fun Controller.below(view: View): Controller {
        return below(view.id)
    }

    fun Controller.alignParentLeft(): Controller {
        addRule(RelativeLayout.ALIGN_PARENT_LEFT, COMPLEX_TRUE)
        return this
    }

    /**
     * Make the current component to the top of the parent component (not beyond).
     * */
    fun Controller.alignParentTop(): Controller {
        addRule(RelativeLayout.ALIGN_PARENT_TOP, COMPLEX_TRUE)
        return this
    }

    /**
     * Make the current component to the right of the parent component (not beyond).
     * */
    fun Controller.alignParentRight(): Controller {
        addRule(RelativeLayout.ALIGN_PARENT_RIGHT, COMPLEX_TRUE)
        return this
    }

    /**
     * Make the current component below the parent component (not beyond).
     * */
    fun Controller.alignParentBottom(): Controller {
        addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, COMPLEX_TRUE)
        return this
    }

    /**
     * Make the current component in the center of the parent component.
     * */
    fun Controller.centerInParent(): Controller {
        addRule(RelativeLayout.CENTER_IN_PARENT, COMPLEX_TRUE)
        return this
    }

}

enum class ComplexContainerKey {
    RULE
}