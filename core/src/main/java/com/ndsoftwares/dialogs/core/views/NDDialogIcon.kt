 // NDSoftwares

package com.ndsoftwares.dialogs.core.views

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.RippleDrawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel
import com.ndsoftwares.dialogs.core.utils.getDp
import com.ndsoftwares.dialogs.core.utils.getHighlightColor

/** Custom ImageView used for icon buttons on the sheets. */
class NDDialogIcon
@JvmOverloads constructor(
    ctx: Context,
    attrs: AttributeSet? = null,
    styleAttr: Int = 0
) : AppCompatImageView(ctx, attrs, styleAttr) {

    init {

        val shapeModel = ShapeAppearanceModel().toBuilder().apply {
            setAllCorners(CornerFamily.ROUNDED, 45.getDp())
        }.build()

        val shapeDrawable = MaterialShapeDrawable(shapeModel)
        val rippleColor = ColorStateList.valueOf(getHighlightColor(ctx))

        background = RippleDrawable(rippleColor, null, shapeDrawable)
    }
}
