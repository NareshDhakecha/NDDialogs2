 // NDSoftwares

@file:Suppress("unused")

package com.ndsoftwares.dialogs.input

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.ndsoftwares.dialogs.core.NDDialog
import com.ndsoftwares.dialogs.core.layoutmanagers.CustomLinearLayoutManager
import com.ndsoftwares.dialogs.input.databinding.NdDialogInputBinding
import com.ndsoftwares.dialogs.input.type.Input
import com.ndsoftwares.dialogs.input.type.InputRadioButtons

/** Listener which returns the inputs with the new data. */
typealias InputListener = (result: Bundle) -> Unit

/**
 * The [NDInputDialog] lets you display a form consisting of various inputs.
 */
class NDInputDialog : NDDialog() {

    override val dialogTag = "InputSheet"

    private lateinit var binding: NdDialogInputBinding

    private var listener: InputListener? = null
    private var contentText: String? = null
    private var input = mutableListOf<Input>()

    private val saveAllowed: Boolean
        get() = input.filter { it.required }.all { it.valid() }

    /**
     * Set the content of the sheet.
     *
     * @param content The text for the content.
     */
    fun content(content: String) {
        this.contentText = content
    }

    /**
     * Set the content of the sheet.
     *
     * @param contentRes The String resource id for the content.
     */
    fun content(@StringRes contentRes: Int) {
        this.contentText = windowContext.getString(contentRes)
    }

    /**
     * Set the content of the sheet.
     *
     * @param contentRes Resource id for the format string
     * @param formatArgs The format arguments that will be used for
     *                   substitution.
     */
    fun content(@StringRes contentRes: Int, vararg formatArgs: Any?) {
        this.contentText = windowContext.getString(contentRes, *formatArgs)
    }

    /**
     * Set the [InputListener].
     *
     * @param listener Listener that is invoked with the new input data when the positive button is clicked.
     */
    fun onPositive(listener: InputListener) {
        this.listener = listener
    }

    /**
     * Set the text of the positive button and set the [InputListener].
     *
     * @param positiveRes The String resource id for the positive button.
     * @param listener Listener that is invoked with the new input data when the positive button is clicked.
     */
    fun onPositive(@StringRes positiveRes: Int, listener: InputListener? = null) {
        this.positiveText = windowContext.getString(positiveRes)
        this.listener = listener
    }

    /**
     *  Set the text of the positive button and set the [InputListener].
     *
     * @param positiveText The text for the positive button.
     * @param listener Listener that is invoked with the new input data when the positive button is clicked.
     */
    fun onPositive(positiveText: String, listener: InputListener? = null) {
        this.positiveText = positiveText
        this.listener = listener
    }

    /**
     * Set the text and icon of the positive button and set the [InputListener].
     *
     * @param positiveRes The String resource id for the positive button.
     * @param drawableRes The drawable resource for the button icon.
     * @param listener Listener that is invoked with the new input data when the positive button is clicked.
     */
    fun onPositive(
        @StringRes positiveRes: Int,
        @DrawableRes drawableRes: Int,
        listener: InputListener? = null
    ) {
        this.positiveText = windowContext.getString(positiveRes)
        this.positiveButtonDrawableRes = drawableRes

        this.listener = listener
    }

    /**
     *  Set the text and icon of the positive button and set the [InputListener].
     *
     * @param positiveText The text for the positive button.
     * @param drawableRes The drawable resource for the button icon.
     * @param listener Listener that is invoked with the new input data when the positive button is clicked.
     */
    fun onPositive(
        positiveText: String,
        @DrawableRes drawableRes: Int,
        listener: InputListener? = null
    ) {
        this.positiveText = positiveText
        this.positiveButtonDrawableRes = drawableRes
        this.listener = listener
    }

    /**
     * Add multiple inputs.
     *
     * @param input The [InputRadioButtons] arguments to be added.
     */
    fun with(vararg input: Input) {
        this.input.addAll(input.toMutableList())
    }

    /**
     * Add an input.
     *
     * @param input Instance of [InputRadioButtons].
     */
    fun with(input: Input) {
        this.input.add(input)
    }

    override fun onCreateLayoutView(): View =
        NdDialogInputBinding.inflate(LayoutInflater.from(activity)).also { binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setButtonPositiveListener(::save)
        validate(true)
        contentText?.let { binding.content.text = it; binding.content.visibility = View.VISIBLE }
        with(binding.inputRecyclerView) {
            layoutManager = CustomLinearLayoutManager(requireContext(), true)
            adapter = InputAdapter(requireContext(), input, ::validate)
        }
    }

    private fun validate(init: Boolean = false) {
        displayButtonPositive(saveAllowed, !init)
    }

    private fun save() {
        listener?.invoke(getResult())
        dismiss()
    }

    private fun getResult(): Bundle {
        val bundle = Bundle()
        input.forEachIndexed { i, input ->
            input.invokeResultListener()
            input.putValue(bundle, i)
        }
        return bundle
    }

    /** Build [NDInputDialog] and show it later. */
    fun build(ctx: Context, width: Int? = null, func: NDInputDialog.() -> Unit): NDInputDialog {
        this.windowContext = ctx
        this.width = width
        this.func()
        return this
    }

    /** Build and show [NDInputDialog] directly. */
    fun show(ctx: Context, width: Int? = null, func: NDInputDialog.() -> Unit): NDInputDialog {
        this.windowContext = ctx
        this.width = width
        this.func()
        this.show()
        return this
    }
}