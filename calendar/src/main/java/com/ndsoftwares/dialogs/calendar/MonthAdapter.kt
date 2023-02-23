 // NDSoftwares

package com.ndsoftwares.dialogs.calendar

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ndsoftwares.dialogs.calendar.databinding.NdDialogCalendarMonthItemBinding
import com.ndsoftwares.dialogs.core.utils.getPrimaryColor
import com.ndsoftwares.dialogs.core.utils.getTextColor
import java.time.Month
import java.time.YearMonth
import java.time.format.DateTimeFormatter
 import com.google.android.material.R

/** Listener that is invoked if a month is selected. */
internal typealias MonthSelectedListener = (Month) -> Unit

internal class MonthAdapter(
    private val ctx: Context,
    private val disablePast: Boolean = false,
    private val disableFuture: Boolean = false,
    private var itemHeight: Int? = null,
    private val listener: MonthSelectedListener
) : RecyclerView.Adapter<MonthAdapter.MonthItem>() {

    private val calendarMonths = Month.values().mapTo(mutableListOf(), { it })

    private val formatter = DateTimeFormatter.ofPattern("MMMM")
    private var selectedMonth = YearMonth.now().month
    private var currentYearMonth = YearMonth.now()
    private var currentSelectedYear = YearMonth.now()

    private val primaryColor = getPrimaryColor(ctx)
    private val textColor = getTextColor(ctx)
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonthItem =
        MonthItem(
                NdDialogCalendarMonthItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: MonthItem, i: Int) {
        val month = calendarMonths[i]
        itemHeight?.let { holder.binding.root.layoutParams.apply { height = it } }
        holder.binding.month.text = formatter.format(month)
        holder.binding.handleState(month)
        holder.binding.root.setOnClickListener {
            updateSelectedMonth(month)
            listener.invoke(month)
        }
    }

    private fun NdDialogCalendarMonthItemBinding.handleState(monthAtIndex: Month) = when {
        disablePast && currentSelectedYear.year == currentYearMonth.year && monthAtIndex.value < currentYearMonth.month.value -> {
            month.isSelected = false
            month.setTextAppearance(ctx, com.google.android.material.R.style.TextAppearance_MaterialComponents_Subtitle2)
            month.setTextColor(textColor)
            month.alpha = 0.2f
        }
        disableFuture && currentSelectedYear.year == currentYearMonth.year && monthAtIndex.value > currentYearMonth.month.value -> {
            month.isSelected = false
            month.setTextAppearance(ctx, R.style.TextAppearance_MaterialComponents_Subtitle2)
            month.setTextColor(textColor)
            month.alpha = 0.2f
        }
        currentYearMonth.month == monthAtIndex && selectedMonth == monthAtIndex -> {
            month.isSelected = true
            month.setTextAppearance(ctx, R.style.TextAppearance_MaterialComponents_Subtitle2)
            month.setTextColor(primaryColor)
            month.alpha = 1f
        }
        currentYearMonth.month == monthAtIndex -> {
            month.isSelected = true
            month.setTextAppearance(ctx, R.style.TextAppearance_MaterialComponents_Body2)
            month.setTextColor(primaryColor)
            month.alpha = 1f
        }
        selectedMonth == monthAtIndex -> {
            month.isSelected = false
            month.setTextAppearance(ctx, R.style.TextAppearance_MaterialComponents_Subtitle2)
            month.setTextColor(primaryColor)
            month.alpha = 1f
        }
        else -> {
            month.isSelected = false
            month.setTextAppearance(ctx, R.style.TextAppearance_MaterialComponents_Body2)
            month.setTextColor(textColor)
            month.alpha = 1f
        }
    }

    fun updateItemHeight(itemHeight: Int?) {
        this.itemHeight = itemHeight
        notifyDataSetChanged()
    }

    fun updateCurrentYearMonth(yearMonth: YearMonth) {
        currentSelectedYear = yearMonth
        notifyDataSetChanged()
    }

    private fun updateSelectedMonth(month: Month) {
        selectedMonth = month
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = calendarMonths.size

    inner class MonthItem(val binding: NdDialogCalendarMonthItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}