/*
 * Designed and developed by Aidan Follestad (@afollestad)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ndsoftwares.dialogs.core.utils

import android.content.Context
import androidx.annotation.RestrictTo
import androidx.annotation.StyleRes
import com.ndsoftwares.dialogs.core.R
import com.ndsoftwares.dialogs.core.DialogStyle

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
enum class Theme(@StyleRes val styleRes: Int) {

    BOTTOM_SHEET_DAY(R.style.BottomSheet_Base_Light),
    BOTTOM_SHEET_NIGHT(R.style.BottomSheet_Base_Dark),
    DIALOG_SHEET_DAY(R.style.DialogSheet_Base_Light),
    DIALOG_SHEET_NIGHT(R.style.DialogSheet_Base_Dark);

    companion object {
        fun inferTheme(ctx: Context, dialogStyle: DialogStyle): Theme {
            val isPrimaryDark = getTextColor(ctx).isColorDark()
            val isBottomSheet = dialogStyle == DialogStyle.BOTTOM_SHEET
            return when {
                isPrimaryDark -> if (isBottomSheet) BOTTOM_SHEET_DAY else DIALOG_SHEET_DAY
                else -> if (isBottomSheet) BOTTOM_SHEET_NIGHT else DIALOG_SHEET_NIGHT
            }
        }
    }
}