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

package com.onevn.browser.legacy.pattern.action

import android.annotation.SuppressLint
import android.content.Context
import android.webkit.CookieManager
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.onevn.browser.legacy.R
import com.onevn.browser.legacy.pattern.PatternAction
import com.onevn.browser.legacy.tab.manager.MainTabData
import java.io.IOException

class WebSettingPatternAction : PatternAction {

    var userAgentString: String? = null
        private set
    var javaScriptSetting: Int = UNDEFINED
        private set
    var navLock: Int = UNDEFINED
        private set
    var loadImage: Int = UNDEFINED
        private set
    var cookie = UNDEFINED
        private set
    var thirdCookie = UNDEFINED
        private set

    constructor(ua: String?, js: Int, navLock: Int, image: Int, cookie: Int, thirdCookie: Int) {
        userAgentString = ua
        javaScriptSetting = js
        this.navLock = navLock
        loadImage = image
        this.cookie = cookie
        this.thirdCookie = thirdCookie
    }

    @Throws(IOException::class)
    constructor(reader: JsonReader) {
        if (reader.peek() != JsonReader.Token.BEGIN_OBJECT) return
        reader.beginObject()
        while (reader.hasNext()) {
            when (reader.nextName()) {
                FIELD_NAME_UA -> {
                    if (reader.peek() != JsonReader.Token.STRING) return
                    userAgentString = reader.nextString()
                }
                FIELD_NAME_JS -> {
                    if (reader.peek() != JsonReader.Token.NUMBER) return
                    javaScriptSetting = reader.nextInt()
                }
                FIELD_NAME_NAV_LOCK -> {
                    if (reader.peek() != JsonReader.Token.NUMBER) return
                    navLock = reader.nextInt()
                }
                FIELD_NAME_IMAGE -> {
                    if (reader.peek() != JsonReader.Token.NUMBER) return
                    loadImage = reader.nextInt()
                }
                FIELD_NAME_THIRD_COOKIE -> {
                    if (reader.peek() != JsonReader.Token.NUMBER) return
                    thirdCookie = reader.nextInt()
                }
                FIELD_NAME_COOKIE -> {
                    if (reader.peek() != JsonReader.Token.NUMBER) return
                    cookie = reader.nextInt()
                }
                else -> {
                    reader.skipValue()
                }
            }
        }
        reader.endObject()
    }

    override val typeId = PatternAction.WEB_SETTING

    override fun getTitle(context: Context): String {
        return context.getString(R.string.pattern_change_websettings)
    }

    @Throws(IOException::class)
    override fun write(writer: JsonWriter): Boolean {
        writer.value(PatternAction.WEB_SETTING)
        writer.beginObject()
        if (userAgentString != null) {
            writer.name(FIELD_NAME_UA)
            writer.value(userAgentString)
        }
        writer.name(FIELD_NAME_JS)
        writer.value(javaScriptSetting)
        writer.name(FIELD_NAME_NAV_LOCK)
        writer.value(navLock)
        writer.name(FIELD_NAME_IMAGE)
        writer.value(loadImage)
        writer.name(FIELD_NAME_THIRD_COOKIE)
        writer.value(thirdCookie)
        writer.name(FIELD_NAME_COOKIE)
        writer.value(cookie)
        writer.endObject()
        return true
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun run(context: Context, tab: MainTabData, url: String): Boolean {
        val settings = tab.mWebView.webSettings

        if (userAgentString != null)
            settings.userAgentString = userAgentString

        when (javaScriptSetting) {
            ENABLE -> settings.javaScriptEnabled = true
            DISABLE -> settings.javaScriptEnabled = false
        }

        when (navLock) {
            ENABLE -> tab.isNavLock = true
            DISABLE -> tab.isNavLock = false
        }

        when (loadImage) {
            ENABLE -> settings.loadsImagesAutomatically = true
            DISABLE -> settings.loadsImagesAutomatically = false
        }

        when (cookie) {
            ENABLE -> tab.cookieMode = MainTabData.COOKIE_ENABLE
            DISABLE -> tab.cookieMode = MainTabData.COOKIE_DISABLE
        }
        CookieManager.getInstance().setAcceptCookie(tab.isEnableCookie)

        when (thirdCookie) {
            ENABLE -> CookieManager.getInstance().setAcceptThirdPartyCookies(tab.mWebView.webView, true)
            DISABLE -> CookieManager.getInstance().setAcceptThirdPartyCookies(tab.mWebView.webView, false)
        }
        return false
    }

    companion object {
        const val UNDEFINED = 0
        const val ENABLE = 1
        const val DISABLE = 2

        private const val FIELD_NAME_UA = "0"
        private const val FIELD_NAME_JS = "1"
        private const val FIELD_NAME_NAV_LOCK = "2"
        private const val FIELD_NAME_IMAGE = "3"
        private const val FIELD_NAME_THIRD_COOKIE = "4"
        private const val FIELD_NAME_COOKIE = "5"
    }
}
