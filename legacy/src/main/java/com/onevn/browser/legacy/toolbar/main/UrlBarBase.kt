/*
 * Copyright (C) 2017-2019 DiepDT
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.onevn.browser.legacy.toolbar.main

import android.content.Context
import android.view.Gravity
import com.onevn.browser.core.utility.extensions.convertDpToPx
import com.onevn.browser.legacy.R
import com.onevn.browser.legacy.action.manager.ActionController
import com.onevn.browser.legacy.action.manager.ActionIconManager
import com.onevn.browser.legacy.action.manager.SoftButtonActionArrayManager
import com.onevn.browser.legacy.action.manager.SoftButtonActionManager
import com.onevn.browser.legacy.settings.data.AppData
import com.onevn.browser.legacy.tab.manager.MainTabData
import com.onevn.browser.legacy.toolbar.ButtonToolbarController
import com.onevn.browser.legacy.utils.view.swipebutton.SwipeTextButton
import com.onevn.browser.ui.extensions.decodePunyCodeUrl
import com.onevn.browser.ui.theme.ThemeData

abstract class UrlBarBase(context: Context, controller: ActionController, iconManager: ActionIconManager, layout: Int, request_callback: RequestCallback) : ToolbarBase(context, AppData.toolbar_url, layout, request_callback) {
    private val mLeftButtonController: ButtonToolbarController
    private val mRightButtonController: ButtonToolbarController
    protected val centerUrlButton: SwipeTextButton

    init {
        val toolbarSizeY = context.convertDpToPx(AppData.toolbar_url.size.get())

        val softbtnManager = SoftButtonActionManager.getInstance(context)

        mLeftButtonController = ButtonToolbarController(findViewById(R.id.leftLinearLayout), controller, iconManager, toolbarSizeY)
        mRightButtonController = ButtonToolbarController(findViewById(R.id.rightLinearLayout), controller, iconManager, toolbarSizeY)

        centerUrlButton = findViewById(R.id.centerUrlButton)

        centerUrlButton.setActionData(softbtnManager.btn_url_center, controller, iconManager)
        ButtonToolbarController.settingButtonSize(centerUrlButton, toolbarSizeY)

        addButtons()
    }

    override fun onPreferenceReset() {
        super.onPreferenceReset()
        addButtons()

        centerUrlButton.notifyChangeState()
        centerUrlButton.setSense(AppData.swipebtn_sensitivity.get())
        centerUrlButton.textSize = AppData.toolbar_text_size_url.get().toFloat()
    }

    override fun applyTheme(themeData: ThemeData?) {
        super.applyTheme(themeData)
        applyTheme(mLeftButtonController)
        applyTheme(mRightButtonController)
    }

    private fun addButtons() {
        val manager = SoftButtonActionArrayManager.getInstance(context)
        mLeftButtonController.addButtons(manager.btn_url_left.list)
        mRightButtonController.addButtons(manager.btn_url_right.list)
        onThemeChanged(ThemeData.getInstance())// TODO
    }

    override fun notifyChangeWebState(data: MainTabData?) {
        super.notifyChangeWebState(data)
        mLeftButtonController.notifyChangeState()
        mRightButtonController.notifyChangeState()
        centerUrlButton.notifyChangeState()

        if (data != null)
            changeTitle(data)
    }

    override fun resetToolBar() {
        mLeftButtonController.resetIcon()
        mRightButtonController.resetIcon()
    }

    fun changeTitle(data: MainTabData) {
        //need post Runnable?
        post {
            if (!AppData.toolbar_always_show_url.get() && data.title != null && !data.isInPageLoad) {
                centerUrlButton.run {
                    setTypeUrl(false)
                    text = data.title
                    gravity = Gravity.CENTER_HORIZONTAL or Gravity.CENTER_VERTICAL
                }
            } else {
                centerUrlButton.run {
                    setTypeUrl(true)
                    text = data.url.decodePunyCodeUrl()
                    gravity = Gravity.START or Gravity.CENTER_VERTICAL
                }
            }
        }
    }

}
