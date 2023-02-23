 // NDSoftwares

@file:Suppress("unused")

package com.ndsoftwares.dialogs.core.views

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.Gravity
import android.widget.ImageView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel
import com.ndsoftwares.dialogs.core.R
import com.ndsoftwares.dialogs.core.utils.*

/** Container with a handle drawable. */
internal class NDDialogHandle
@JvmOverloads constructor(
    val ctx: Context,
    attrs: AttributeSet? = null
) : LinearLayoutCompat(ctx, attrs) {

    companion object {
        private const val DEFAULT_CORNER_FAMILY = CornerFamily.ROUNDED
        private const val DEFAULT_CORNER_RADIUS = 8f
    }

    init {
        orientation = VERTICAL
        setPadding(8.toDp(), 8.toDp(), 8.toDp(), 8.toDp())

        val cornerFamily = intOfAttrs(
            ctx,
            R.attr.ndDialogHandleCornerFamily
        ) ?: DEFAULT_CORNER_FAMILY

        val cornerRadius = dimensionOfAttrs(
            ctx,
            R.attr.ndDialogHandleCornerRadius
        ) ?: DEFAULT_CORNER_RADIUS.toDp()

        val fillColor = colorOfAttr(ctx, R.attr.ndDialogHandleFillColor).takeUnlessNotResolved()
                ?: ContextCompat.getColor(ctx, R.color.ndDialogDividerColor)

        val borderColor =
            colorOfAttr(ctx, R.attr.ndDialogHandleBorderColor).takeUnlessNotResolved()
                ?: ContextCompat.getColor(ctx, R.color.ndDialogDividerColor)

        val borderWidth = dimensionOfAttrs(ctx, R.attr.ndDialogHandleBorderWidth)

        val shapeModel = ShapeAppearanceModel().toBuilder().apply {
            setAllCorners(cornerFamily, cornerRadius)
        }.build()

        val drawable = MaterialShapeDrawable(shapeModel).apply {
            this.fillColor = ColorStateList.valueOf(fillColor)
            borderWidth?.let { setStroke(it, borderColor) }
        }

        val handleWidth = dimensionOfAttrs(ctx, R.attr.ndDialogHandleWidth) ?: 28.getDp()
        val handleHeight = dimensionOfAttrs(ctx, R.attr.ndDialogHandleHeight) ?: 4.getDp()

        addView(ImageView(ctx).apply {
            layoutParams = LayoutParams(
                handleWidth.toInt(),
                handleHeight.toInt()
            ).apply {
                setMargins(0, 8.toDp(), 0, 8.toDp())
            }
            gravity = Gravity.CENTER
            setImageDrawable(drawable)
        })
    }
}
