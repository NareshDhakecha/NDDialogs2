 // NDSoftwares

@file:Suppress("unused")

package com.ndsoftwares.dialogs.input.type

import android.os.Bundle
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

/**
 * The base class of any input. Every input can have a label and a drawable.
 * It can be optional or required.
 */
abstract class Input(private val key: String? = null) {

    internal var required: Boolean = false

    internal var label: String? = null
        private set

    internal var labelRes: Int? = null
        private set

    internal var drawableRes: Int? = null
        private set

    /** Require a value before the user can click the positive button. */
    fun required(required: Boolean = true) {
        this.required = required
    }

    /** Set a drawable. */
    fun drawable(@DrawableRes drawableRes: Int) {
        this.drawableRes = drawableRes
    }

    /** Set the label text. */
    fun label(@StringRes labelRes: Int) {
        this.labelRes = labelRes
    }

    /** Set the label text. */
    fun label(label: String) {
        this.label = label
    }

    /** Check if the input value is valid. */
    abstract fun valid(): Boolean

    /** Invoke the result listener which returns the input value. */
    abstract fun invokeResultListener(): Unit?

    /** Save the input value into the bundle. Takes the index as an key, if there's no unique input key available. */
    abstract fun putValue(bundle: Bundle, index: Int)

    internal fun getKeyOrIndex(index: Int): String =
        if (key.isNullOrEmpty()) index.toString() else key
}