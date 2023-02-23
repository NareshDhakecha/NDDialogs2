 // NDSoftwares

@file:Suppress("unused")

package com.ndsoftwares.dialogs.core.views

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.ShapeAppearanceModel
import com.ndsoftwares.dialogs.core.R
import com.ndsoftwares.dialogs.core.ButtonStyle
import com.ndsoftwares.dialogs.core.utils.*

internal typealias ButtonClickListener = () -> Unit

/** Container that contains a button. */
class NDDialogButtonContainer
@JvmOverloads constructor(
    val ctx: Context,
    attrs: AttributeSet? = null,
) : LinearLayoutCompat(ctx, attrs) {

    companion object {
        private const val DEFAULT_CORNER_FAMILY = CornerFamily.ROUNDED
        private const val DEFAULT_CORNER_RADIUS = 8f
        private const val BUTTON_MIN_WIDTH = 120
        private const val BUTTON_ICON_PADDING = 12
    }

    private var negativeBtn: MaterialButton? = null
    private var positiveBtn: MaterialButton? = null

    init {
        orientation = VERTICAL
    }

    private fun createButton(
        style: ButtonStyle? = null,
        btnText: String,
        @DrawableRes btnDrawable: Int?,
        btnListener: ButtonClickListener,
        negative: Boolean,
        shapeModel: ShapeAppearanceModel.Builder,
    ) {

        val buttonStyleAttr = if (negative) R.attr.ndDialogNegativeButtonType
        else R.attr.ndDialogPositiveButtonType

        val buttonStyleValue = intOfAttrs(ctx, buttonStyleAttr) ?: ButtonStyle.TEXT.ordinal
        val buttonStyle = style ?: ButtonStyle.values()[buttonStyleValue]

        val primaryColor = colorOfAttrs(
            ctx,
            R.attr.ndDialogButtonColor,
            R.attr.ndDialogPrimaryColor,
            com.google.android.material.R.attr.colorPrimary
        )
        val rippleColor = getHighlightOfColor(primaryColor)

        val btnWidthLayoutParam =
            intOfAttrs(ctx, R.attr.ndDialogButtonWidth) ?: ViewGroup.LayoutParams.WRAP_CONTENT

        gravity = Gravity.CENTER

        val outlinedButtonBorderWidth = dimensionOfAttrs(ctx,
            if (negative) R.attr.ndDialogNegativeButtonOutlinedButtonBorderWidth else R.attr.ndDialogPositiveButtonOutlinedButtonBorderWidth,
            R.attr.ndDialogButtonOutlinedButtonBorderWidth
        )

        val outlinedButtonBorderColor = colorOfAttrs(ctx,
            if (negative) R.attr.ndDialogNegativeButtonOutlinedButtonBorderColor else R.attr.ndDialogPositiveButtonOutlinedButtonBorderColor,
            R.attr.ndDialogButtonOutlinedButtonBorderColor
        )

        addView(NDDialogButton(ctx, null, buttonStyle.styleRes).apply {

            layoutParams =
                ViewGroup.LayoutParams(btnWidthLayoutParam, ViewGroup.LayoutParams.WRAP_CONTENT)

            text = btnText
            btnDrawable?.let { icon = ContextCompat.getDrawable(context, it) }
            iconGravity = MaterialButton.ICON_GRAVITY_TEXT_START
            iconPadding = BUTTON_ICON_PADDING.toDp()
            iconTint = ColorStateList.valueOf(primaryColor)
            minWidth = BUTTON_MIN_WIDTH.toDp()
            minimumWidth = BUTTON_MIN_WIDTH.toDp()

            setOnClickListener { btnListener.invoke() }

            when (buttonStyle) {
                ButtonStyle.TEXT, ButtonStyle.OUTLINED -> {
                    setRippleColor(ColorStateList.valueOf(rippleColor))
                    setTextColor(primaryColor)
                }
                ButtonStyle.NORMAL -> {
                    setBackgroundColor(primaryColor)
                }
            }

            shapeAppearanceModel = shapeModel.apply {
                when (buttonStyle) {
                    ButtonStyle.TEXT -> {
                        strokeWidth =
                            0 /* Set border stroke width to zero to remove the border and simulate a normal TextButton. */
                    }
                    ButtonStyle.OUTLINED -> {
                        outlinedButtonBorderColor.takeUnlessNotResolved()
                            ?.let { strokeColor = ColorStateList.valueOf(it) }
                        outlinedButtonBorderWidth?.let { strokeWidth = it.toInt() }
                    }
                    else -> {
                    }
                }
            }.build()

        }.also { if (negative) negativeBtn = it else positiveBtn = it })
    }

    /** Setup a negative button. */
    fun setupNegativeButton(
        buttonStyle: ButtonStyle?,
        btnText: String,
        @DrawableRes btnDrawable: Int?,
        btnListener: ButtonClickListener,
    ) {

        val parentFamily = R.attr.ndDialogButtonCornerFamily
        val parentRadius = R.attr.ndDialogButtonCornerRadius

        val negParentFamily = R.attr.ndDialogNegativeButtonCornerFamily
        val negParentRadius = R.attr.ndDialogNegativeButtonCornerRadius

        val negBtnBottomLeftFamily = intOfAttrs(
            ctx,
            R.attr.ndDialogNegativeButtonBottomLeftCornerFamily,
            negParentFamily,
            parentFamily
        ) ?: DEFAULT_CORNER_FAMILY

        val negBtnBottomRightFamily = intOfAttrs(
            ctx,
            R.attr.ndDialogNegativeButtonBottomRightCornerFamily,
            negParentFamily,
            parentFamily
        ) ?: DEFAULT_CORNER_FAMILY

        val negBtnTopLeftFamily = intOfAttrs(
            ctx,
            R.attr.ndDialogNegativeButtonTopLeftCornerFamily,
            negParentFamily,
            parentFamily
        ) ?: DEFAULT_CORNER_FAMILY

        val negBtnTopRightFamily = intOfAttrs(
            ctx,
            R.attr.ndDialogNegativeButtonTopRightCornerFamily,
            negParentFamily,
            parentFamily
        ) ?: DEFAULT_CORNER_FAMILY

        val negBtnBottomLeftRadius = dimensionOfAttrs(
            ctx,
            R.attr.ndDialogNegativeButtonBottomLeftCornerRadius,
            negParentRadius,
            parentRadius
        ) ?: DEFAULT_CORNER_RADIUS

        val negBtnBottomRightRadius = dimensionOfAttrs(
            ctx,
            R.attr.ndDialogNegativeButtonBottomRightCornerRadius,
            negParentRadius,
            parentRadius
        ) ?: DEFAULT_CORNER_RADIUS

        val negBtnTopLeftRadius = dimensionOfAttrs(
            ctx,
            R.attr.ndDialogNegativeButtonTopLeftCornerRadius,
            negParentRadius,
            parentRadius
        ) ?: DEFAULT_CORNER_RADIUS

        val negBtnTopRightRadius = dimensionOfAttrs(
            ctx,
            R.attr.ndDialogNegativeButtonTopRightCornerRadius,
            negParentRadius,
            parentRadius
        ) ?: DEFAULT_CORNER_RADIUS

        val shapeModel = ShapeAppearanceModel().toBuilder().apply {
            setBottomLeftCorner(negBtnBottomLeftFamily, negBtnBottomLeftRadius.toDp())
            setBottomRightCorner(negBtnBottomRightFamily, negBtnBottomRightRadius.toDp())
            setTopLeftCorner(negBtnTopLeftFamily, negBtnTopLeftRadius.toDp())
            setTopRightCorner(negBtnTopRightFamily, negBtnTopRightRadius.toDp())
        }

        createButton(buttonStyle, btnText, btnDrawable, btnListener, true, shapeModel)
    }

    /** Setup a positive button. */
    fun setupPositiveButton(
        buttonStyle: ButtonStyle?,
        btnText: String,
        @DrawableRes btnDrawable: Int?,
        btnListener: ButtonClickListener,
    ) {

        val parentFamily = R.attr.ndDialogButtonCornerFamily
        val parentRadius = R.attr.ndDialogButtonCornerRadius

        val posParentFamily = R.attr.ndDialogPositiveButtonCornerFamily
        val posParentRadius = R.attr.ndDialogPositiveButtonCornerRadius

        val posBtnBottomLeftFamily = intOfAttrs(
            ctx,
            R.attr.ndDialogPositiveButtonBottomLeftCornerFamily,
            posParentFamily,
            parentFamily
        ) ?: DEFAULT_CORNER_FAMILY

        val posBtnBottomRightFamily = intOfAttrs(
            ctx,
            R.attr.ndDialogPositiveButtonBottomRightCornerFamily,
            posParentFamily,
            parentFamily
        ) ?: DEFAULT_CORNER_FAMILY

        val posBtnTopLeftFamily = intOfAttrs(
            ctx,
            R.attr.ndDialogPositiveButtonTopLeftCornerFamily,
            posParentFamily,
            parentFamily
        ) ?: DEFAULT_CORNER_FAMILY

        val posBtnTopRightFamily = intOfAttrs(
            ctx,
            R.attr.ndDialogPositiveButtonTopRightCornerFamily,
            posParentFamily,
            parentFamily
        ) ?: DEFAULT_CORNER_FAMILY

        val posBtnBottomLeftRadius = dimensionOfAttrs(
            ctx,
            R.attr.ndDialogPositiveButtonBottomLeftCornerRadius,
            posParentRadius,
            parentRadius
        ) ?: DEFAULT_CORNER_RADIUS

        val posBtnBottomRightRadius = dimensionOfAttrs(
            ctx,
            R.attr.ndDialogPositiveButtonBottomRightCornerRadius,
            posParentRadius,
            parentRadius
        ) ?: DEFAULT_CORNER_RADIUS

        val posBtnTopLeftRadius = dimensionOfAttrs(
            ctx,
            R.attr.ndDialogPositiveButtonTopLeftCornerRadius,
            posParentRadius,
            parentRadius
        ) ?: DEFAULT_CORNER_RADIUS

        val posBtnTopRightRadius = dimensionOfAttrs(
            ctx,
            R.attr.ndDialogPositiveButtonTopRightCornerRadius,
            posParentRadius,
            parentRadius
        ) ?: DEFAULT_CORNER_RADIUS

        val shapeModel = ShapeAppearanceModel().toBuilder().apply {
            setBottomLeftCorner(posBtnBottomLeftFamily, posBtnBottomLeftRadius.toDp())
            setBottomRightCorner(posBtnBottomRightFamily, posBtnBottomRightRadius.toDp())
            setTopLeftCorner(posBtnTopLeftFamily, posBtnTopLeftRadius.toDp())
            setTopRightCorner(posBtnTopRightFamily, posBtnTopRightRadius.toDp())
        }

        createButton(buttonStyle, btnText, btnDrawable, btnListener, false, shapeModel)
    }

    /** Make positive button clickable. */
    fun positiveButtonClickable(isClickable: Boolean) {
        this.positiveBtn?.isClickable = isClickable
    }

    /** Set positive button listener. */
    fun negativeButtonListener(btnListener: ButtonClickListener) {
        this.negativeBtn?.setOnClickListener { btnListener.invoke() }
    }

    /** Set positive button listener. */
    fun positiveButtonListener(btnListener: ButtonClickListener) {
        this.positiveBtn?.setOnClickListener { btnListener.invoke() }
    }
}
