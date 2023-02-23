 // NDSoftwares

package com.ndsoftwares.dialogs.core.layoutmanagers

import android.content.Context
import androidx.annotation.RestrictTo
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

/** Custom implementation of [GridLayoutManager] with more control. */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
class CustomGridLayoutManager(
    ctx: Context,
    columns: Int,
    private val scrollable: Boolean,
    @RecyclerView.Orientation orientation: Int = RecyclerView.VERTICAL
) : GridLayoutManager(ctx, columns, orientation, false) {

    override fun canScrollHorizontally(): Boolean =
        scrollable && super.canScrollHorizontally()

    override fun canScrollVertically(): Boolean =
        scrollable && super.canScrollVertically()
}