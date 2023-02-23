 // NDSoftwares

package com.ndsoftwares.dialogs.core.views

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.res.ResourcesCompat
import com.ndsoftwares.dialogs.core.R
import com.ndsoftwares.dialogs.core.utils.colorOfAttr

/** Custom TextView used for most of the text on the sheets. */
class NDDialogContent
@JvmOverloads constructor(
    ctx: Context,
    attrs: AttributeSet? = null,
    styleAttr: Int = android.R.attr.textViewStyle
) : AppCompatTextView(ctx, attrs, styleAttr) {

    init {

        val a = ctx.obtainStyledAttributes(attrs, R.styleable.NDDialogContent, styleAttr, 0)

        val colorDefault = colorOfAttr(ctx, android.R.attr.textColorPrimary)
        val color = a.getColor(R.styleable.NDDialogContent_ndDialogContentColor, colorDefault)
        setTextColor(color)

        val height = a.getDimensionPixelSize(R.styleable.NDDialogContent_ndDialogContentLineHeight, 0)
        height.takeIf { it != 0 }?.let { lineHeight = height }

        val fontResId = a.getResourceId(R.styleable.NDDialogContent_ndDialogContentFont, 0)
        fontResId.takeIf { it != 0 }?.let { typeface = ResourcesCompat.getFont(ctx, it) }

        val spacing =
            a.getFloat(R.styleable.NDDialogContent_ndDialogContentLetterSpacing, 0f)
        spacing.takeIf { it != 0f }?.let { letterSpacing = it }

        a.recycle()
    }
}
