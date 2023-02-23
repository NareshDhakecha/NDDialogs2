 // NDSoftwares

package com.ndsoftwares.dialogs.time_clock

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.RippleDrawable
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.view.View
import com.ndsoftwares.dialogs.core.utils.*
import com.ndsoftwares.dialogs.time_clock.databinding.NdDialogTimeClockBinding
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

internal class ClockTimeSelector(
    ctx: Context,
    private val bindingSelector: NdDialogTimeClockBinding,
    private val is24HoursView: Boolean = true
) {

    private val colorTextInactive = getTextColor(ctx)
    private val primaryColor = getPrimaryColor(ctx)
    private val textActiveColor =
        colorOfAttrs(ctx, com.ndsoftwares.dialogs.core.R.attr.ndDialogValueTextActiveColor).takeUnlessNotResolved()
            ?: primaryColor
    private val highlightColor = getHighlightColor(ctx)

    private val hoursBuffer = StringBuilder("00")
    private val minsBuffer = StringBuilder("00")

    private var isPositionOnHours = true
    private var currentIndex = 0

    private var isAm = true

    init {

        with(bindingSelector) {

            hoursInput.setTextColor(colorTextInactive)
            hoursInput.text = getHoursTime()

            minutesInput.setTextColor(colorTextInactive)
            minutesInput.text = getMinutesTime()

            numericalInput.rightImageListener { increaseIndex() }
            numericalInput.setRightImageDrawable(com.ndsoftwares.dialogs.core.R.drawable.nddialog_ic_arrow_right)

            numericalInput.leftImageListener { reduceIndex() }
            numericalInput.setLeftImageDrawable(com.ndsoftwares.dialogs.core.R.drawable.nddialog_ic_arrow_left)

            numericalInput.digitListener { onDigit(it) }

            hoursInput.setOnClickListener { focusOnHours() }
            minutesInput.setOnClickListener { focusOnMinutes() }

            amLabel.visibility = if (is24HoursView) View.GONE else View.VISIBLE
            pmLabel.visibility = if (is24HoursView) View.GONE else View.VISIBLE

            amLabel.changeHighlightColor()
            amLabel.setOnClickListener { setAmActive() }

            pmLabel.changeHighlightColor()
            pmLabel.setOnClickListener { setPmActive() }

            setAmActive()
            processIndexChange()
        }
    }

    private fun View.changeHighlightColor() {
        (background as RippleDrawable).apply {
            setColor(ColorStateList.valueOf(highlightColor))
        }
    }

    /** Process clicked digit. */
    private fun onDigit(value: Int) {

        if (isPositionOnHours) {

            if (is24HoursView) {
                if (currentIndex == 0 && value >= 3 && value <= 9) {
                    hoursBuffer[currentIndex] = '0'
                    increaseIndex()
                    hoursBuffer[currentIndex] = value.toString()[0]
                } else {
                    if (currentIndex == 0 && value != 0 && Character.getNumericValue(hoursBuffer[1]) > 3) {
                        hoursBuffer[1] = '0'
                    }
                    hoursBuffer[currentIndex] = value.toString()[0]
                }

            } else {
                if (currentIndex == 0 && value >= 2 && value <= 9) {
                    hoursBuffer[currentIndex] = '0'
                    increaseIndex()
                    hoursBuffer[currentIndex] = value.toString()[0]
                } else {
                    if (currentIndex == 0) {
                        if (value != 0 && Character.getNumericValue(hoursBuffer[1]) > 2) {
                            hoursBuffer[1] = '0'
                        } else if (value == 0 && Character.getNumericValue(hoursBuffer[1]) == 0) {
                            hoursBuffer[1] = '1'
                        }
                    }
                    hoursBuffer[currentIndex] = value.toString()[0]
                }
            }
            bindingSelector.hoursInput.text = getHoursTime()

        } else {
            minsBuffer[currentIndex] = value.toString()[0]
            bindingSelector.minutesInput.text = getMinutesTime()
        }

        increaseIndex()
    }

    private fun focusOnHours() {
        currentIndex = 0
        isPositionOnHours = true
        processIndexChange()
    }

    private fun focusOnMinutes() {
        currentIndex = 0
        isPositionOnHours = false
        processIndexChange()
    }

    private fun reduceIndex() {
        when {
            currentIndex == 1 -> currentIndex--
            currentIndex < 1 -> {
                isPositionOnHours = !isPositionOnHours
                currentIndex = 1
            }
        }

        processIndexChange()
    }

    private fun increaseIndex() {

        when {
            currentIndex == 0 -> currentIndex++
            currentIndex >= 1 -> {
                isPositionOnHours = !isPositionOnHours
                currentIndex = 0
            }
        }

        processIndexChange()
    }

    private fun processIndexChange() {
        setUnderlineToIndex()
        limitKeyboardOnIndexInput()
    }

    private fun limitKeyboardOnIndexInput() {

        with(bindingSelector.numericalInput) {
            when {
                isPositionOnHours && currentIndex == 0 -> enableDigits()
                isPositionOnHours && currentIndex == 1 -> {
                    enableDigits()
                    if (is24HoursView) {
                        if (hoursBuffer[0] == '2')
                            disableDigits(4..9)
                    } else {
                        if (hoursBuffer[0] == '1' || hoursBuffer[0] == '2')
                            disableDigits(3..9)
                        else if (hoursBuffer[0] == '0')
                            disableDigits(0..0)
                    }
                }

                !isPositionOnHours && currentIndex == 0 -> {
                    if (hoursBuffer[0] == '2' && hoursBuffer[1] == '4') {
                        disableDigits()
                    } else {
                        enableDigits()
                        disableDigits(6..9)
                    }
                }
                !isPositionOnHours && currentIndex == 1 -> enableDigits()
            }
        }
    }

    private fun setUnderlineToIndex() {

        with(bindingSelector) {

            hoursInput.text = bindingSelector.hoursInput.text.toString()
            minutesInput.text = bindingSelector.minutesInput.text.toString()

            val spanColor = ForegroundColorSpan(textActiveColor)

            if (isPositionOnHours) {

                hoursInput.text = SpannableString(bindingSelector.hoursInput.text).apply {
                    setSpan(
                        spanColor,
                        currentIndex,
                        currentIndex.plus(1),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    setSpan(
                        UnderlineSpan().apply { },
                        currentIndex,
                        currentIndex.plus(1),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }

            } else {

                minutesInput.text = SpannableString(bindingSelector.minutesInput.text).apply {
                    setSpan(
                        spanColor,
                        currentIndex,
                        currentIndex.plus(1),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    setSpan(
                        UnderlineSpan(),
                        currentIndex,
                        currentIndex.plus(1),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }
            }
        }
    }

    /** Get current time in milliseconds, hourOfDay and minutes. */
    fun getTime(): Triple<Long, Int, Int> {

        var hh = getHoursTime().toInt()
        val mm = getMinutesTime().toInt()

        if (!is24HoursView) {
            if (isAm && hh >= 12 && mm > 0) hh -= 12
            else if (!isAm && hh < 12 && mm >= 0) hh += 12
        }

        val hhMillis = TimeUnit.HOURS.toMillis(hh.toLong())
        val mmMillis = TimeUnit.MINUTES.toMillis(mm.toLong())

        val millis = hhMillis + mmMillis

        return Triple(millis, hh, mm)
    }

    /** Set current time. */
    @SuppressLint("SimpleDateFormat")
    fun setTime(timeInMillis: Long) {

        val formatHours = if (is24HoursView) "HH" else "hh"
        val formatMinutes = "mm"
        val timeZoneUTC = TimeZone.getTimeZone("etc/UTC")

        val hours = SimpleDateFormat(formatHours).apply {
            timeZone = timeZoneUTC
        }.format(timeInMillis)

        val minutes = SimpleDateFormat(formatMinutes).apply {
            timeZone = timeZoneUTC
        }.format(timeInMillis)

        if (isAmTime(timeInMillis)) setAmActive()
        else setPmActive()

        hoursBuffer.replace(0, 2, hours.padStart(2, '0'))
        minsBuffer.replace(0, 2, minutes.padStart(2, '0'))

        bindingSelector.hoursInput.text = getHoursTime()
        bindingSelector.minutesInput.text = getMinutesTime()

        focusOnHours()
    }

    private fun getHoursTime(): String =
        hoursBuffer.toString().padStart(2, '0')

    private fun getMinutesTime(): String =
        minsBuffer.toString().padStart(2, '0')

    private fun setAmActive(active: Boolean = true) {
        bindingSelector.amLabel.setTextColor(if (active) textActiveColor else colorTextInactive)
        bindingSelector.pmLabel.setTextColor(if (active) colorTextInactive else textActiveColor)
        isAm = active
    }

    private fun setPmActive() {
        setAmActive(false)
    }
}