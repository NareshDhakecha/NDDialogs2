 // NDSoftwares

package com.ndsoftwares.dialogs.core.views

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.graphics.Typeface
import android.graphics.drawable.RippleDrawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.ndsoftwares.dialogs.core.R
import com.ndsoftwares.dialogs.core.utils.getHighlightColor
import com.ndsoftwares.dialogs.core.utils.getPrimaryColor
import com.ndsoftwares.dialogs.core.utils.toDp

typealias DigitClickListener = (value: Int) -> Unit
typealias ImageClickListener = () -> Unit

/**
 * Custom view which creates a numerical digits view with 2 additional icons for actions.
 */
class NDDialogNumericalInput
@JvmOverloads constructor(
    val ctx: Context,
    attrs: AttributeSet? = null,
    styleAttr: Int = 0
) : LinearLayout(ctx, attrs, styleAttr) {

    companion object {
        private const val ROWS = 4
        private const val COLUMNS = 3
        private const val LEFT_IMAGE_POSITION = 10
        private const val RIGHT_IMAGE_POSITION = 12
    }

    private val primaryColor = getPrimaryColor(ctx)
    private val highlightColor = getHighlightColor(ctx)

    private val digits = mutableListOf<NDDialogDigit>()
    private lateinit var leftImage: ImageView
    private lateinit var rightImage: ImageView
    private var digitListener: DigitClickListener? = null
    private var leftImageListener: ImageClickListener? = null
    private var rightImageListener: ImageClickListener? = null

    init {
        orientation = VERTICAL
        var digitNumber = 1
        repeat(ROWS) { row ->
            val rowLayout = LinearLayout(ctx).apply {
                layoutParams =
                    LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT).apply {
                        if (row < 3) setMargins(0, 0, 0, 16.toDp())
                    }
                orientation = HORIZONTAL
            }
            repeat(COLUMNS) {

                when (digitNumber) {
                    LEFT_IMAGE_POSITION -> {
                        val view = getDrawableView()
                        view.first.setOnClickListener { leftImageListener?.invoke() }
                        rowLayout.addView(view.first)
                        leftImage = view.second
                    }
                    RIGHT_IMAGE_POSITION -> {
                        val view = getDrawableView()
                        view.first.setOnClickListener { rightImageListener?.invoke() }
                        rowLayout.addView(view.first)
                        rightImage = view.second
                    }
                    else -> {
                        val digitValue = (digitNumber.takeIf { it != 11 } ?: 0)
                        val view = getDigitView(digitValue)
                        view.setOnClickListener { digitListener?.invoke(it.tag as Int) }
                        rowLayout.addView(view)
                        digits.add(digitValue.takeIf { it == 0 } ?: digits.size, view)
                    }
                }
                digitNumber++
            }
            addView(rowLayout)
        }
    }

    private fun getDrawableView(): Pair<LinearLayout, ImageView> {

        val imageView = ImageView(ctx).apply {
            layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        }

        val view = LinearLayout(ctx).apply {
            orientation = VERTICAL
            layoutParams = LayoutParams(0, LayoutParams.MATCH_PARENT, 1f)
            minimumHeight = 42.toDp()
            background = ContextCompat.getDrawable(ctx, R.drawable.nddialog_ripple_bg_rounded)
            gravity = Gravity.CENTER
            isClickable = true
            isFocusable = true
            addView(imageView)
        }
        view.changeHighlightColor()
        return Pair(view, imageView)
    }

    private fun getDigitView(digitValue: Int): NDDialogDigit {

        val view = NDDialogDigit(ctx).apply {
            minimumHeight = 42.toDp()
            layoutParams = LayoutParams(0, LayoutParams.WRAP_CONTENT, 1f)
            background = ContextCompat.getDrawable(ctx, R.drawable.nddialog_ripple_bg_rounded)
            setTextAppearance(ctx, com.google.android.material.R.style.TextAppearance_MaterialComponents_Headline5)
            setPadding(
                8.toDp(),
                8.toDp(),
                8.toDp(),
                8.toDp()
            )
            isClickable = true
            textAlignment = TEXT_ALIGNMENT_CENTER
            gravity = Gravity.CENTER
            setTypeface(this.typeface, Typeface.BOLD)
            isFocusable = true
            tag = digitValue
            text = digitValue.toString()
        }
        view.changeHighlightColor()
        return view
    }

    /** Set digit listener. */
    fun digitListener(listener: DigitClickListener) {
        this.digitListener = listener
    }

    /** Set left image listener. */
    fun leftImageListener(listener: ImageClickListener) {
        this.leftImageListener = listener
    }

    /** Set right image listener. */
    fun rightImageListener(listener: ImageClickListener) {
        this.rightImageListener = listener
    }

    /** Set left image drawable. */
    fun setLeftImageDrawable(@DrawableRes drawableRes: Int) {
        this.leftImage.background = ContextCompat.getDrawable(ctx, drawableRes)
        this.leftImage.background.setColorFilter(primaryColor, PorterDuff.Mode.SRC_ATOP)
    }

    /** Set right image drawable. */
    fun setRightImageDrawable(@DrawableRes drawableRes: Int) {
        this.rightImage.background = ContextCompat.getDrawable(ctx, drawableRes)
        this.rightImage.background.setColorFilter(primaryColor, PorterDuff.Mode.SRC_ATOP)
    }

    /** Enable digits within a range. */
    fun enableDigits(range: IntRange = 0..9) {
        range.toMutableList().forEach { digits[it].enableDigit() }
    }

    private fun TextView.enableDigit() {
        isClickable = true
        isFocusable = true
        alpha = 1f
    }

    /** Disable digits within a range. */
    fun disableDigits(range: IntRange = 0..9) {
        range.toMutableList().forEach { digits[it].disableDigit() }
    }

    private fun TextView.disableDigit() {
        isClickable = false
        isFocusable = false
        alpha = 0.3f
    }

    private fun View.changeHighlightColor() {
        (background as RippleDrawable).apply {
            setColor(ColorStateList.valueOf(highlightColor))
        }
    }
}
