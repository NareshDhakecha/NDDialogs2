 // NDSoftwares

@file:Suppress("unused")

package com.ndsoftwares.dialogs.info

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.ndsoftwares.dialogs.core.PositiveListener
import com.ndsoftwares.dialogs.core.NDDialog
import com.ndsoftwares.dialogs.core.utils.getIconColor
import com.ndsoftwares.dialogs.info.databinding.NdDialogInfoBinding


 /**
 * The [NDInfoDialog] lets you display an information or warning.
 */
class NDInfoDialog : NDDialog() {

    override val dialogTag = "InfoSheet"

    private lateinit var binding: NdDialogInfoBinding

    private var contentText: String? = null
    private var displayButtons = true

    private var drawable: Drawable? = null

    @DrawableRes
    private var drawableRes: Int? = null

    @ColorInt
    private var drawableColor: Int? = null

    /**
     * Set the content of the sheet.
     *
     * @param content The text for the content.
     */
    fun content(content: String) {
        this.contentText = content
    }

    /**
     * Set the content of the sheet.
     *
     * @param contentRes The String resource id for the content.
     */
    fun content(@StringRes contentRes: Int) {
        this.contentText = windowContext.getString(contentRes)
    }

    /**
     * Set the content of the sheet.
     *
     * @param contentRes Resource id for the format string
     * @param formatArgs The format arguments that will be used for
     *                   substitution.
     */
    fun content(@StringRes contentRes: Int, vararg formatArgs: Any?) {
        this.contentText = windowContext.getString(contentRes, *formatArgs)
    }

    /** Set a drawable left of the text. */
    fun drawable(@DrawableRes drawableRes: Int) {
        this.drawableRes = drawableRes
        this.drawable = null
    }

    /** Set a drawable left of the text. */
    fun drawable(drawable: Drawable) {
        this.drawable = drawable
        this.drawableRes = null
    }

    /** Set the color of the drawable. */
    fun drawableColor(@ColorRes colorRes: Int) {
        this.drawableColor = ContextCompat.getColor(windowContext, colorRes)
    }

    /**
     * Set a listener.
     *
     * @param positiveListener Listener that is invoked when the positive button is clicked.
     */
    fun onPositive(positiveListener: PositiveListener) {
        this.positiveListener = positiveListener
    }

    /**
     * Set the text of the positive button and optionally a listener.
     *
     * @param positiveText The text for the positive button.
     * @param positiveListener Listener that is invoked when the positive button is clicked.
     */
    fun onPositive(positiveText: String, positiveListener: PositiveListener? = null) {
        this.positiveText = positiveText
        this.positiveListener = positiveListener
    }

    /**
     * Set the text of the positive button and optionally a listener.
     *
     * @param positiveRes The String resource id for the positive button.
     * @param positiveListener Listener that is invoked when the positive button is clicked.
     */
    fun onPositive(@StringRes positiveRes: Int, positiveListener: PositiveListener? = null) {
        this.positiveText = windowContext.getString(positiveRes)
        this.positiveListener = positiveListener
    }

    /**
     * Set the text and icon of the positive button and optionally a listener.
     *
     * @param positiveText The text for the positive button.
     * @param drawableRes The drawable resource for the button icon.
     * @param positiveListener Listener that is invoked when the positive button is clicked.
     */
    fun onPositive(
        positiveText: String,
        @DrawableRes drawableRes: Int,
        positiveListener: PositiveListener? = null,
    ) {
        this.positiveText = positiveText
        this.positiveButtonDrawableRes = drawableRes
        this.positiveListener = positiveListener
    }

    /**
     * Set the text and icon of the positive button and optionally a listener.
     *
     * @param positiveRes The String resource id for the positive button.
     * @param drawableRes The drawable resource for the button icon.
     * @param positiveListener Listener that is invoked when the positive button is clicked.
     */
    fun onPositive(
        @StringRes positiveRes: Int,
        @DrawableRes drawableRes: Int,
        positiveListener: PositiveListener? = null,
    ) {
        this.positiveText = windowContext.getString(positiveRes)
        this.positiveButtonDrawableRes = drawableRes
        this.positiveListener = positiveListener
    }

    /** Display buttons and require a positive button click. */
    fun displayButtons(displayButtons: Boolean = true) {
        this.displayButtons = displayButtons
    }

    override fun onCreateLayoutView(): View =
        NdDialogInfoBinding.inflate(LayoutInflater.from(activity)).also { binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        displayButtonsView(displayButtons)
        with(binding) {
            contentText?.let { content.text = it }
            if (drawableRes != null || drawable != null) {
                val infoIconDrawable = drawable ?: drawableRes?.let {
                    ContextCompat.getDrawable(
                        requireContext(),
                        it)
                }
                icon.setImageDrawable(infoIconDrawable)
                icon.setColorFilter(drawableColor ?: getIconColor(requireContext()))
                icon.visibility = View.VISIBLE
            }
        }
    }

    /** Build [NDInfoDialog] and show it later. */
    fun build(ctx: Context, width: Int? = null, func: NDInfoDialog.() -> Unit): NDInfoDialog {
        this.windowContext = ctx
        this.width = width
        this.func()
        return this
    }

    /** Build and show [NDInfoDialog] directly. */
    fun show(ctx: Context, width: Int? = null, func: NDInfoDialog.() -> Unit): NDInfoDialog {
        this.windowContext = ctx
        this.width = width
        this.func()
        this.show()
        return this
    }
}