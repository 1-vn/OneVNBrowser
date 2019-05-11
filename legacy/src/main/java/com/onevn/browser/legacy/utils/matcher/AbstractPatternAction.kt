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

package com.onevn.browser.legacy.utils.matcher

import android.content.Context

import com.squareup.moshi.JsonWriter

import java.io.IOException
import java.io.Serializable

abstract class AbstractPatternAction : Serializable {
    abstract fun getTitle(context: Context): String?

    @Throws(IOException::class)
    abstract fun write(writer: JsonWriter): Boolean
}