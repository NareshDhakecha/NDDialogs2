 // NDSoftwares

package com.ndsoftwares.dialogs.core.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.TypedValue
import androidx.annotation.*
import androidx.core.content.ContextCompat
import com.ndsoftwares.dialogs.core.R


/** Get a color. */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
@ColorInt
fun colorOf(ctx: Context, @ColorRes colorRes: Int): Int =
    ContextCompat.getColor(ctx, colorRes)

/** Get a color by a theme attribute. */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
@ColorInt
fun colorOfAttr(ctx: Context, @AttrRes attr: Int): Int {
    val a = ctx.theme.obtainStyledAttributes(intArrayOf(attr))
    return a.getColor(0, 0)
}

/** Get a color by a theme attribute. */
@SuppressLint("ResourceType")
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
@ColorInt
fun colorOfAttrs(ctx: Context, @AttrRes vararg attrs: Int): Int {
    val a = ctx.theme.obtainStyledAttributes(attrs.toList().toIntArray())
    attrs.forEachIndexed { index, _ ->
        val color = a.getColor(index, 0)
        if (color != 0) return color
    }
    return 0
}

/** Get a color by a theme attribute of a specific style. */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
@ColorInt
internal fun colorOfAttrOfTheme(ctx: Context?, @AttrRes resId: Int, @StyleRes stylesId: Int): Int {
    val typedValue = TypedValue()
    val theme = ctx?.resources?.newTheme()
    theme?.applyStyle(stylesId, true)
    theme?.resolveAttribute(resId, typedValue, true)
    return typedValue.data
}

/**
 * From [https://github.com/afollestad/material-dialogs/blob/master/core/src/main/java/com/afollestad/materialdialogs/utils/MDUtil.kt]
 */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
fun Int.isColorDark(threshold: Double = 0.5): Boolean {
    if (this == Color.TRANSPARENT) {
        return false
    }
    val darkness =
        1 - (0.299 * Color.red(this) + 0.587 * Color.green(this) + 0.114 * Color.blue(this)) / 255
    return darkness >= threshold
}

@ColorInt
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
internal fun Int.withAlpha(@FloatRange(from = 0.0, to = 1.0) alphaFactor: Float): Int {
    return Color.argb(
        alphaFactor.times(255).toInt(),
        Color.red(this),
        Color.green(this),
        Color.blue(this)
    )
}

/** Returns the same int value if  */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
fun Int.takeUnlessNotResolved(): Int? {
    return if (this != 0) this else null
}

/** Returns the same int value if  */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
fun Float.takeUnlessNotResolved(): Float? {
    return if (this != 0f) this else null
}

/** Get color for icons. */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
@ColorInt
fun getIconColor(ctx: Context): Int = colorOfAttrs(
    ctx,
    // 1. Try to resolve custom attr color.
    R.attr.ndDialogIconsColor,
    // 2. Get default theme attr used for icons.
    com.google.android.material.R.attr.colorOnSurface
)

/** Get primary color. */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
@ColorInt
fun getPrimaryColor(ctx: Context): Int = colorOfAttrs(
    ctx,
    // 1. Try to resolve custom primary attr color.
    R.attr.ndDialogPrimaryColor,
    // 2. Resolve default attr for primary color.
    com.google.android.material.R.attr.colorPrimary
)

/** Get highlight color. */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
@ColorInt
fun getHighlightColor(ctx: Context): Int {
    return colorOfAttrs(
        ctx,
        // 1. Try to resolve custom highlight color attr.
        R.attr.ndDialogHighlightColor
    ).takeUnlessNotResolved() ?:
    /* Create custom highlight color based on primary color. */
    getPrimaryColor(ctx).withAlpha(HIGHLIGHT_ALPHA)
}

/** Get the highlight color of any color. */
fun getHighlightOfColor(@ColorInt color: Int): Int
        = color.withAlpha(HIGHLIGHT_ALPHA)

/** Get text color. */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
@ColorInt
fun getTextColor(ctx: Context): Int = colorOfAttrs(
    ctx,
    // 1. Try to resolve primary text color attr.
    R.attr.ndDialogContentColor,
    // 2. Resolve default primary text color attr.
    android.R.attr.textColorPrimary
)

/** Get text inverse color. */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
@ColorInt
fun getTextInverseColor(ctx: Context): Int = colorOfAttrs(
    ctx,
    // 1. Try to resolve primary text inverse color attr.
    R.attr.ndDialogContentInverseColor,
    // 2. Resolve default primary text inverse color attr.
    android.R.attr.textColorPrimaryInverse
)

/** Get background color. */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
@ColorInt
fun getBottomSheetBackgroundColor(ctx: Context, @StyleRes styleRes: Int): Int {
    val attr = R.attr.ndDialogBackgroundColor
    return colorOfAttrs(ctx, attr).takeUnlessNotResolved()
        ?: colorOfAttrOfTheme(ctx, attr, styleRes)
}

/** Get visibility status of the toolbar. */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
internal fun isDisplayToolbar(ctx: Context, defaultValue: Boolean): Boolean {
    val a = ctx.theme.obtainStyledAttributes(intArrayOf(R.attr.ndDialogDisplayToolbar))
    return a.getBoolean(0, defaultValue)
}

/** Get visibility status of the close button. */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
internal fun isDisplayCloseButton(ctx: Context, defaultValue: Boolean): Boolean {
    val a = ctx.theme.obtainStyledAttributes(intArrayOf(R.attr.ndDialogDisplayCloseButton))
    return a.getBoolean(0, defaultValue)
}

/** Get visibility status of the handle view. */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
internal fun isDisplayHandle(ctx: Context, defaultValue: Boolean): Boolean {
    val a = ctx.theme.obtainStyledAttributes(intArrayOf(R.attr.ndDialogDisplayHandle))
    return a.getBoolean(0, defaultValue)
}

/** Get corner radius. */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
fun getCornerRadius(ctx: Context): Float? {
    val a = ctx.theme.obtainStyledAttributes(intArrayOf(R.attr.ndDialogCornerRadius))
    return a.getDimension(0, 0f).takeUnlessNotResolved()
}

/** Get corner family. */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
fun getCornerFamily(ctx: Context): Int? {
    val a = ctx.theme.obtainStyledAttributes(intArrayOf(R.attr.ndDialogCornerFamily))
    return a.getInt(0, 0).takeUnlessNotResolved()
}

/** Get corner family. */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
fun getCornerFamily(ctx: Context, @AttrRes attr: Int): Int? {
    val a = ctx.theme.obtainStyledAttributes(intArrayOf(attr))
    return a.getInt(0, 0).takeUnlessNotResolved()
}


/** Get an Integer value by theme attributes. */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
fun intOfAttrs(ctx: Context, @AttrRes vararg attrs: Int): Int? {
    val a = ctx.theme.obtainStyledAttributes(attrs.toList().toIntArray())
    attrs.forEachIndexed { i, _ ->
        val attrValue = a.getInt(i, -42)
        if (attrValue != -42) return attrValue
    }
    return null
}

/** Get an Integer value by theme attributes. */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
fun booleanOfAttrs(ctx: Context, @AttrRes vararg attrs: Int): Boolean {
    val a = ctx.theme.obtainStyledAttributes(attrs.toList().toIntArray())
    attrs.forEachIndexed { i, _ ->
        val attrValue = a.getBoolean(i, false)
        if (attrValue) return attrValue
    }
    return false
}

/** Get an Integer value by theme attributes. */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
fun dimensionOfAttrs(ctx: Context, @AttrRes vararg attrs: Int): Float? {
    val a = ctx.theme.obtainStyledAttributes(attrs.toList().toIntArray())
    attrs.forEachIndexed { i, _ ->
        val attrValue = a.getDimension(i, -1f)
        if (attrValue != -1f) return attrValue
    }
    return null
}