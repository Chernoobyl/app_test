package com.example.appgesuas.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RegisterModel(
    val form: FormModel,
    val service: ServiceModel,
    val forwarder: String,
    val motive: String,
) : Parcelable
