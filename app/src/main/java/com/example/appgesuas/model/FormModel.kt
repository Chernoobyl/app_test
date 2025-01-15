package com.example.appgesuas.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FormModel(
    var name: String,
    var cpf: String,
    var birtDate: String? = null,
    var cellPhone: String,
) : Parcelable
