/*
 * Copyright (C) 2017 Hazuki
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

package jp.hazuki.yuzubrowser.legacy.adblock.faster

import android.net.Uri

class ListFilter(vararg filter: Filter) : ArrayList<Filter>(filter.size), BaseFilter {
    override fun find(key: String, pageUrl: Uri, requestUri: Uri, isThirdParty: Boolean): Filter? {
        return firstOrNull { it.match(key, pageUrl, requestUri, isThirdParty) }
    }

    override fun match(key: String, pageUrl: Uri, requestUri: Uri, isThirdParty: Boolean): Boolean {
        return any { it.match(key, pageUrl, requestUri, isThirdParty) }
    }

    init {
        filter.forEach { add(it) }
    }
}