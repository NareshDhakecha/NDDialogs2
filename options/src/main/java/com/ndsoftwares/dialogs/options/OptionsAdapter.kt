 // NDSoftwares

package com.ndsoftwares.dialogs.options

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable
import android.graphics.drawable.StateListDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.ndsoftwares.dialogs.core.utils.*
import com.ndsoftwares.dialogs.core.views.NDDialogContent
import com.ndsoftwares.dialogs.options.databinding.NdDialogOptionsGridItemBinding
import com.ndsoftwares.dialogs.options.databinding.NdDialogOptionsListItemBinding

internal class OptionsAdapter(
    private val ctx: Context,
    private val options: MutableList<Option>,
    private val type: DisplayMode,
    private val showIcon: Boolean,
    private val multipleChoice: Boolean,
    private val collapsedItems: Boolean,
    private val listener: OptionsSelectionListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TAG_DISABLED_SELECTED = "tag_disabled_selected"
        private const val SELECTOR_STATE_DISABLED_INDEX = 0
        private const val SELECTOR_STATE_SELECTED_INDEX = 1
    }

    private val selectedOptions = mutableMapOf<Int, Pair<ImageView, NDDialogContent>>()

    private val iconsColor = getIconColor(ctx)
    private val textColor = getTextColor(ctx)
    private val highlightColor = getHighlightColor(ctx)

    private val selectedTextColor =
        colorOfAttr(ctx, com.ndsoftwares.dialogs.core.R.attr.ndDialogOptionSelectedTextColor).takeUnlessNotResolved()
            ?: getPrimaryColor(ctx)

    private val selectedIconsColor =
        colorOfAttr(ctx, com.ndsoftwares.dialogs.core.R.attr.ndDialogOptionSelectedImageColor).takeUnlessNotResolved()
            ?: getPrimaryColor(ctx)

    private val disabledTextColor =
        colorOfAttr(ctx, com.ndsoftwares.dialogs.core.R.attr.ndDialogOptionDisabledTextColor).takeUnlessNotResolved()
            ?: getTextColor(ctx)

    private val disabledIconsColor =
        colorOfAttr(ctx, com.ndsoftwares.dialogs.core.R.attr.ndDialogOptionDisabledImageColor).takeUnlessNotResolved()
            ?: getIconColor(ctx)

    private val disabledBackgroundColor =
        colorOfAttr(ctx, com.ndsoftwares.dialogs.core.R.attr.ndDialogOptionDisabledBackgroundColor).takeUnlessNotResolved()
            ?: colorOf(ctx, com.ndsoftwares.dialogs.core.R.color.ndDialogOptionDisabledColor)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (type) {
            DisplayMode.GRID_HORIZONTAL, DisplayMode.GRID_VERTICAL -> {
                GridItem(
                    NdDialogOptionsGridItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            DisplayMode.LIST -> {
                ListItem(
                    NdDialogOptionsListItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, i: Int) {
        when (holder) {
            is GridItem -> holder.binding.buildGridItem(i)
            is ListItem -> holder.binding.buildListItem(i)
        }
    }

    private fun NdDialogOptionsListItemBinding.buildListItem(i: Int) {

        val option = options[i]

        label.text = option.textRes?.let { ctx.getString(it) } ?: option.text ?: ""


        option.drawable?.let {
            icon.setImageDrawable(it)
            icon.visibility = View.VISIBLE
        }

        option.drawableRes?.let { res ->
            icon.setImageDrawable(ContextCompat.getDrawable(ctx, res))
            icon.visibility = View.VISIBLE
        }

        icon.visibility = if (showIcon)  View.VISIBLE else View.GONE

        optionContainer.changeRippleAndStateColor()

        val selected = option.selected || listener.isSelected(i)

        if (option.disabled && !selected) {
            showDisabled(label, icon, optionContainer)
        } else {

            if (option.disabled && selected) optionContainer.tag = TAG_DISABLED_SELECTED
            else optionContainer.setOnClickListener {
                selectOption(i, label, icon, optionContainer)
            }

            if (selected) selectOption(i, label, icon, optionContainer)
            else showDeselected(label, icon, optionContainer)
        }
    }

    private fun NdDialogOptionsGridItemBinding.buildGridItem(i: Int) {

        val option = options[i]

        if (collapsedItems) {
            root.layoutParams = ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
            )
        }

        label.text = option.textRes?.let { ctx.getString(it) } ?: option.text ?: ""

        option.drawable?.let {
            icon.setImageDrawable(it)
            icon.visibility = View.VISIBLE
        }

        option.drawableRes?.let { res ->
            icon.setImageDrawable(ContextCompat.getDrawable(ctx, res))
            icon.visibility = View.VISIBLE
        }

        icon.visibility = if (showIcon) View.VISIBLE else View.GONE

        optionContainer.changeRippleAndStateColor()

        val selected = option.selected || listener.isSelected(i)

        if (option.disabled && !selected) {
            showDisabled(label, icon, optionContainer)
        } else {

            if (option.disabled && selected) optionContainer.tag = TAG_DISABLED_SELECTED
            else optionContainer.setOnClickListener {
                selectOption(i, label, icon, optionContainer)
            }

            if (selected) selectOption(i, label, icon, optionContainer)
            else showDeselected(label, icon, optionContainer)
        }
    }

    private fun showDisabled(label: NDDialogContent, icon: ImageView, root: View) {
        label.setTextColor(disabledTextColor)
        icon.setColorFilter(disabledIconsColor)
        root.changeRippleAndStateColor(
            Color.TRANSPARENT,
            SELECTOR_STATE_DISABLED_INDEX,
            disabledBackgroundColor
        )
        root.isActivated = true
    }

    private fun showSelected(label: NDDialogContent, icon: ImageView, root: View) {
        label.setTextColor(selectedTextColor)
        icon.setColorFilter(selectedIconsColor)

        if (root.tag == TAG_DISABLED_SELECTED) {
            root.changeRippleAndStateColor(Color.TRANSPARENT)
        }

        if (multipleChoice) {
            root.isSelected = true
        }
    }

    private fun showDeselected(label: NDDialogContent, icon: ImageView, root: View) {
        label.setTextColor(textColor)
        icon.setColorFilter(iconsColor)
        if (multipleChoice) {
            root.isSelected = false
        }
    }

    private fun View.changeRippleAndStateColor(
        rippleColor: Int = highlightColor,
        stateIndex: Int = SELECTOR_STATE_SELECTED_INDEX,
        stateBackgroundColor: Int = highlightColor
    ) {
        // Ripple drawable
        (background as RippleDrawable).apply {
            setColor(ColorStateList.valueOf(rippleColor))
            // Selector drawable
            (getDrawable(1) as StateListDrawable).apply {
                // Selected state drawable
                (getStateDrawable(stateIndex) as GradientDrawable).apply {
                    color = ColorStateList.valueOf(stateBackgroundColor)
                }
            }
        }
    }

    private fun selectOption(index: Int, label: NDDialogContent, icon: ImageView, root: View) {
        if (multipleChoice) {
            if (!listener.isMultipleChoiceSelectionAllowed(index)) return
            if (selectedOptions.contains(index)) {
                listener.deselectMultipleChoice(index)
                selectedOptions[index]?.let { showDeselected(it.second, it.first, root) }
                selectedOptions.remove(index)
            } else {
                listener.selectMultipleChoice(index)
                selectedOptions[index] = Pair(icon, label)
                showSelected(label, icon, root)
            }
        } else {
            selectedOptions.forEach { showDeselected(it.value.second, it.value.first, root) }
            selectedOptions.clear()
            selectedOptions[index] = Pair(icon, label)
            showSelected(label, icon, root)
            listener.select(index)
        }
    }

    override fun getItemCount(): Int = options.size

    inner class GridItem(val binding: NdDialogOptionsGridItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    inner class ListItem(val binding: NdDialogOptionsListItemBinding) :
        RecyclerView.ViewHolder(binding.root)

}