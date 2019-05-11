/*
 * Copyright (C) 2017-2019 Hazuki
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

package com.onevn.browser.download.settings

import android.os.Environment
import com.rejasupotaro.android.kvs.annotations.Key
import com.rejasupotaro.android.kvs.annotations.Table
import com.onevn.browser.ui.PREFERENCE_FILE_NAME

@Table(name = PREFERENCE_FILE_NAME)
internal abstract class DownloadPrefsSchema {
    @Key(name = "download_folder")
    var downloadFolder: String = "file://" + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absolutePath
}