 // NDSoftwares

package com.ndsoftwares.dialogs.time

import android.annotation.SuppressLint
import android.content.Context
import android.text.Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
import android.text.Spannable.SPAN_INCLUSIVE_INCLUSIVE
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.view.View
import android.widget.TextView
import com.ndsoftwares.dialogs.core.utils.getPrimaryColor
import com.ndsoftwares.dialogs.core.utils.getTextColor
import com.ndsoftwares.dialogs.core.utils.splitTime
import com.ndsoftwares.dialogs.time.databinding.NdDialogTimeBinding
import java.util.concurrent.TimeUnit

/** Internal listener which informs about the success of the time validation. */
internal typealias TimeValidationListener = (valid: Boolean) -> Unit

internal class TimeSelector(
    private val ctx: Context,
    private val binding: NdDialogTimeBinding,
    private val format: TimeFormat = TimeFormat.M_SS,
    private val minTime: Long = Long.MIN_VALUE,
    private val maxTime: Long = Long.MAX_VALUE,
    private val validationListener: TimeValidationListener? = null
) {

    private val textColor = getTextColor(ctx)
    private val primaryColor = getPrimaryColor(ctx)

    private val time = StringBuffer("0")

    init {

        with(binding) {

            numericalInput.rightImageListener { onBackspace() }
            numericalInput.setRightImageDrawable(com.ndsoftwares.dialogs.core.R.drawable.nddialog_ic_backspace)

            numericalInput.leftImageListener { onClear() }
            numericalInput.setLeftImageDrawable(com.ndsoftwares.dialogs.core.R.drawable.nddialog_ic_backspace)

            numericalInput.digitListener { onDigit(it) }

            timeValue.text = getFormattedTime()
        }

        validationListener?.invoke(false)
    }

    private fun onDigit(value: Int) {

        if (time.length >= format.length) time.deleteCharAt(0)
        time.append(value)
        binding.timeValue.text = getFormattedTime()
        validate()
    }

    @SuppressLint("StringFormatInvalid")
    private fun validate() {

        if (time.isNotEmpty()) {

            val timeInSeconds = getTimeInSeconds()

            when {
                timeInSeconds < minTime -> {
                    binding.hintLabel.visibility = View.VISIBLE
                    binding.hintLabel.text =
                        ctx.getString(
                            com.ndsoftwares.dialogs.core.R.string.at_least_placeholder,
                            getFormattedHintTime(minTime * 1000)
                        )
                    validationListener?.invoke(false)
                }
                timeInSeconds > maxTime -> {
                    binding.hintLabel.visibility = View.VISIBLE
                    binding.hintLabel.text = ctx.getString(
                        com.ndsoftwares.dialogs.core.R.string.at_most_placeholder,
                        getFormattedHintTime(maxTime * 1000)
                    )
                    validationListener?.invoke(false)
                }
                else -> {
                    binding.hintLabel.visibility = View.GONE
                    validationListener?.invoke(true)
                }
            }
        } else {
            binding.hintLabel.visibility = View.GONE
            validationListener?.invoke(false)
        }
    }

    /** Calculates the current time in seconds based on the input. */
    fun getTimeInSeconds(): Long {

        var timeInSeconds = 0L
        val reversedTime = time.reversed()
        val timeIntoFormat = StringBuilder(reversedTime)
        for (i in 2..timeIntoFormat.length step 3) {
            timeIntoFormat.insert(i, '_')
        }
        val timeReversedArray = timeIntoFormat.split("_")
        val formatReversedArray = format.name.reversed().split("_")
        formatReversedArray.forEachIndexed { i, formatTimeUnit ->
            if (i >= timeReversedArray.size) return@forEachIndexed
            val time = timeReversedArray[i]
            if (time.isEmpty()) return@forEachIndexed
            when {
                formatTimeUnit.contains("H", ignoreCase = true) -> {
                    timeInSeconds += TimeUnit.HOURS.toSeconds(time.reversed().toLong())
                }
                formatTimeUnit.contains("M", ignoreCase = true) -> {
                    timeInSeconds += TimeUnit.MINUTES.toSeconds(time.reversed().toLong())
                }
                formatTimeUnit.contains("S", ignoreCase = true) -> {
                    timeInSeconds += time.reversed().toInt()
                }
            }
        }

        return timeInSeconds
    }

    private fun onClear() {
        time.setLength(0)
        binding.timeValue.setText(getFormattedTime(), TextView.BufferType.SPANNABLE)
        validate()
    }

    private fun onBackspace() {

        if (time.isNotEmpty()) time.deleteCharAt(time.lastIndex)

        binding.timeValue.setText(getFormattedTime(), TextView.BufferType.SPANNABLE)
        validate()
    }

    private fun getFormattedTime(): SpannableStringBuilder {

        val smallTextSize = ctx.resources.getDimensionPixelSize(com.ndsoftwares.dialogs.core.R.dimen.textSizeSubheading)

        val formattedTime = SpannableStringBuilder(SpannableString(time))
        repeat(format.length.minus(time.length)) { formattedTime.insert(0, "0") }

        val formatArray = format.name.split("_")
        var insertIndex = 0
        formatArray.forEachIndexed { i, formatTimeUnit ->
            insertIndex += formatTimeUnit.length
            val space = if (formatArray.lastIndex != i) " " else ""
            val span = when {
                formatTimeUnit.contains("H", ignoreCase = true) ->
                    SpannableString(ctx.getString(com.ndsoftwares.dialogs.core.R.string.hour_code).plus(space))
                formatTimeUnit.contains("M", ignoreCase = true) ->
                    SpannableString(ctx.getString(com.ndsoftwares.dialogs.core.R.string.minute_code).plus(space))
                formatTimeUnit.contains("S", ignoreCase = true) ->
                    SpannableString(ctx.getString(com.ndsoftwares.dialogs.core.R.string.second_code).plus(space))
                else -> SpannableString("")
            }
            span.setSpan(
                AbsoluteSizeSpan(smallTextSize),
                0, span.lastIndex.plus(1),
                SPAN_INCLUSIVE_INCLUSIVE
            )
            formattedTime.insert(insertIndex, span)
            formattedTime.setSpan(
                ForegroundColorSpan(primaryColor),
                insertIndex.minus(formatTimeUnit.length),
                insertIndex,
                SPAN_INCLUSIVE_INCLUSIVE
            )
            insertIndex += span.length
        }

        var index = formattedTime.indexOfFirst { it.isDigit() && it != '0' }
        if (index == -1) index = formattedTime.lastIndex.minus(1)

        formattedTime.setSpan(ForegroundColorSpan(textColor), 0, index, SPAN_EXCLUSIVE_EXCLUSIVE)

        formattedTime.setSpan(
            UnderlineSpan(),
            formattedTime.lastIndex.minus(1),
            formattedTime.lastIndex,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        return formattedTime
    }

    private fun getFormattedHintTime(timeInMillis: Long): SpannableString {

        val formattedTime = SpannableStringBuilder()

        if (timeInMillis > 0) {

            var millis = timeInMillis
            val days = TimeUnit.MILLISECONDS.toDays(millis).toInt()
            millis -= TimeUnit.DAYS.toMillis(days.toLong())
            val hours = TimeUnit.MILLISECONDS.toHours(millis).toInt()
            millis -= TimeUnit.HOURS.toMillis(hours.toLong())
            var minutes = TimeUnit.MILLISECONDS.toMinutes(millis).toInt()
            millis -= TimeUnit.MINUTES.toMillis(minutes.toLong())
            val seconds = TimeUnit.MILLISECONDS.toSeconds(millis).toInt()

            val small16dp =
                ctx.resources.getDimensionPixelSize(com.ndsoftwares.dialogs.core.R.dimen.textSizeSubheading)
            val big20dp = ctx.resources.getDimensionPixelSize(com.ndsoftwares.dialogs.core.R.dimen.textSizeTitle)

            if (days > 0) {
                formattedTime.append(days.toString(), AbsoluteSizeSpan(big20dp))
                formattedTime.append(
                    ctx.getString(com.ndsoftwares.dialogs.core.R.string.day_code),
                    AbsoluteSizeSpan(small16dp)
                )
                formattedTime.append("  ")
            }

            if (hours > 0) {
                formattedTime.append(hours.toString(), AbsoluteSizeSpan(big20dp))
                formattedTime.append(ctx.getString(com.ndsoftwares.dialogs.core.R.string.hour_code), AbsoluteSizeSpan(small16dp))
                formattedTime.append(" ")
            }

            if (minutes >= 0 || (hours == 0 || days == 0)) {
                if (seconds > 0) minutes = minutes.plus(1)
                formattedTime.append(minutes.toString(), AbsoluteSizeSpan(big20dp))
                formattedTime.append(
                    ctx.getString(com.ndsoftwares.dialogs.core.R.string.minute_code),
                    AbsoluteSizeSpan(small16dp)
                )
                formattedTime.append(" ")
            }
        }

        return SpannableString(formattedTime)
    }

    private fun SpannableStringBuilder.append(text: CharSequence, span: Any) {
        val textLength = text.length
        append(text)
        setSpan(span, this.length - textLength, length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
    }

    /** Set current time based on the seconds passed. */
    fun setTime(timeInSeconds: Long) {

        val (days, hours, minutes, seconds) = splitTime(timeInSeconds)

        // No support for days yet
        val filledTimeString = StringBuilder().apply {
            when(format) {
                TimeFormat.HH_MM_SS -> {
                    append(hours.toString().padStart(2, '0'))
                    append(minutes.toString().padStart(2, '0'))
                    append(seconds.toString().padStart(2, '0'))
                }
                TimeFormat.HH_MM -> {
                    append(hours.toString().padStart(2, '0'))
                    append(minutes.toString().padStart(2, '0'))
                }
                TimeFormat.MM_SS -> {
                    append(minutes.toString().padStart(2, '0'))
                    append(seconds.toString().padStart(2, '0'))
                }
                TimeFormat.M_SS -> {
                    append(minutes.toString().substring(0, minutes.toString().length.coerceAtMost(1)))
                    append(seconds.toString().padStart(2, '0'))
                }
                TimeFormat.HH -> {
                    append(hours.toString().padStart(2, '0'))
                }
                TimeFormat.MM -> {
                    append(minutes.toString().padStart(2, '0'))
                }
                TimeFormat.SS -> {
                    append(seconds.toString().padStart(2, '0'))
                }
            }
        }

        time.setLength(0)
        filledTimeString.reversed().forEach {
            time.insert(0, it)
        }

        binding.timeValue.text = getFormattedTime()
        validate()
    }

}