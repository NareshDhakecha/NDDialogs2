 // NDSoftwares

package com.ndsoftwares.dialogs.core.views

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.res.ResourcesCompat
import com.ndsoftwares.dialogs.core.R
import com.ndsoftwares.dialogs.core.utils.colorOfAttrs

/** Custom TextView used for the digits of the [NDDialogNumericalInput]. */
class NDDialogDigit
@JvmOverloads constructor(
    ctx: Context,
    attrs: AttributeSet? = null,
    styleAttr: Int = android.R.attr.textViewStyle
) : AppCompatTextView(ctx, attrs, styleAttr) {

    init {

        val a = ctx.obtainStyledAttributes(attrs, R.styleable.NDDialogDigit, styleAttr, 0)

        val height = a.getDimensionPixelSize(R.styleable.NDDialogDigit_ndDialogDigitLineHeight, 0)
        height.takeIf { it != 0 }?.let { lineHeight = height }

        val colorDefault = colorOfAttrs(ctx, R.attr.ndDialogContentColor, android.R.attr.textColorPrimary)
        val color = a.getColor(R.styleable.NDDialogDigit_ndDialogDigitColor, colorDefault)
        setTextColor(color)

        val fontResId = a.getResourceId(R.styleable.NDDialogDigit_ndDialogDigitFont, 0)
        fontResId.takeIf { it != 0 }?.let { typeface = ResourcesCompat.getFont(ctx, it) }

        val spacing =
            a.getFloat(R.styleable.NDDialogDigit_ndDialogDigitLetterSpacing, 0f)
        spacing.takeIf { it != 0f }?.let { letterSpacing = it }

        a.recycle()
    }
}
