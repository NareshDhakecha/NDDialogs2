 // NDSoftwares

package com.ndsoftwares.dialogs.core.utils

import android.widget.TextView
import androidx.annotation.RestrictTo

/**
 * Sets the width of the TextView by length of ems.
 */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
fun TextView.widthByLength(length: Int) {
    maxEms = length
    minEms = length
}