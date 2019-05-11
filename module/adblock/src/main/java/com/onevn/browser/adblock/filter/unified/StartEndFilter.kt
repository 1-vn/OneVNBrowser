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

package com.onevn.browser.adblock.filter.unified

import android.net.Uri

class StartEndFilter(
    filter: String,
    contentType: Int,
    ignoreCase: Boolean,
    domains: DomainMap?,
    thirdParty: Int
) : UnifiedFilter(filter, contentType, ignoreCase, domains, thirdParty) {
    override val type: Int
        get() = FILTER_TYPE_START_END

    override fun check(url: Uri): Boolean {
        val urlStr = url.schemeSpecificPart
        if (urlStr.regionMatches(2, pattern, 0, pattern.length, ignoreCase)) {
            return if (pattern.length + 2 == urlStr.length) {
                true
            } else {
                urlStr[pattern.length + 2].checkSeparator()
            }
        }
        return false
    }
}
