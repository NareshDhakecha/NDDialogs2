 // NDSoftwares

package com.ndsoftwares.dialogs.input

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.widget.AppCompatRadioButton
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.ndsoftwares.dialogs.core.utils.getIconColor
import com.ndsoftwares.dialogs.core.utils.getPrimaryColor
import com.ndsoftwares.dialogs.core.utils.getTextColor
import com.ndsoftwares.dialogs.core.utils.toDp
import com.ndsoftwares.dialogs.core.views.NDDialogContent
import com.ndsoftwares.dialogs.input.databinding.NdDialogInputCheckBoxItemBinding
import com.ndsoftwares.dialogs.input.databinding.NdDialogInputEditTextItemBinding
import com.ndsoftwares.dialogs.input.databinding.NdDialogInputRadioButtonsItemBinding
import com.ndsoftwares.dialogs.input.databinding.NdDialogInputSpinnerItemBinding
import com.ndsoftwares.dialogs.input.type.*

internal typealias InputAdapterChangeListener = () -> Unit

internal class InputAdapter(
    private val ctx: Context,
    private val input: MutableList<Input>,
    private val listener: InputAdapterChangeListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_EDIT_TEXT = 0
        private const val VIEW_TYPE_CHECK_BOX = 1
        private const val VIEW_TYPE_RADIO_BUTTONS = 2
        private const val VIEW_TYPE_SPINNER = 3
    }

    private val primaryColor = getPrimaryColor(ctx)
    private val iconsColor = getIconColor(ctx)
    private val textColor = getTextColor(ctx)

    override fun getItemViewType(i: Int): Int = when (input[i]) {
        is InputEditText -> VIEW_TYPE_EDIT_TEXT
        is InputCheckBox -> VIEW_TYPE_CHECK_BOX
        is InputRadioButtons -> VIEW_TYPE_RADIO_BUTTONS
        is InputSpinner -> VIEW_TYPE_SPINNER
        else -> throw IllegalStateException("No ViewType for this Input.")
    }

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): RecyclerView.ViewHolder {
        return when (type) {
            VIEW_TYPE_EDIT_TEXT -> {
                EditTextViewHolder(
                    NdDialogInputEditTextItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            VIEW_TYPE_CHECK_BOX -> {
                CheckBoxViewHolder(
                    NdDialogInputCheckBoxItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            VIEW_TYPE_RADIO_BUTTONS -> {
                RadioButtonsViewHolder(
                    NdDialogInputRadioButtonsItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            VIEW_TYPE_SPINNER -> {
                SpinnerViewHolder(
                    NdDialogInputSpinnerItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            else -> throw IllegalStateException("No ViewHolder for this ViewType.")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, i: Int) {
        val input = input[i]
        when {
            holder is EditTextViewHolder && input is InputEditText -> {
                holder.binding.buildEditText(input)
            }
            holder is CheckBoxViewHolder
                    && input is InputCheckBox -> {
                holder.binding.buildCheckBox(input)
            }
            holder is RadioButtonsViewHolder
                    && input is InputRadioButtons -> {
                holder.binding.buildRadioButtons(input)
            }
            holder is SpinnerViewHolder
                    && input is InputSpinner -> {
                holder.binding.buildSpinner(input)
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun NdDialogInputEditTextItemBinding.buildEditText(input: InputEditText) {

        setupGeneralInputInfo(input, label, icon)

        with(textInput) {

            setText(input.value)

            input.isPasswordVisible?.let {
                transformationMethod = if (it) null else PasswordTransformationMethod()
            }

            input.inputType?.let { inputType = it }
            input.maxLines?.let { maxLines = it }
            input.inputFilter?.let { filters = arrayOf(it) }

            isVerticalScrollBarEnabled = true

            setOnTouchListener { view, motionEvent ->
                if (motionEvent.action != 0 && MotionEvent.ACTION_UP != 0 || MotionEvent.ACTION_DOWN != 0) {
                    view.parent.requestDisallowInterceptTouchEvent(true)
                    false
                } else false
            }

            addTextWatcher { text ->
                input.value = text
                listener.invoke()
            }
        }

        with(textInputLayout) {

            val hintText = input.hintRes?.let { ctx.getString(it) } ?: input.hint
            hintText?.let { hint = it }

            input.endIconMode?.let { endIconMode = it }
            input.isEndIconActivated?.let { setEndIconActivated(it) }
            input.startIconDrawableRes?.let { setStartIconDrawable(it) }
            input.errorIconDrawableRes?.let { setErrorIconDrawable(it) }
            input.isEndIconActivated?.let { setEndIconActivated(it) }
            setStartIconTintList(ColorStateList.valueOf(primaryColor))

            input.validationResultListener { result ->
                if (!result.valid) {
                    error = result.errorText
                }
                isErrorEnabled = !result.valid
            }
        }
    }

    private fun NdDialogInputCheckBoxItemBinding.buildCheckBox(input: InputCheckBox) {

        setupGeneralInputInfo(input, label, icon)

        checkBox.isChecked = input.value

        val checkBoxText = input.textRes?.let { ctx.getString(it) } ?: input.text
        checkBox.text = checkBoxText
        checkBox.setTextColor(textColor)
        checkBox.buttonTintList = ColorStateList.valueOf(primaryColor)
        checkBox.setOnCheckedChangeListener { _, checked ->
            input.value = checked
            listener.invoke()
        }
    }

    private fun NdDialogInputRadioButtonsItemBinding.buildRadioButtons(input: InputRadioButtons) {

        setupGeneralInputInfo(input, label)

        input.radioButtonOptions?.forEachIndexed { index, radioButtonText ->
            val button = AppCompatRadioButton(ctx).apply {
                setTextAppearance(ctx, android.R.style.TextAppearance_Material_Body2)
                setTextColor(textColor)
                buttonTintList = ColorStateList.valueOf(primaryColor)
                layoutParams = RadioGroup.LayoutParams(
                    RadioGroup.LayoutParams.MATCH_PARENT,
                    RadioGroup.LayoutParams.WRAP_CONTENT
                )
                setPadding(16.toDp(), 0, 0, 0)
                text = radioButtonText
                id = index
            }
            radioGroup.addView(button)
        }

        input.value?.let { radioGroup.check(it) }
        radioGroup.setOnCheckedChangeListener { _, idIndex ->
            input.value = idIndex
            listener.invoke()
        }
    }

    private fun NdDialogInputSpinnerItemBinding.buildSpinner(input: InputSpinner) {

        setupGeneralInputInfo(input, label, icon)

        val spinnerOptions = input.spinnerOptions

        if (input.value == null) {
            val spinnerNoSelectionText =
                input.textRes?.let { ctx.getString(it) } ?: input.noSelectionText
            spinnerOptions?.add(spinnerNoSelectionText ?: ctx.getString(R.string.select))
        }

        val adapter: ArrayAdapter<String> = object : ArrayAdapter<String>(
            ctx,
            android.R.layout.simple_spinner_dropdown_item, spinnerOptions ?: mutableListOf()
        ) {
            override fun getCount(): Int =
                super.getCount().takeIf { it > 0 }?.minus(1) ?: super.getCount()
        }
        icon.setColorFilter(primaryColor)
        spinner.adapter = adapter
        val selectionIndex = input.value ?: spinnerOptions?.lastIndex ?: 0
        spinner.setSelection(selectionIndex)
        spinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(aV: AdapterView<*>?, v: View, i: Int, id: Long) {
                (aV?.getChildAt(0) as TextView).setTextColor(textColor)
                if (i == selectionIndex) return
                input.value = i
                listener.invoke()
            }

            override fun onNothingSelected(arg0: AdapterView<*>?) = Unit
        }
    }

    private fun setupGeneralInputInfo(
            input: Input,
            label: NDDialogContent? = null,
            icon: ImageView? = null
    ) {

        label?.let {
            val labelText = input.labelRes?.let { ctx.getString(it) } ?: input.label
            labelText?.let {
                label.text = it.takeUnless { input.required } ?: it.plus(" *")
                label.visibility = View.VISIBLE
            }
        }

        icon?.let {
            input.drawableRes?.let { res ->
                icon.setImageDrawable(ContextCompat.getDrawable(ctx, res))
                icon.setColorFilter(iconsColor)
                icon.visibility = View.VISIBLE
            } ?: kotlin.run { icon.visibility = View.GONE }
        }
    }

    override fun getItemCount(): Int = input.size

    internal inner class EditTextViewHolder(val binding: NdDialogInputEditTextItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    internal inner class CheckBoxViewHolder(val binding: NdDialogInputCheckBoxItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    internal inner class RadioButtonsViewHolder(val binding: NdDialogInputRadioButtonsItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    internal inner class SpinnerViewHolder(val binding: NdDialogInputSpinnerItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}
