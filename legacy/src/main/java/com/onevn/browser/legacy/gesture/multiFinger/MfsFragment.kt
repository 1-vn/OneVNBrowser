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

package com.onevn.browser.legacy.gesture.multiFinger

import android.content.Context
import android.os.Bundle
import com.onevn.browser.legacy.R
import com.onevn.browser.legacy.settings.activity.OneVNPreferenceFragment

class MfsFragment : OneVNPreferenceFragment() {

    private var listener: OnMfsFragmentListener? = null

    override fun onCreateOneVNPreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.pref_multi_finger_settings)

        retainInstance = true

        findPreference("multi_finger_gesture_list").setOnPreferenceClickListener {
            listener?.onGoToList()
            false
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (activity is OnMfsFragmentListener) {
            listener = activity as OnMfsFragmentListener
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    internal interface OnMfsFragmentListener {
        fun onGoToList()
    }
}
