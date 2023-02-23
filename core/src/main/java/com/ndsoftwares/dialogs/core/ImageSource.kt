

@file:Suppress("unused")

package com.ndsoftwares.dialogs.core

import androidx.appcompat.widget.AppCompatImageView

typealias ImageViewBuilder = AppCompatImageView.() -> Unit

/**
 * A class that holds general image view settings.
 */
abstract class ImageSource {

    internal var imageViewBuilder: ImageViewBuilder? = null
    internal var ratio: Ratio? = null

    /** Setup ImageView. */
    fun setupImageView(imageViewBuilder: ImageViewBuilder) {
        this.imageViewBuilder = imageViewBuilder
    }

    /** Set the ratio of the container view that contains the ImageView. */
    fun ratio(ratio: Ratio) {
        this.ratio = ratio
    }
}