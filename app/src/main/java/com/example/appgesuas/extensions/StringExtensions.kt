package com.example.appgesuas.extensions

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun String.hasTwoNames(): Boolean {
    var hasTwoNames = true
    val splitedName = this.split(" ")

    if (splitedName.size >= 2) {
        if (splitedName[0].length < 2 || splitedName[1].length < 2) {
            hasTwoNames = false
        } else {
            splitedName.forEach {
                if (it.isBlank()) {
                    hasTwoNames = false
                }
            }
        }
    } else {
        hasTwoNames = false
    }
    return hasTwoNames
}

fun String.calculateAge(): Int {
    try {
        val formato = SimpleDateFormat("dd/MM/yyyy", Locale("PT-BR"))
        val dataNasc = formato.parse(this)

        val dataAtual = Calendar.getInstance()
        val dataNascCalendar = Calendar.getInstance()
        dataNascCalendar.time = dataNasc as Date

        val diffAnos = dataAtual.get(Calendar.YEAR) - dataNascCalendar.get(Calendar.YEAR)

        if (dataAtual.get(Calendar.MONTH) < dataNascCalendar.get(Calendar.MONTH) ||
            (dataAtual.get(Calendar.MONTH) == dataNascCalendar.get(Calendar.MONTH) && dataAtual.get(Calendar.DAY_OF_MONTH) < dataNascCalendar.get(Calendar.DAY_OF_MONTH))
        ) {
            // Ainda não fez aniversário este ano, subtrai 1 da idade
            return diffAnos - 1
        }
        return diffAnos
    } catch (e: Exception) {
        println("Ocorreu um erro ao calcular a idade: ${e.message}")
        return -1
    }
}