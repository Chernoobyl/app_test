package com.example.appgesuas.extensions

import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.core.view.children

fun RadioGroup.getIndexSelectedRadioButton(): Int {
    val radioButtonId = checkedRadioButtonId
    return children
        .filter {
            it is RadioButton
        }
        .mapIndexed { index: Int, view: View ->
            index to view
        }
        .firstOrNull {
            it.second.id == radioButtonId
        }?.first ?: -1
}
