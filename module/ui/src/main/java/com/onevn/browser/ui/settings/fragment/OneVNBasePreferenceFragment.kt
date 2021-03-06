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

package com.onevn.browser.ui.settings.fragment

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.preference.PreferenceScreen
import com.takisoft.preferencex.PreferenceFragmentCompat
import com.onevn.browser.ui.PREFERENCE_FILE_NAME
import com.onevn.browser.ui.R


abstract class OneVNBasePreferenceFragment : PreferenceFragmentCompat() {
    var preferenceResId: Int = 0
        private set

    abstract fun onCreateOneVNPreferences(savedInstanceState: Bundle?, rootKey: String?)

    override fun onCreatePreferencesFix(savedInstanceState: Bundle?, rootKey: String?) {
        preferenceManager.sharedPreferencesName = PREFERENCE_FILE_NAME
        onCreateOneVNPreferences(savedInstanceState, rootKey)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return try {
            super.onCreateView(inflater, container, savedInstanceState)
        } finally {
            activity?.let {
                preferenceManager.sharedPreferencesName = PREFERENCE_FILE_NAME
                val a = it.theme.obtainStyledAttributes(intArrayOf(android.R.attr.listDivider))
                val divider = a.getDrawable(0)
                a.recycle()
                setDivider(divider)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val activity = activity ?: return

        preferenceScreen?.run {
            val key: String? = arguments?.getString(ARG_PREFERENCE_ROOT)
            val title = if (!TextUtils.isEmpty(key)) findPreference(key).title else title
            activity.title = if (TextUtils.isEmpty(title)) getText(R.string.pref_settings) else title
        }
    }

    override fun addPreferencesFromResource(preferencesResId: Int) {
        super.addPreferencesFromResource(preferencesResId)
        this.preferenceResId = preferencesResId
    }

    override fun setPreferencesFromResource(preferencesResId: Int, key: String?) {
        super.setPreferencesFromResource(preferencesResId, key)
        this.preferenceResId = preferencesResId
    }

    open fun onPreferenceStartScreen(pref: PreferenceScreen): Boolean = false
}
