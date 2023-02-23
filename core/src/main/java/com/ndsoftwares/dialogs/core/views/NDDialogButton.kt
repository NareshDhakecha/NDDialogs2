 // NDSoftwares

package com.ndsoftwares.dialogs.core.views

import android.content.Context
import android.util.AttributeSet
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.button.MaterialButton
import com.ndsoftwares.dialogs.core.R

/** Custom text button used for the bottom buttons view. */
class NDDialogButton
@JvmOverloads constructor(
    ctx: Context,
    attrs: AttributeSet? = null,
    styleAttrs: Int = android.R.attr.buttonStyle
) : MaterialButton(ctx, attrs, styleAttrs) {

    init {

        val a = ctx.obtainStyledAttributes(attrs, R.styleable.NDDialogButton, styleAttrs, 0)

        val fontResId = a.getResourceId(R.styleable.NDDialogButton_ndDialogButtonTextFont, 0)
        fontResId.takeIf { it != 0 }?.let { typeface = ResourcesCompat.getFont(ctx, it) }

        val spacing =
            a.getFloat(R.styleable.NDDialogButton_ndDialogButtonTextLetterSpacing, 0f)
        spacing.takeIf { it != 0f }?.let { letterSpacing = it }

        a.recycle()
    }
}
