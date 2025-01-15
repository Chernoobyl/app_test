package com.example.appgesuas.model

sealed class StateFields {
    object Default : StateFields()
    object Validated : StateFields()
    class InvalidFields(val fields: List<Pair<String, Int>>) : StateFields()
}
