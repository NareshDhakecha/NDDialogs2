 // NDSoftwares

package com.ndsoftwares.dialogs.core.utils

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.annotation.RestrictTo
import androidx.appcompat.widget.AppCompatImageView
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel


/**
 * Clip the top corners of a ImageView. Works with any [CornerFamily].
 * Replaces the current background drawable with a custom drawable with clipped corners.
 * The previously set background color of the ImageView is automatically applied to the new background drawable.
 */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
fun AppCompatImageView.clipTopCorners(@CornerFamily cornerFamily: Int, cornerRadius: Float) {

    val colorDrawable = background as ColorDrawable?
    val backgroundColor = colorDrawable?.color ?: Color.TRANSPARENT

    val shapeModel = ShapeAppearanceModel().toBuilder().apply {
        setTopRightCorner(cornerFamily, cornerRadius)
        setTopLeftCorner(cornerFamily, cornerRadius)
    }.build()

    val shapeDrawable = MaterialShapeDrawable(shapeModel).apply {
        fillColor = ColorStateList.valueOf(backgroundColor)
    }

    background = shapeDrawable
    clipToOutline = true
}

