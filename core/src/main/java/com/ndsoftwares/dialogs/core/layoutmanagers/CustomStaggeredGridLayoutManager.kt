 // NDSoftwares

package com.ndsoftwares.dialogs.core.layoutmanagers

import androidx.annotation.RestrictTo
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

/** Custom implementation of [StaggeredGridLayoutManager] with more control. */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
class CustomStaggeredGridLayoutManager(
    columns: Int,
    @RecyclerView.Orientation orientation: Int = RecyclerView.VERTICAL,
    private val scrollable: Boolean = true
) : StaggeredGridLayoutManager(columns, orientation) {

    override fun canScrollVertically(): Boolean =
        scrollable && super.canScrollVertically()

    override fun canScrollHorizontally(): Boolean =
        scrollable && super.canScrollHorizontally()
}