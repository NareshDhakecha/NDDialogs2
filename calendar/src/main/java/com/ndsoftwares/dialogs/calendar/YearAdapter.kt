 // NDSoftwares

package com.ndsoftwares.dialogs.calendar

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ndsoftwares.dialogs.calendar.databinding.NdDialogCalendarYearItemBinding
import com.ndsoftwares.dialogs.core.utils.getPrimaryColor
import com.ndsoftwares.dialogs.core.utils.getTextColor
import java.time.Year
import java.time.format.DateTimeFormatter
import com.google.android.material.R

/** Listener that is invoked if a year is selected. */
internal typealias YearSelectedListener = (Year) -> Unit

internal class YearAdapter(
    private val ctx: Context,
    private val years: MutableList<Year>,
    private val listener: YearSelectedListener
) : RecyclerView.Adapter<YearAdapter.YearItem>() {

    private val formatter = DateTimeFormatter.ofPattern("yyyy")
    private var selectedYear = Year.now()
    private var currentYear = Year.now()

    private val primaryColor = getPrimaryColor(ctx)
    private val textColor = getTextColor(ctx)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YearItem =
        YearItem(
            NdDialogCalendarYearItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: YearItem, i: Int) {
        val year = years[i]
        holder.binding.year.text = formatter.format(year.atDay(1))
        holder.binding.handleState(year)
        holder.binding.root.setOnClickListener {
            listener.invoke(year)
            updateSelectedYear(year)
        }
    }

    private fun NdDialogCalendarYearItemBinding.handleState(yearAtIndex: Year) = when {
        currentYear == yearAtIndex && selectedYear == yearAtIndex -> {
            year.isSelected = true
            year.setTextAppearance(ctx, R.style.TextAppearance_MaterialComponents_Subtitle2)
            year.setTextColor(primaryColor)
        }
        currentYear == yearAtIndex -> {
            year.isSelected = true
            year.setTextAppearance(ctx, R.style.TextAppearance_MaterialComponents_Body2)
            year.setTextColor(primaryColor)
        }
        selectedYear == yearAtIndex -> {
            year.isSelected = false
            year.setTextAppearance(ctx, R.style.TextAppearance_MaterialComponents_Subtitle2)
            year.setTextColor(primaryColor)
        }
        else -> {
            year.isSelected = false
            year.setTextAppearance(ctx, R.style.TextAppearance_MaterialComponents_Body2)
            year.setTextColor(textColor)
        }
    }

    private fun updateSelectedYear(year: Year) {
        selectedYear = year
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = years.size

    inner class YearItem(val binding: NdDialogCalendarYearItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}