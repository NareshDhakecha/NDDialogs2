 // NDSoftwares

package com.ndsoftwares.dialogs.core.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.ndsoftwares.dialogs.core.R
import com.ndsoftwares.dialogs.core.utils.colorOf
import com.ndsoftwares.dialogs.core.utils.colorOfAttr

/** Custom Divider. */
class NDDialogDivider
@JvmOverloads constructor(
    ctx: Context,
    attrs: AttributeSet? = null,
    styleAttrs: Int = 0,
    styleRes: Int = 0
) : View(ctx, attrs, styleAttrs, styleRes) {

    init {
        val dividerColorDefault = colorOf(ctx, R.color.ndDialogDividerColor)
        val dividerColor = colorOfAttr(ctx, R.attr.ndDialogDividerColor)
        setBackgroundColor(dividerColor.takeIf { it != 0 } ?: dividerColorDefault)
    }
}
