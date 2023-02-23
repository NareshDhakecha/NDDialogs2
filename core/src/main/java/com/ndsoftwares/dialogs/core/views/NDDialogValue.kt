 // NDSoftwares

package com.ndsoftwares.dialogs.core.views

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.res.ResourcesCompat
import com.ndsoftwares.dialogs.core.R

/** Custom TextView used for the value of some sheets. */
class NDDialogValue
@JvmOverloads constructor(
    ctx: Context,
    attrs: AttributeSet? = null,
    styleAttr: Int = android.R.attr.textViewStyle
) : AppCompatTextView(ctx, attrs, styleAttr) {

    init {

        val a = ctx.obtainStyledAttributes(attrs, R.styleable.NDDialogValue, styleAttr, 0)

        val height =
            a.getDimensionPixelSize(R.styleable.NDDialogValue_ndDialogValueLineHeight, 0)
        height.takeIf { it != 0 }?.let { lineHeight = height }

        val fontResId = a.getResourceId(R.styleable.NDDialogValue_ndDialogValueFont, 0)
        fontResId.takeIf { it != 0 }?.let { typeface = ResourcesCompat.getFont(ctx, it) }

        val spacing =
            a.getFloat(R.styleable.NDDialogValue_ndDialogValueLetterSpacing, 0f)
        spacing.takeIf { it != 0f }?.let { letterSpacing = it }

        a.recycle()
    }
}
