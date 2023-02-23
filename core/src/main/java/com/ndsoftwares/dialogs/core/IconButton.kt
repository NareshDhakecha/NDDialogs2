

@file:Suppress("unused")

package com.ndsoftwares.dialogs.core

import android.graphics.drawable.Drawable
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes

/**
 * Represents an icon acting as a button
 * giving additional possibilities for actions.
 */
class IconButton internal constructor() {

    internal var listener: ClickListener? = null

    @DrawableRes
    internal var drawableRes: Int? = null

    internal var drawable: Drawable? = null

    @ColorRes
    internal var drawableColor: Int? = null

    constructor(@DrawableRes drawableRes: Int) : this() {
        this.drawableRes = drawableRes
    }

    constructor(drawable: Drawable) : this() {
        this.drawable = drawable
    }

    constructor(
        @DrawableRes drawableRes: Int,
        @ColorRes drawableColor: Int
    ) : this() {
        this.drawableRes = drawableRes
        this.drawableColor = drawableColor
    }

    constructor(
        drawable: Drawable,
        @ColorRes drawableColor: Int
    ) : this() {
        this.drawable = drawable
        this.drawableColor = drawableColor
    }

    internal fun listener(listener: ClickListener?) {
        this.listener = listener
    }
}