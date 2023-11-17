package com.ndsoftwares.dialogs.app.custom

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.StringRes
import com.ndsoftwares.dialogs.app.databinding.CustomSheetBinding
import com.ndsoftwares.dialogs.core.NDDialog
import com.ndsoftwares.dialogs.core.PositiveListener

@Suppress("unused")
class CustomNDDialog : NDDialog() {

    override val dialogTag = "CustomSheet"

    private lateinit var binding: CustomSheetBinding

    fun onPositive(positiveListener: PositiveListener) {
        this.positiveListener = positiveListener
    }

    fun onPositive(@StringRes positiveRes: Int, positiveListener: PositiveListener? = null) {
        this.positiveText = windowContext.getString(positiveRes)
        this.positiveListener = positiveListener
    }

    fun onPositive(positiveText: String, positiveListener: PositiveListener? = null) {
        this.positiveText = positiveText
        this.positiveListener = positiveListener
    }

    /**
     * Implement this method and add your own layout, which will be appended to the default sheet with toolbar and buttons.
     */
    override fun onCreateLayoutView(): View {

        // Inflate layout through binding class and return the root view
        return CustomSheetBinding.inflate(LayoutInflater.from(activity)).also { binding = it }.root

//        Or without binding
//        return LayoutInflater.from(activity).inflate(R.layout.sheets_custom, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        setButtonPositiveListener {  } //If you want to override the default positive click listener
        displayButtonsView(false) //If you want to change the visibility of the buttons view
//        displayButtonPositive(false) //Hiding the positive button will prevent clicks
//        displayToolbar(false) //Hide the toolbar of the sheet, the title and the icon
    }

    /** Build [CustomNDDialog] and show it later. */
    fun build(ctx: Context, width: Int? = null, func: CustomNDDialog.() -> Unit): CustomNDDialog {
        this.windowContext = ctx
        this.width = width
        this.func()
        return this
    }

    /** Build and show [CustomNDDialog] directly. */
    fun show(ctx: Context, width: Int? = null, func: CustomNDDialog.() -> Unit): CustomNDDialog {
        this.windowContext = ctx
        this.width = width
        this.func()
        this.show()
        return this
    }
}