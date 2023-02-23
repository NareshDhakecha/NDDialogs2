 // NDSoftwares

package com.ndsoftwares.dialogs.core.views

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import com.google.android.material.textfield.TextInputLayout
import com.ndsoftwares.dialogs.core.R
import com.ndsoftwares.dialogs.core.utils.getPrimaryColor
import com.ndsoftwares.dialogs.core.utils.toDp


/** Custom TextInputLayout. */
class NDDialogTextInputLayout
@JvmOverloads constructor(
    ctx: Context,
    attrs: AttributeSet? = null,
    styleAttrs: Int = com.google.android.material.R.attr.textInputStyle,
) : TextInputLayout(ctx, attrs, styleAttrs) {

    companion object {
        private const val DEFAULT_CORNER_RADIUS = 8f
    }

    init {

        val a = ctx.obtainStyledAttributes(attrs, R.styleable.NDDialogTextInputLayout, 0, 0)

        val cornerRadius = a.getDimension(
            R.styleable.NDDialogTextInputLayout_ndDialogTextInputLayoutCornerRadius,
            DEFAULT_CORNER_RADIUS.toDp()
        )

        val topLeftCornerRadius = a.getDimension(
            R.styleable.NDDialogTextInputLayout_ndDialogTextInputLayoutTopLeftCornerRadius,
            cornerRadius
        )

        val topRightCornerRadius = a.getDimension(
            R.styleable.NDDialogTextInputLayout_ndDialogTextInputLayoutTopRightCornerRadius,
            cornerRadius
        )

        val bottomLeftRadius = a.getDimension(
            R.styleable.NDDialogTextInputLayout_ndDialogTextInputLayoutBottomLeftCornerRadius,
            cornerRadius
        )

        val bottomRightRadius = a.getDimension(
            R.styleable.NDDialogTextInputLayout_ndDialogTextInputLayoutBottomRightCornerRadius,
            cornerRadius
        )

        setBoxCornerRadii(
            topLeftCornerRadius,
            topRightCornerRadius,
            bottomLeftRadius,
            bottomRightRadius
        )

        val primaryColor = getPrimaryColor(ctx)

        val endIconColor = a.getColor(
            R.styleable.NDDialogTextInputLayout_ndDialogTextInputLayoutEndIconColor,
            0
        )
        endIconColor.takeIf { it != 0 }?.let { setEndIconTintList(ColorStateList.valueOf(it)) }

        val helperTextColor = a.getColor(
            R.styleable.NDDialogTextInputLayout_ndDialogTextInputLayoutHelperTextColor,
            primaryColor
        )
        setHelperTextColor(ColorStateList.valueOf(helperTextColor))

        val boxFocusedStrokeColor = a.getColor(
            R.styleable.NDDialogTextInputLayout_ndDialogTextInputLayoutBoxStrokeColor,
            primaryColor
        )

        val states = arrayOf(intArrayOf(android.R.attr.state_enabled))
        val colors = intArrayOf(boxFocusedStrokeColor)
        setBoxStrokeColorStateList(ColorStateList(states, colors))

        val hintColor = a.getColor(
            R.styleable.NDDialogTextInputLayout_ndDialogTextInputLayoutHintTextColor,
            primaryColor
        )
        defaultHintTextColor = ColorStateList.valueOf(hintColor)

        val boxStrokeErrorColor = a.getColor(
            R.styleable.NDDialogTextInputLayout_ndDialogTextInputLayoutBoxStrokeErrorColor,
            0
        )
        boxStrokeErrorColor.takeIf { it != 0 }?.let { setBoxStrokeErrorColor(ColorStateList.valueOf(it)) }

        val errorTextColor = a.getColor(
            R.styleable.NDDialogTextInputLayout_ndDialogTextInputLayoutErrorTextColor,
            0
        )
        errorTextColor.takeIf { it != 0 }?.let {
            setErrorTextColor(ColorStateList.valueOf(it))
        }

        val errorDrawableColor = a.getColor(
            R.styleable.NDDialogTextInputLayout_ndDialogTextInputLayoutErrorDrawableColor,
            0
        )
        errorDrawableColor.takeIf { it != 0 }?.let {
            setErrorIconTintList(ColorStateList.valueOf(it))
        }


        a.recycle()
    }
}
