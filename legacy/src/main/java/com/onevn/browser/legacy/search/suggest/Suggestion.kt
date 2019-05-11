/*
 * Copyright (c) 2017 Hazuki
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.onevn.browser.legacy.search.suggest

class Suggestion : SuggestItem {
    constructor(word: String) {
        this.title = word
        this.suggestHistory = false
    }

    constructor(word: String, history: Boolean) {
        this.title = word
        this.suggestHistory = history
    }

    override val title: String
    override val suggestHistory: Boolean

    override fun equals(other: Any?): Boolean {
        return if (other is Suggestion) {
            title == other.title
        } else false
    }

    override fun hashCode(): Int {
        var result = title.hashCode()
        result = 31 * result + suggestHistory.hashCode()
        return result
    }
}
