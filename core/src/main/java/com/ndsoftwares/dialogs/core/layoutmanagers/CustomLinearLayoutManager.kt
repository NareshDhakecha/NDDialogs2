 // NDSoftwares

package com.ndsoftwares.dialogs.core.layoutmanagers

import android.content.Context
import androidx.annotation.RestrictTo
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/** Custom implementation of [LinearLayoutManager] with more control. */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
class CustomLinearLayoutManager(
    context: Context,
    private val scrollable: Boolean = false,
    @RecyclerView.Orientation orientation: Int = RecyclerView.VERTICAL,
    reverseLayout: Boolean = false
) : LinearLayoutManager(context, orientation, reverseLayout) {

    override fun canScrollVertically(): Boolean =
        scrollable && super.canScrollVertically()

    override fun canScrollHorizontally(): Boolean =
        scrollable && super.canScrollHorizontally()

}