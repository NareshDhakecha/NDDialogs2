 // NDSoftwares

@file:Suppress("unused")

package com.ndsoftwares.dialogs.input.type

import android.os.Bundle
import androidx.annotation.StringRes

/** Listener which returns the new index. */
typealias InputSpinnerListener = (index: Int) -> Unit

/**
 * Input of the type Spinner.
 */
class InputSpinner(key: String? = null, func: InputSpinner.() -> Unit) : Input(key) {

    init {
        func()
    }

    private var changeListener: InputSpinnerListener? = null
    private var resultListener: InputSpinnerListener? = null

    internal var spinnerOptions: MutableList<String>? = null
        private set

    internal var noSelectionText: String? = null
        private set

    internal var textRes: Int? = null
        private set

    var value: Int? = null
        internal set(value) {
            value?.let { changeListener?.invoke(it) }
            field = value
        }

    /** Set the by default selected index. */
    fun selected(selectedIndex: Int) {
        this.value = selectedIndex
    }

    /** Set the options to tbe displays within the Spinner. */
    fun options(options: MutableList<String>) {
        this.spinnerOptions = options
    }

    /** Set the text when no item is selected. */
    fun noSelectionText(@StringRes textRes: Int) {
        this.textRes = textRes
    }

    /** Set the text when no item is selected. */
    fun noSelectionText(text: String) {
        this.noSelectionText = text
    }

    /** Set a listener which returns the new value when it changed. */
    fun changeListener(listener: InputSpinnerListener) {
        this.changeListener = listener
    }

    /** Set a listener which returns the final value when the user clicks the positive button. */
    fun resultListener(listener: InputSpinnerListener) {
        this.resultListener = listener
    }

    override fun invokeResultListener() =
        value?.let { resultListener?.invoke(it) }

    override fun valid(): Boolean = value != -1

    override fun putValue(bundle: Bundle, index: Int) {
        value?.let { bundle.putInt(getKeyOrIndex(index), it) }
    }
}