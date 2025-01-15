package com.example.appgesuas.extensions

import android.widget.EditText
import com.example.appgesuas.util.MaskTextWatcher

fun EditText.setMask(mask: String) {
    this.addTextChangedListener(MaskTextWatcher.insert(mask))
}
