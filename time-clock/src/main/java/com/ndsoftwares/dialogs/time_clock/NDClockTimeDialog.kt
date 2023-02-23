 // NDSoftwares

@file:Suppress("unused")

package com.ndsoftwares.dialogs.time_clock

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.ndsoftwares.dialogs.core.NDDialog
import com.ndsoftwares.dialogs.time_clock.databinding.NdDialogTimeClockBinding
import java.util.*

/** Listener which returns the selected clock time in milliseconds. */
typealias ClockTimeListener = (milliseconds: Long, hours: Int, minutes: Int) -> Unit

/**
 * The [NDClockTimeDialog] lets you quickly pick a clock time.
 */
class NDClockTimeDialog : NDDialog() {

    override val dialogTag = "ClockTimeSheet"

    private lateinit var binding: NdDialogTimeClockBinding
    private lateinit var selector: ClockTimeSelector

    private var listener: ClockTimeListener? = null
    private var currentTimeInMillis: Long = Calendar.getInstance().timeInMillis
    private var format24Hours: Boolean = true

    /** Set 24-hours format or 12-hours format. Default is 24-hours format. */
    fun format24Hours(format24Hours: Boolean) {
        this.format24Hours = format24Hours
    }

    /** Set current time in milliseconds. */
    fun currentTime(currentTimeInMillis: Long) {
        this.currentTimeInMillis = currentTimeInMillis
    }

    /**
     * Set the [ClockTimeListener].
     *
     * @param listener Listener that is invoked with the clock time when the positive button is clicked.
     */
    fun onPositive(listener: ClockTimeListener) {
        this.listener = listener
    }

    /**
     * Set the text of the positive button and set the [ClockTimeListener].
     *
     * @param positiveRes The String resource id for the positive button.
     * @param listener Listener that is invoked with the clock time when the positive button is clicked.
     */
    fun onPositive(@StringRes positiveRes: Int, listener: ClockTimeListener? = null) {
        this.positiveText = windowContext.getString(positiveRes)
        this.listener = listener
    }

    /**
     *  Set the text of the positive button and set the [ClockTimeListener].
     *
     * @param positiveText The text for the positive button.
     * @param listener Listener that is invoked with the clock time when the positive button is clicked.
     */
    fun onPositive(positiveText: String, listener: ClockTimeListener? = null) {
        this.positiveText = positiveText
        this.listener = listener
    }

    /**
     * Set the text and icon of the positive button and set the [ClockTimeListener].
     *
     * @param positiveRes The String resource id for the positive button.
     * @param listener Listener that is invoked with the clock time when the positive button is clicked.
     */
    fun onPositive(
        @StringRes positiveRes: Int,
        @DrawableRes drawableRes: Int,
        listener: ClockTimeListener? = null
    ) {
        this.positiveText = windowContext.getString(positiveRes)
        this.positiveButtonDrawableRes = drawableRes
        this.listener = listener
    }

    /**
     *  Set the text and icon of the positive button and set the [ClockTimeListener].
     *
     * @param positiveText The text for the positive button.
     * @param listener Listener that is invoked with the clock time when the positive button is clicked.
     */
    fun onPositive(
        positiveText: String,
        @DrawableRes drawableRes: Int,
        listener: ClockTimeListener? = null
    ) {
        this.positiveText = positiveText
        this.positiveButtonDrawableRes = drawableRes
        this.listener = listener
    }

    override fun onCreateLayoutView(): View =
        NdDialogTimeClockBinding.inflate(LayoutInflater.from(activity))
            .also { binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setButtonPositiveListener(::save)
        selector = ClockTimeSelector(
            ctx = requireContext(),
            bindingSelector = binding,
            is24HoursView = format24Hours
        )
        selector.setTime(currentTimeInMillis)
    }

    private fun save() {
        val time = selector.getTime()
        listener?.invoke(time.first, time.second, time.third)
        dismiss()
    }

    /** Build [NDClockTimeDialog] and show it later. */
    fun build(ctx: Context, width: Int? = null, func: NDClockTimeDialog.() -> Unit): NDClockTimeDialog {
        this.windowContext = ctx
        this.width = width
        this.func()
        return this
    }

    /** Build and show [NDClockTimeDialog] directly. */
    fun show(ctx: Context, width: Int? = null, func: NDClockTimeDialog.() -> Unit): NDClockTimeDialog {
        this.windowContext = ctx
        this.width = width
        this.func()
        this.show()
        return this
    }
}