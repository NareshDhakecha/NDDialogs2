 // NDSoftwares

package com.ndsoftwares.dialogs.color

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.recyclerview.widget.RecyclerView
import com.ndsoftwares.dialogs.color.databinding.NdDialogColorTemplatesItemBinding
import com.ndsoftwares.dialogs.core.utils.colorOf

 internal class ColorAdapter(
    private val ctx: Context,
    @ColorRes
    private val colors: MutableList<Int>,
    @ColorInt
    private val selectedColor: Int,
    private val callback: ColorListener
) : RecyclerView.Adapter<ColorAdapter.ColorItem>() {

    private var selectedItem: ImageView? = null
    private var selectedPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorItem =
        ColorItem(
            NdDialogColorTemplatesItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ColorItem, position: Int) {
        val color = colorOf(ctx, colors[position])
        with(holder.binding) {

            val background = colorView.background as RippleDrawable
            (background.getDrawable(1) as GradientDrawable).setColor(color)

            colorActiveTickMark.visibility =
                if (selectedPosition == position) View.VISIBLE else View.GONE

            root.setOnClickListener {
                selectedPosition = position
//                select(colorActiveTickMark)
                callback.invoke(color)
                notifyDataSetChanged()
            }

            if (selectedColor == color)
                root.callOnClick()
        }
    }

    private fun select(image: ImageView) {
        selectedItem?.visibility = View.GONE
        selectedItem = image
        selectedItem?.visibility = View.VISIBLE
    }

    fun removeSelection() {
//        selectedItem?.visibility = View.GONE
//        selectedItem = null
        selectedPosition = -1
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = colors.size

    inner class ColorItem(val binding: NdDialogColorTemplatesItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}