 // NDSoftwares

package com.ndsoftwares.sample.utils

import android.annotation.SuppressLint
import androidx.annotation.RestrictTo
import java.text.SimpleDateFormat

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
@SuppressLint("SimpleDateFormat")
fun Long.toFormattedDate(): String =
    SimpleDateFormat("d MMM yyyy").format(this)
