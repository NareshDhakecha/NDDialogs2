 // NDSoftwares

@file:Suppress("unused")

package com.ndsoftwares.dialogs.core

import android.app.Dialog
import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.annotation.DimenRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.preference.PreferenceFragmentCompat
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel
import com.ndsoftwares.dialogs.core.utils.*


/**
 * This class is the base of all types of sheets.
 * You can implement this class in your own and build your
 * own custom sheet with the already existing features which the base class offers.
 */
abstract class NDDialogFragment : DialogFragment() {

    open val dialogTag = "NDDialogFragment"

    companion object {
        const val DEFAULT_CORNER_RADIUS = 16f
        const val DEFAULT_CORNER_FAMILY = CornerFamily.ROUNDED
    }

    open lateinit var windowContext: Context

    protected var dialogStyle: DialogStyle = DialogStyle.BOTTOM_SHEET
    private var dialogTheme = Theme.BOTTOM_SHEET_DAY
    private var behavior = BottomSheetBehavior.STATE_EXPANDED
    private var peekHeight = 0
    protected var width: Int? = null
    protected var cornerFamily: Int? = null
    protected var cornerRadiusDp: Float? = null
    private var borderStrokeWidthDp: Float? = null
    private var borderStrokeColor: Int? = null

    /** Set sheet style. */
    fun style(style: DialogStyle) {
        this.dialogStyle = style
    }

    /** Override style to switch between sheet & dialog style. */
    override fun setStyle(style: Int, theme: Int) {
        dialogTheme = Theme.inferTheme(requireContext(), dialogStyle)
        super.setStyle(style, dialogTheme.styleRes)
    }

    /** Set the [BottomSheetBehavior] state. */
    fun behavior(@BottomSheetBehavior.State behavior: Int) {
        this.behavior = behavior
    }

    /** Set the peek height. */
    fun peekHeight(peekHeight: Int) {
        this.peekHeight = peekHeight
    }

    /**
     * Set the [CornerFamily].
     * Overrides the style default.
     */
    fun cornerFamily(@CornerFamily cornerFamily: Int) {
        this.cornerFamily = cornerFamily
    }

    /**
     * Set the corner radius in dp.
     * Overrides the style default.
     */
    fun cornerRadius(cornerRadiusInDp: Float) {
        this.cornerRadiusDp = cornerRadiusInDp
    }

    /**
     * Set the corner radius in dp.
     * Overrides the style default.
     */
    fun cornerRadius(@DimenRes cornerRadiusInDpRes: Int) {
        this.cornerRadiusDp = windowContext.resources.getDimension(cornerRadiusInDpRes)
    }

    /** Set the border stroke width */
    fun borderWidth(strokeWidthDp: Float) {
        this.borderStrokeWidthDp = strokeWidthDp
    }

    /** Set the border stroke width */
    fun borderWidth(@DimenRes strokeWidthDpRes: Int) {
        this.borderStrokeWidthDp = windowContext.resources.getDimension(strokeWidthDpRes)
    }

    /**
     * Set the border stroke color around the sheet.
     * If no color is set, the primary color is used.
     */
    fun borderColor(strokeColor: Int) {
        this.borderStrokeColor = strokeColor
    }

    /** Override theme to allow auto switch between day & night design. */
    override fun getTheme(): Int {
        dialogTheme = Theme.inferTheme(requireContext(), dialogStyle)
        return dialogTheme.styleRes
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSheetBehavior(view)
        setupSheetBackground(view)
    }

    private fun setupSheetBehavior(view: View) {

        if (dialogStyle == DialogStyle.DIALOG) {
            // We don't need a behavior for the dialog
            return
        }

        view.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                view.viewTreeObserver.removeOnGlobalLayoutListener(this)
                val dialog = dialog as? BottomSheetDialog? ?: return
                val dialogBehavior = dialog.behavior
                dialogBehavior.state = behavior
                dialogBehavior.peekHeight = peekHeight
                dialogBehavior.addBottomSheetCallback(object :
                    BottomSheetBehavior.BottomSheetCallback() {
                    override fun onSlide(bottomSheet: View, dY: Float) {
                        // TODO: Make button layout stick to the bottom through translationY property.
                    }

                    override fun onStateChanged(bottomSheet: View, newState: Int) {
                        if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                            dismiss()
                        }
                    }
                })
            }
        })
    }

    @CornerFamily
    fun getActualCornerFamily(): Int =
        this.cornerFamily
            ?: getCornerFamily(requireContext())
            ?: DEFAULT_CORNER_FAMILY

    fun getActualCornerRadius(): Float =
        this.cornerRadiusDp?.toDp()
            ?: getCornerRadius(requireContext())
            ?: DEFAULT_CORNER_RADIUS.toDp()

    private fun setupSheetBackground(view: View) {

        // Remove dialog background
        if (dialogStyle == DialogStyle.DIALOG) {
            dialog?.window?.decorView?.setBackgroundColor(Color.TRANSPARENT)
        }

        val cornerFamily = getActualCornerFamily()
        val cornerRadius = getActualCornerRadius()

        val model = ShapeAppearanceModel().toBuilder().apply {
            when (dialogStyle) {
                DialogStyle.BOTTOM_SHEET -> {
                    setTopRightCorner(cornerFamily, cornerRadius)
                    setTopLeftCorner(cornerFamily, cornerRadius)
                }
                else -> setAllCorners(cornerFamily, cornerRadius)
            }
        }.build()

        val shape = MaterialShapeDrawable(model).apply {

            borderStrokeWidthDp?.let { width ->
                setStroke(width.toDp(), borderStrokeColor ?: getPrimaryColor(requireContext()))
                setPadding(width.getDp(), width.getDp(), width.getDp(), width.getDp())
            }

            val backgroundColor = getBottomSheetBackgroundColor(requireContext(),
                dialogTheme.styleRes)
            fillColor = ColorStateList.valueOf(backgroundColor)
        }

        view.background = shape
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return when (dialogStyle) {
            DialogStyle.BOTTOM_SHEET -> BottomSheetDialog(requireContext(), theme)
            else -> Dialog(requireContext(), theme)
        }
    }

    override fun setupDialog(dialog: Dialog, style: Int) {
        when (dialogStyle) {

            DialogStyle.BOTTOM_SHEET -> {

                // If the dialog is an AppCompatDialog, we'll handle it
                val acd = dialog as AppCompatDialog
                when (style) {
                    STYLE_NO_INPUT -> {
                        dialog.getWindow()?.addFlags(
                            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                        )
                        acd.supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
                    }
                    STYLE_NO_FRAME, STYLE_NO_TITLE -> acd.supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
                }
            }

            else -> {
                when (style) {
                    STYLE_NO_INPUT -> {
                        val window = dialog.window
                        window?.addFlags(
                            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                                    or WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                        )
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                    }
                    STYLE_NO_FRAME, STYLE_NO_TITLE -> dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
//        if (sheetStyle == SheetStyle.DIALOG) {
//            dialog?.window?.setLayout(
//                width ?: ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT
//            )
//        } else {
//            width?.let { width ->
//                dialog?.window?.setLayout(width,
//                    ViewGroup.LayoutParams.MATCH_PARENT)
//            }
//        }

        if (dialogStyle == DialogStyle.DIALOG) {
            dialog?.window?.setLayout(
                    width ?: getDialogWidth(),
                    ViewGroup.LayoutParams.WRAP_CONTENT
            )
        } else {
            width?.let { width ->
                dialog?.window?.setLayout(width,
                        ViewGroup.LayoutParams.MATCH_PARENT)
            }
        }
    }

    private fun getDialogWidth(): Int {
        return minOf((Resources.getSystem().displayMetrics.widthPixels * 0.95).toInt(), 1400)
    }

    /** Show the sheet. */
    fun show() {
        windowContext.let { ctx ->
            when (ctx) {
                is FragmentActivity -> show(ctx.supportFragmentManager, dialogTag)
                is AppCompatActivity -> show(ctx.supportFragmentManager, dialogTag)
                is Fragment -> show(ctx.childFragmentManager, dialogTag)
                is PreferenceFragmentCompat -> show(ctx.childFragmentManager, dialogTag)
                else -> throw IllegalStateException("Context has no window attached.")
            }
        }
    }
}