 // NDSoftwares

package com.ndsoftwares.dialogs.core.views

import android.content.Context
import android.util.AttributeSet
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.textfield.TextInputEditText
import com.ndsoftwares.dialogs.core.R
import com.ndsoftwares.dialogs.core.utils.colorOfAttr

/** Custom EditTextView used for text input. */
class NDDialogInputEditText
@JvmOverloads constructor(
    ctx: Context,
    attrs: AttributeSet? = null
) : TextInputEditText(ctx, attrs) {

    init {

        // Ignore that this custom view uses the same styleable as SheetContent, as it is supposed to look the same

        val a = ctx.obtainStyledAttributes(attrs, R.styleable.NDDialogContent, 0, 0)

        val colorDefault = colorOfAttr(ctx, android.R.attr.textColorPrimary)
        val color = a.getColor(R.styleable.NDDialogContent_ndDialogContentColor, colorDefault)
        setTextColor(color)

        val fontResId = a.getResourceId(R.styleable.NDDialogContent_ndDialogContentFont, 0)
        fontResId.takeIf { it != 0 }?.let { typeface = ResourcesCompat.getFont(ctx, it) }

        val spacing =
            a.getFloat(R.styleable.NDDialogContent_ndDialogContentLetterSpacing, 0f)
        spacing.takeIf { it != 0f }?.let { letterSpacing = it }

        a.recycle()
    }
}
