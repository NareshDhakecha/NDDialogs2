

@file:Suppress("unused")

package com.ndsoftwares.dialogs.core

/**
 * A class that represent an aspect ratio.
 */
data class Ratio(val width: Int, val height: Int) {
    // Dimension ratio for ConstraintLayout
    val dimensionRatio: String = "$width:$height"
}