package com.ndsoftwares.dialogs.core.views

import android.content.Context
import android.util.AttributeSet
import android.widget.EdgeEffect
import androidx.recyclerview.widget.RecyclerView
import com.ndsoftwares.dialogs.core.utils.getPrimaryColor


/** Custom RecyclerView to apply edge effect automatically. */
class NDDialogRecyclerView
@JvmOverloads constructor(
    ctx: Context,
    attrs: AttributeSet? = null
) : RecyclerView(ctx, attrs) {

    init {

        edgeEffectFactory = object : RecyclerView.EdgeEffectFactory() {
            override fun createEdgeEffect(view: RecyclerView, direction: Int): EdgeEffect =
                EdgeEffect(view.context).apply { color = getPrimaryColor(ctx) }
        }
    }
}