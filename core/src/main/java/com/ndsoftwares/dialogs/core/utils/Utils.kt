 // NDSoftwares

package com.ndsoftwares.dialogs.core.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.annotation.RestrictTo

/** Save a text into the clipboard. */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
fun copyIntoClipboard(ctx: Context, label: String, value: String) {
    val clipboard = ctx.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText(label, value)
    clipboard.setPrimaryClip(clip)
}

/** Receive the clipboard data. */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
fun pasteFromClipboard(ctx: Context): String? {
    val clipboard = ctx.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val item = clipboard.primaryClip?.getItemAt(0)
    return item?.text?.toString()
}