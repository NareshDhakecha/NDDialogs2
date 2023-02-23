 // NDSoftwares

package com.ndsoftwares.dialogs.core.views

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.res.ResourcesCompat
import com.ndsoftwares.dialogs.core.R
import com.ndsoftwares.dialogs.core.utils.colorOfAttrs

/** Custom TextView used for for the title of a sheet. */
class NDDialogTitle
@JvmOverloads constructor(
    ctx: Context,
    attrs: AttributeSet? = null,
    styleAttr: Int = android.R.attr.textViewStyle
) : AppCompatTextView(ctx, attrs, styleAttr) {

    init {

        val a = ctx.obtainStyledAttributes(attrs, R.styleable.NDDialogTitle, styleAttr, 0)

        val colorDefault = colorOfAttrs(ctx, R.attr.ndDialogPrimaryColor, com.google.android.material.R.attr.colorPrimary)
        val color = a.getColor(R.styleable.NDDialogTitle_ndDialogTitleColor, colorDefault)
        setTextColor(color)

        val height = a.getDimensionPixelSize(R.styleable.NDDialogTitle_ndDialogTitleLineHeight, 0)
        height.takeIf { it != 0 }?.let { lineHeight = height }

        val fontResId = a.getResourceId(R.styleable.NDDialogTitle_ndDialogTitleFont, 0)
        fontResId.takeIf { it != 0 }?.let { typeface = ResourcesCompat.getFont(ctx, it) }

        val spacing =
            a.getFloat(R.styleable.NDDialogTitle_ndDialogTitleLetterSpacing, 0f)
        spacing.takeIf { it != 0f }?.let { letterSpacing = it }

        a.recycle()
    }
}
