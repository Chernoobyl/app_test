package com.example.appgesuas.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ServiceModel(
    val description: String
) : Parcelable
