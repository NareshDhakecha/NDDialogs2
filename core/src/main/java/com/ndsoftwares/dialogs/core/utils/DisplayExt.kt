 // NDSoftwares

package com.ndsoftwares.dialogs.core.utils

import android.content.res.Resources
import androidx.annotation.RestrictTo

/** Converts px as Int to dp as Float. */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
fun Int.getDp(): Float = (this * Resources.getSystem().displayMetrics.density)

/** Converts px as Float to dp as Int. */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
fun Float.getDp(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()

/** Converts px as Float to dp as Float. */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
fun Float.toDp(): Float = (this * Resources.getSystem().displayMetrics.density)

/** Converts px as Int to dp as Int. */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
fun Int.toDp(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()

