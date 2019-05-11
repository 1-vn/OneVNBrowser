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

package com.onevn.browser.legacy.settings.data

import android.content.Context
import com.onevn.browser.adblock.repository.abp.AbpDatabase
import com.onevn.browser.adblock.repository.abp.AbpEntity
import kotlinx.coroutines.runBlocking

fun initAbpFilter(context: Context, abpDatabase: AbpDatabase) = runBlocking {
    val sites = context.assets.open("adblock/onevn_filter.txt").bufferedReader().readLines()
    abpDatabase
            .abpDao()
            .inset(sites
                    .map { it.split(',') }
                    .map { AbpEntity(url = it[2], title = it[1], enabled = it[0] == "1") })
}

fun disableOneVNList(abpDatabase: AbpDatabase) = runBlocking {
    val dao = abpDatabase.abpDao()
    val entity = dao.getAll()[0]
    entity.enabled = false
    dao.update(entity)
}
