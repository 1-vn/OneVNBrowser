package com.onevn.browser.legacy.webkit.handler

import android.content.Context
import com.onevn.browser.legacy.utils.extensions.setClipboardWithToast
import java.lang.ref.WeakReference

class WebSrcImageCopyUrlHandler(context: Context) : WebSrcImageHandler() {
    private val mReference: WeakReference<Context> = WeakReference(context)

    override fun handleUrl(url: String) {
        mReference.get()?.run {
            setClipboardWithToast(url)
        }
    }
}
