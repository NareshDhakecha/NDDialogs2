 // NDSoftwares

@file:Suppress("unused")

package com.ndsoftwares.dialogs.time

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.ndsoftwares.dialogs.core.NDDialog
import com.ndsoftwares.dialogs.time.databinding.NdDialogTimeBinding

/** Listener which returns the selected duration time in milliseconds. */
typealias DurationTimeListener = (timeInSec: Long) -> Unit

/**
 * The [NDTimeDialog] lets you pick a duration time in a specific format.
 */
class NDTimeDialog : NDDialog() {

    override val dialogTag = "TimeSheet"

    private lateinit var binding: NdDialogTimeBinding
    private lateinit var selector: TimeSelector

    private var listener: DurationTimeListener? = null
    private var format: TimeFormat = TimeFormat.MM_SS
    private var minTime: Long = Long.MIN_VALUE
    private var maxTime: Long = Long.MAX_VALUE
    private var currentTime: Long? = null
    private var saveAllowed = false

    /** Set the time format. */
    fun format(format: TimeFormat) {
        this.format = format
    }

    /** Set current time in seconds. */
    fun currentTime(currentTimeInSec: Long) {
        this.currentTime = currentTimeInSec
    }

    /** Set min time in seconds. */
    fun minTime(minTimeInSec: Long) {
        this.minTime = minTimeInSec
    }

    /** Set max time in seconds. */
    fun maxTime(maxTimeInSec: Long) {
        this.maxTime = maxTimeInSec
    }

    /**
     * Set the [DurationTimeListener].
     *
     * @param listener Listener that is invoked with the duration time when the positive button is clicked.
     */
    fun onPositive(listener: DurationTimeListener) {
        this.listener = listener
    }

    /**
     * Set the text of the positive button and set the [DurationTimeListener].
     *
     * @param positiveRes The String resource id for the positive button.
     * @param listener Listener that is invoked with the duration time when the positive button is clicked.
     */
    fun onPositive(@StringRes positiveRes: Int, listener: DurationTimeListener? = null) {
        this.positiveText = windowContext.getString(positiveRes)
        this.listener = listener
    }

    /**
     *  Set the text of the positive button and set the [DurationTimeListener].
     *
     * @param positiveText The text for the positive button.
     * @param listener Listener that is invoked with the duration time when the positive button is clicked.
     */
    fun onPositive(positiveText: String, listener: DurationTimeListener? = null) {
        this.positiveText = positiveText
        this.listener = listener
    }

    /**
     * Set the text and icon of the positive button and set the [DurationTimeListener].
     *
     * @param positiveRes The String resource id for the positive button.
     * @param drawableRes The drawable resource for the button icon.
     * @param listener Listener that is invoked with the duration time when the positive button is clicked.
     */
    fun onPositive(
        @StringRes positiveRes: Int,
        @DrawableRes drawableRes: Int,
        listener: DurationTimeListener? = null
    ) {
        this.positiveText = windowContext.getString(positiveRes)
        this.positiveButtonDrawableRes = drawableRes
        this.listener = listener
    }

    /**
     *  Set the text and icon of the positive button and set the [DurationTimeListener].
     *
     * @param positiveText The text for the positive button.
     * @param drawableRes The drawable resource for the button icon.
     * @param listener Listener that is invoked with the duration time when the positive button is clicked.
     */
    fun onPositive(
        positiveText: String,
        @DrawableRes drawableRes: Int,
        listener: DurationTimeListener? = null
    ) {
        this.positiveText = positiveText
        this.positiveButtonDrawableRes = drawableRes
        this.listener = listener
    }

    private val validationListener: TimeValidationListener = { valid ->
        saveAllowed = valid
        displayButtonPositive(saveAllowed)
    }

    private fun save() {
        listener?.invoke(selector.getTimeInSeconds())
        dismiss()
    }

    override fun onCreateLayoutView(): View =
        NdDialogTimeBinding.inflate(LayoutInflater.from(activity)).also { binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setButtonPositiveListener(::save)
        selector = TimeSelector(
            requireContext(),
            binding = binding,
            format = format,
            minTime = minTime,
            maxTime = maxTime,
            validationListener = validationListener
        )
        currentTime?.let { selector.setTime(it) }
    }

    /** Build [NDTimeDialog] and show it later. */
    fun build(ctx: Context, width: Int? = null, func: NDTimeDialog.() -> Unit): NDTimeDialog {
        this.windowContext = ctx
        this.width = width
        this.func()
        return this
    }

    /** Build and show [NDTimeDialog] directly. */
    fun show(ctx: Context, width: Int? = null, func: NDTimeDialog.() -> Unit): NDTimeDialog {
        this.windowContext = ctx
        this.width = width
        this.func()
        this.show()
        return this
    }
}