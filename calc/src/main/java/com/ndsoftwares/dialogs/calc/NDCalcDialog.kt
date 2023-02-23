package com.ndsoftwares.dialogs.calc

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.ndsoftwares.dialogs.calc.databinding.NdDialogCalcBinding
import com.ndsoftwares.dialogs.core.NDDialog
import net.objecthunter.exp4j.ExpressionBuilder
import org.apache.commons.lang3.math.NumberUtils
import java.text.DecimalFormat
import java.util.*

/** Listener which returns the amount. */
typealias AmountListener = (amount: Double) -> Unit

class NDCalcDialog : NDDialog() {

//    private var mAmount: String = ""
    private lateinit var binding: NdDialogCalcBinding

    private var listener: AmountListener? = null

    private var totalAmount: Double = 0.0

    fun defaultAmount(amount: Double){
        totalAmount = amount
    }

    /**
     * Set a listener.
     *
     * @param positiveListener Listener that is invoked when the positive button is clicked.
     */
    fun onPositive(positiveListener: AmountListener) {
        this.listener = positiveListener
    }

    /**
     * Set the text of the positive button and optionally a listener.
     *
     * @param positiveRes The String resource id for the positive button.
     * @param positiveListener Listener that is invoked when the positive button is clicked.
     */
    fun onPositive(@StringRes positiveRes: Int, positiveListener: AmountListener? = null) {
        this.positiveText = windowContext.getString(positiveRes)
        this.listener = positiveListener
    }

    /**
     * Set the text of the positive button and optionally a listener.
     *
     * @param positiveText The text for the positive button.
     * @param positiveListener Listener that is invoked when the positive button is clicked.
     */
    fun onPositive(positiveText: String, positiveListener: AmountListener? = null) {
        this.positiveText = positiveText
        this.listener = positiveListener
    }

    /**
     * Set the text and icon of the positive button and optionally a listener.
     *
     * @param positiveRes The String resource id for the positive button.
     * @param drawableRes The drawable resource for the button icon.
     * @param positiveListener Listener that is invoked when the positive button is clicked.
     */
    fun onPositive(@StringRes positiveRes: Int, @DrawableRes drawableRes: Int, positiveListener: AmountListener? = null) {
        this.positiveText = windowContext.getString(positiveRes)
        this.positiveButtonDrawableRes = drawableRes
        this.listener = positiveListener
    }

    /**
     * Set the text and icon of the positive button and optionally a listener.
     *
     * @param positiveText The text for the positive button.
     * @param drawableRes The drawable resource for the button icon.
     * @param positiveListener Listener that is invoked when the positive button is clicked.
     */
    fun onPositive(positiveText: String, @DrawableRes drawableRes: Int, positiveListener: AmountListener? = null) {
        this.positiveText = positiveText
        this.positiveButtonDrawableRes = drawableRes
        this.listener = positiveListener
    }

    override fun onCreateLayoutView(): View =
        NdDialogCalcBinding.inflate(LayoutInflater.from(activity))
            .also { binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setButtonPositiveListener(::save)
        initUI()
        setDefaultAmount()
    }

    private fun save() {
        listener?.invoke(totalAmount)
        dismiss()
    }

    private fun setDefaultAmount() {

        binding.textResult.text = getAmountExpression()
        evalExpression()
    }

    private fun getAmountExpression(): CharSequence {

        val formatter = DecimalFormat("#0.0#")
        if (totalAmount != 0.0) {
            return if(totalAmount % 1 > 0) {
                formatter.format(totalAmount)
            } else {
                totalAmount.toInt().toString()
            }
        }else
            return "0"
    }

    private fun initUI() {
        with(binding){
            button0.setOnClickListener { onDigit("0") }
            button1.setOnClickListener { onDigit("1") }
            button2.setOnClickListener { onDigit("2") }
            button3.setOnClickListener { onDigit("3") }
            button4.setOnClickListener { onDigit("4") }
            button5.setOnClickListener { onDigit("5") }
            button6.setOnClickListener { onDigit("6") }
            button7.setOnClickListener { onDigit("7") }
            button8.setOnClickListener { onDigit("8") }
            button9.setOnClickListener { onDigit("9") }

            buttonFloat.setOnClickListener { onDecimal() }

            buttonBack.setOnClickListener { onBackspace() }

            buttonStart.setOnClickListener { onBracketStart() }
            buttonEnd.setOnClickListener { onBracketEnd() }

            buttonAdd.setOnClickListener { onOperator("+") }
            buttonMin.setOnClickListener { onOperator("-") }
            buttonTimes.setOnClickListener { onOperator("*") }
            buttonDiv.setOnClickListener { onOperator("/") }

            buttonEqual.setOnClickListener { onEqual() }

            buttonClear.setOnClickListener { onClear() }

        }
    }


    private fun evalExpression() {
        var exp: String = binding.textResult.text.toString()
        // replace any blanks
        exp = exp.replace(" ", "")

        if (exp.isNotEmpty()) {
            try {
                val e = ExpressionBuilder(exp).build()
                totalAmount = e.evaluate()
                refreshAmount()
            } catch (ex: IllegalArgumentException) {
                // Just display the last valid value.
                refreshAmount()
            } catch (e: Exception) {
            }
        }
    }

    /**
     * Displays the expression result in the top text box.
     */
    private fun refreshAmount() {
//        var amount: String = totalAmount.toString()
//
//        // check if amount is not empty and is double
//        if (TextUtils.isEmpty(amount)) {
//            amount = 0.0.toString()
//        }
//
//        if (!NumberUtils.isCreatable(amount)) {
//            binding.calcResult.text = amount
//        }else{
//            val fAmount = NumberUtils.toDouble(amount, 0.0)
            val formatter = DecimalFormat("#,##,##0.00")
            val formattedString: String = formatter.format(totalAmount)
            binding.calcResult.text = formattedString
//        }


    }

    private fun onDigit(strDigit: String) {
        if(binding.textResult.text.toString().trim() == "0")
            binding.textResult.text = strDigit
        else
            binding.textResult.append(strDigit)
        evalExpression()
    }

    private fun onOperator(strOperator: String) {
        binding.textResult.append(" $strOperator ")
        evalExpression()
    }

    private fun onClear() {
        binding.textResult.text = "0"
        evalExpression()
    }

    private fun onEqual() {
        try {
            val expression = ExpressionBuilder(binding.textResult.text.toString()).build()
            val result = expression.evaluate()
            binding.textResult.text = (if(result % 1 > 0) result else result.toInt()).toString()
        } catch(e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, "Wrong values", Toast.LENGTH_SHORT).show()
        }
    }

    private fun onBracketStart() {
        binding.textResult.append(" (")
        evalExpression()
    }

     private fun onBracketEnd() {
        binding.textResult.append(") ")
         evalExpression()
    }

     private fun onDecimal() {
        binding.textResult.append(".")
         evalExpression()
    }

    private fun onBackspace() {
        var currentNumber: String = binding.textResult.text.toString().trim()

        // check length
        if (currentNumber.isEmpty()) {
            onClear()
            return
        }

        // first cut-off the last digit
        currentNumber = currentNumber.substring(0, currentNumber.length - 1)
        if (currentNumber.isEmpty())
            onClear()
        else {
            binding.textResult.text = currentNumber
            evalExpression()
        }
    }



    /** Build [NDCalcDialog] and show it later. */
    fun build(ctx: Context, width: Int? = null, func: NDCalcDialog.() -> Unit): NDCalcDialog {
        this.windowContext = ctx
        this.width = width
        this.func()
        return this
    }

    /** Build and show [NDCalcDialog] directly. */
    fun show(ctx: Context, width: Int? = null, func: NDCalcDialog.() -> Unit): NDCalcDialog {
        this.windowContext = ctx
        this.width = width
        this.func()
        this.show()
        return this
    }

}