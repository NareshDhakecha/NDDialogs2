package com.ndsoftwares.sample.utils

import androidx.annotation.StringRes
import com.ndsoftwares.dialogs.app.R


enum class SheetType(@StringRes val titleRes: Int, @StringRes val descRes: Int) {

    CALC(R.string.calc_sheet, R.string.calc_sheet_desc),

    OPTIONS(R.string.options_sheet, R.string.options_sheet_desc),

    COLOR(R.string.color_sheet, R.string.color_sheet_desc),

    CLOCK_TIME(R.string.clock_time_sheet, R.string.clock_time_sheet_desc),

    TIME(R.string.time_sheet, R.string.time_sheet_desc),

    CALENDAR(R.string.calendar_sheet, R.string.calendar_sheet_desc),

    INFO(R.string.info_sheet, R.string.info_sheet_desc),

    LOTTIE(R.string.lottie, R.string.lottie_desc),

    INPUT(R.string.input_sheet, R.string.input_sheet_desc),

    CUSTOM(R.string.custom_sheets, R.string.custom_sheets_desc),
}