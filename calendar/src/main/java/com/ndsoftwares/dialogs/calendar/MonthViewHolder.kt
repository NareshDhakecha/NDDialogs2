

package com.ndsoftwares.dialogs.calendar

import android.view.View
import com.ndsoftwares.calendarview.ui.ViewContainer
import com.ndsoftwares.dialogs.calendar.databinding.NdDialogCalendarHeaderItemBinding

internal class MonthViewHolder(view: View) : ViewContainer(view) {
    private val binding = NdDialogCalendarHeaderItemBinding.bind(view)
    val legend = binding.legend
}