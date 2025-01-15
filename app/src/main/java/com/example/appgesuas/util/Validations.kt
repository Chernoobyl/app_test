package com.example.appgesuas.util

import android.os.Build
import java.time.LocalDate
import java.util.Calendar

object Validations {

    fun isValidDate(data: String): Boolean {
        if (data.isEmpty()) {
            return true
        } else {
            val regex = Regex("""^(0[1-9]|1[0-9]|2[0-9]|3[01])/(0[1-9]|1[0-2])/(\d{4})$""")
            val matchResult = regex.matchEntire(data)

            if (matchResult != null) {
                val dia = matchResult.groups[1]?.value?.toInt()
                val mes = matchResult.groups[2]?.value?.toInt()
                val ano = matchResult.groups[3]?.value?.toInt()

                if (dia != null && mes != null && ano != null) {
                    // Valida ano minimo
                    if (ano < 1900) {
                        return false
                    }

                    // Valida fevereiro
                    if (mes == 2) {
                        if (dia > 29 || (dia == 29 && !isAnoBissexto(ano))) {
                            return false
                        }
                    }

                    // Valida data atual
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        val dataAtual = LocalDate.now()
                        val dataFornecida = LocalDate.of(ano, mes, dia)
                        return dataFornecida.isBefore(dataAtual) || dataFornecida.isEqual(dataAtual)
                    } else {
                        val calendario = Calendar.getInstance()
                        val anoAtual = calendario.get(Calendar.YEAR)
                        val mesAtual = calendario.get(Calendar.MONTH) + 1
                        val diaAtual = calendario.get(Calendar.DAY_OF_MONTH)
                        "$diaAtual/$mesAtual/$anoAtual"

                        return if (ano < anoAtual) {
                            true
                        } else if (ano == anoAtual) {
                            if (mes < mesAtual) {
                                true
                            } else {
                                mes == mesAtual && dia <= diaAtual
                            }
                        } else {
                            false
                        }
                    }
                }
            }
            return false
        }
    }

    private fun isAnoBissexto(ano: Int): Boolean {
        return ano % 4 == 0 && (ano % 100 != 0 || ano % 400 == 0)
    }

    fun isValidCpf(cpf: String): Boolean {
        val cpfClean = cpf.replace(".", "").replace("-", "")

        // ## check if size is eleven
        if (cpfClean.length != 11)
            return false

        // ## check if is number
        try {
            cpfClean.toLong()
        } catch (e: Exception) {
            return false
        }

        if (cpfClean == "00000000000" ||
            cpfClean == "11111111111" ||
            cpfClean == "22222222222" ||
            cpfClean == "33333333333" ||
            cpfClean == "44444444444" ||
            cpfClean == "55555555555" ||
            cpfClean == "66666666666" ||
            cpfClean == "77777777777" ||
            cpfClean == "88888888888" ||
            cpfClean == "99999999999"
        ) {
            return false
        }

        // continue
        val dvCurrent10 = cpfClean.substring(9, 10).toInt()
        val dvCurrent11 = cpfClean.substring(10, 11).toInt()

        // the sum of the nine first digits determines the tenth digit
        val cpfNineFirst = IntArray(9)
        var i = 9
        while (i > 0) {
            cpfNineFirst[i - 1] = cpfClean.substring(i - 1, i).toInt()
            i--
        }
        // multiple the nine digits for your weights: 10,9..2
        val sumProductNine = IntArray(9)
        var weight = 10
        var position = 0
        while (weight >= 2) {
            sumProductNine[position] = weight * cpfNineFirst[position]
            weight--
            position++
        }
        // Verify the nineth digit
        var dvForTenthDigit = sumProductNine.sum() % 11
        dvForTenthDigit = 11 - dvForTenthDigit // rule for tenth digit
        if (dvForTenthDigit > 9)
            dvForTenthDigit = 0
        if (dvForTenthDigit != dvCurrent10)
            return false

        // ### verify tenth digit
        val cpfTenFirst = cpfNineFirst.copyOf(10)
        cpfTenFirst[9] = dvCurrent10
        // multiple the nine digits for your weights: 10,9..2
        val sumProductTen = IntArray(10)
        var w = 11
        var p = 0
        while (w >= 2) {
            sumProductTen[p] = w * cpfTenFirst[p]
            w--
            p++
        }
        // Verify the nineth digit
        var dvForeleventhDigit = sumProductTen.sum() % 11
        dvForeleventhDigit = 11 - dvForeleventhDigit // rule for tenth digit
        if (dvForeleventhDigit > 9)
            dvForeleventhDigit = 0
        if (dvForeleventhDigit != dvCurrent11)
            return false

        return true
    }
}
