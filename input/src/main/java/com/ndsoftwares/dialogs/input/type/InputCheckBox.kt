 // NDSoftwares

@file:Suppress("unused")

package com.ndsoftwares.dialogs.input.type

import android.os.Bundle
import androidx.annotation.StringRes

/** Listener which returns the new value. */
typealias CheckBoxInputListener = (value: Boolean) -> Unit

/**
 * Input of the type Checkbox.
 */
class InputCheckBox(key: String? = null, func: InputCheckBox.() -> Unit) : Input(key) {

    init {
        func()
    }

    private var changeListener: CheckBoxInputListener? = null
    private var resultListener: CheckBoxInputListener? = null

    internal var text: String? = null
        private set

    internal var textRes: Int? = null
        private set

    var value: Boolean = false
        internal set(value) {
            changeListener?.invoke(value)
            field = value
        }

    /** Set the default value. */
    fun defaultValue(defaultValue: Boolean) {
        this.value = defaultValue
    }

    /** Set the text of the CheckBox. */
    fun text(@StringRes textRes: Int) {
        this.textRes = textRes
    }

    /** Set the text of the CheckBox. */
    fun text(text: String) {
        this.text = text
    }

    /** Set a listener which returns the new value when it changed. */
    fun changeListener(listener: CheckBoxInputListener) {
        this.changeListener = listener
    }

    /** Set a listener which returns the final value when the user clicks the positive button. */
    fun resultListener(listener: CheckBoxInputListener) {
        this.resultListener = listener
    }

    override fun invokeResultListener() =
        resultListener?.invoke(value ?: false)

    override fun valid(): Boolean = value ?: false


    override fun putValue(bundle: Bundle, index: Int) {
        bundle.putBoolean(getKeyOrIndex(index), value ?: false)
    }

}