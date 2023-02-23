 // NDSoftwares

@file:Suppress("unused")

package com.ndsoftwares.dialogs.input.type

import android.os.Bundle

/** Listener which returns the new index. */
typealias RadioButtonsInputListener = (index: Int) -> Unit

/**
 * Input of the type RadioButton.
 */
class InputRadioButtons(key: String? = null, func: InputRadioButtons.() -> Unit) : Input(key) {

    init {
        func()
    }

    private var changeListener: RadioButtonsInputListener? = null
    private var resultListener: RadioButtonsInputListener? = null

    internal var radioButtonOptions: MutableList<String>? = null
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

    /** Set the options to be displays as RadioButtons within a group. */
    fun options(options: MutableList<String>) {
        this.radioButtonOptions = options
    }
    
    /** Set a listener which returns the new value when it changed. */
    fun changeListener(listener: RadioButtonsInputListener) {
        this.changeListener = listener
    }
    
    /** Set a listener which returns the final value when the user clicks the positive button. */
    fun resultListener(listener: RadioButtonsInputListener) {
        this.resultListener = listener
    }

    override fun invokeResultListener() =
        resultListener?.invoke(value ?: -1)

    override fun valid(): Boolean = value != -1

    override fun putValue(bundle: Bundle, index: Int) {
        value?.let { bundle.putInt(getKeyOrIndex(index), it) }
    }
}