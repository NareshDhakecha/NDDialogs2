 // NDSoftwares

package com.ndsoftwares.dialogs.core

import androidx.annotation.AttrRes

/**
 * Available button styles.
 */
enum class ButtonStyle(@AttrRes val styleRes: Int) {

    /**
     * Uses the style Widget.MaterialComponents.Button.TextButton.
     */
    TEXT(com.google.android.material.R.attr.materialButtonOutlinedStyle),

    /**
     * Uses the style Widget.MaterialComponents.Button.OutlinedButton.
     */
    OUTLINED(com.google.android.material.R.attr.materialButtonOutlinedStyle),

    /**
     * Uses the style Widget.MaterialComponents.Button.
     */
    NORMAL(com.google.android.material.R.attr.materialButtonStyle),
}