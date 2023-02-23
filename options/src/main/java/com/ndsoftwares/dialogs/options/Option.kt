 // NDSoftwares

@file:Suppress("unused")

package com.ndsoftwares.dialogs.options

import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

/**
 * An option is represented with at least a text.
 * A drawable is optional but makes it easier to understand to the user.
 */
class Option internal constructor() {

    internal var drawable: Drawable? = null
        private set

    @DrawableRes
    internal var drawableRes: Int? = null
        private set

    internal var text: String? = null
        private set

    @StringRes
    internal var textRes: Int? = null
        private set

    internal var selected: Boolean = false
        private set

    internal var disabled: Boolean = false
        private set

    constructor(@StringRes textRes: Int) : this() {
        this.textRes = textRes
    }

    constructor(text: String) : this() {
        this.text = text
    }

    constructor(@DrawableRes drawableRes: Int, text: String) : this() {
        this.drawableRes = drawableRes
        this.text = text
    }

    constructor(drawable: Drawable?, @StringRes textRes: Int) : this() {
        this.drawable = drawable
        this.textRes = textRes
    }

    constructor(drawable: Drawable?, text: String) : this() {
        this.drawable = drawable
        this.text = text
    }

    constructor(@DrawableRes drawableRes: Int, @StringRes textRes: Int) : this() {
        this.drawableRes = drawableRes
        this.textRes = textRes
    }

    /** Declare the option as already selected. */
    fun select(): Option {
        this.selected = true
        return this
    }

    /** Declare the option as already selected. */
    fun selected(selected: Boolean): Option {
        this.selected = selected
        return this
    }

    /** Declare the option as disabled. Disabled options are not selectable anymore. */
    fun disable(): Option {
        this.disabled = true
        return this
    }

    /** Declare the option as disabled. Disabled options are not selectable anymore. */
    fun disabled(disabled: Boolean): Option {
        this.disabled = disabled
        return this
    }
}