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

package com.onevn.browser.di

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import com.onevn.browser.OneVNBrowserApplication
import com.onevn.browser.adblock.AdBlockModule
import com.onevn.browser.adblock.AdBlockUiModule
import com.onevn.browser.bookmark.BookmarkUiModule
import com.onevn.browser.browser.di.ActivityModule
import com.onevn.browser.download.DownloadModule
import com.onevn.browser.legacy.search.SearchModule
import com.onevn.browser.legacy.settings.di.SettingsModule
import com.onevn.browser.legacy.useragent.UserAgentModule
import com.onevn.browser.legacy.webencode.WebEncodeModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    AppModule::class,
    ActivityModule::class,
    ProviderModule::class,
    SettingsModule::class,
    SearchModule::class,
    WebEncodeModule::class,
    UserAgentModule::class,
    DownloadModule::class,
    AdBlockModule::class,
    AdBlockUiModule::class,
    BookmarkUiModule::class
])
interface AppComponent : AndroidInjector<OneVNBrowserApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: OneVNBrowserApplication): Builder

        fun build(): AppComponent
    }
}
