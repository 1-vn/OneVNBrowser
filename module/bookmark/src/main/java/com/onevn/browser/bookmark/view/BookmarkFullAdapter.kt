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

package com.onevn.browser.bookmark.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.onevn.bookmark.R
import com.onevn.browser.bookmark.item.BookmarkItem
import com.onevn.browser.bookmark.item.BookmarkSite
import com.onevn.browser.core.utility.utils.FontUtils
import com.onevn.browser.ui.extensions.decodePunyCodeUrlHost

class BookmarkFullAdapter(
    context: Context,
    list: MutableList<BookmarkItem>,
    pickMode: Boolean,
    openNewTab: Boolean,
    fontSize: Int,
    bookmarkItemListener: OnBookmarkRecyclerListener
) : BookmarkItemAdapter(context, list, pickMode, openNewTab, fontSize, bookmarkItemListener) {

    override fun onBindViewHolder(holder: BookmarkFolderHolder, item: BookmarkItem, position: Int) {
        super.onBindViewHolder(holder, item, position)

        if (item is BookmarkSite && holder is BookmarkSiteHolder) {
            var host: String? = if (item.url.startsWith("javascript:"))
                context.getString(R.string.bookmarklet)
            else
                item.url.decodePunyCodeUrlHost()
            if (host.isNullOrEmpty()) host = item.url

            holder.url.text = host
        }
    }

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup?, viewType: Int): BookmarkFolderHolder {
        return when (viewType) {
            TYPE_SITE -> BookmarkSiteHolder(inflater.inflate(R.layout.bookmark_item_site, parent, false), this)
            TYPE_FOLDER -> BookmarkFolderHolder(inflater.inflate(R.layout.bookmark_item_folder, parent, false), this)
            else -> throw IllegalStateException("Unknown BookmarkItem type")
        }
    }

    class BookmarkSiteHolder(itemView: View, adapter: BookmarkFullAdapter) : SimpleBookmarkSiteHolder(itemView, adapter) {
        val url: TextView = itemView.findViewById(R.id.urlTextView)

        init {
            val fontSize = adapter.fontSize
            if (fontSize >= 0) {
                url.textSize = FontUtils.getSmallerTextSize(fontSize).toFloat()
            }
        }
    }
}
