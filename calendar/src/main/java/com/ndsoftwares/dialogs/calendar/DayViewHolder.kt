

package com.ndsoftwares.dialogs.calendar

import android.view.View
import com.ndsoftwares.calendarview.model.CalendarDay
import com.ndsoftwares.calendarview.ui.ViewContainer
import com.ndsoftwares.dialogs.calendar.databinding.NdDialogCalendarDayItemBinding

internal class DayViewHolder(view: View, listener: (CalendarDay) -> Unit) : ViewContainer(view) {
    val binding = NdDialogCalendarDayItemBinding.bind(view)
    lateinit var day: CalendarDay

    init {
        view.setOnClickListener { listener.invoke(day) }
    }
}