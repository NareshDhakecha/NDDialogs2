 // NDSoftwares

package com.ndsoftwares.dialogs.input

import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import androidx.annotation.RestrictTo
import androidx.appcompat.widget.AppCompatEditText

/** Add a [TextWatcher] to the [AppCompatEditText]. */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
fun AppCompatEditText.addTextWatcher(textChanged: ((String) -> Unit)? = null): TextWatcher {
    var timer: CountDownTimer? = null
    val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
        override fun afterTextChanged(editable: Editable?) {
            timer?.cancel()
            timer = object : CountDownTimer(300, 100) {
                override fun onTick(millisUntilFinished: Long) = Unit
                override fun onFinish() {
                    textChanged?.invoke(editable.toString())
                }
            }.start()
        }
    }
    addTextChangedListener(textWatcher)
    return textWatcher
}