package com.example.appgesuas.util

import android.text.Editable
import android.text.TextWatcher

object MaskTextWatcher {
    fun unmask(s: String, mask: String? = "", country: String? = ""): String {
        var s = s
        val c = mask?.toCharArray() ?: charArrayOf()
        for (i in c.indices) {
            when (c[i]) {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> if (i < s.length && s[i] == c[i]) {
                    s = s.substring(0, i) + '.'.toString() + s.substring(i + 1)
                }
            }
        }

        country?.let {
            if (it == "Perú") {
                return s
            }
        }

        return unmaskOnlySymbol(
            s,
        )
    }

    fun unmaskOnlySymbol(s: String): String {
        return s.replace("[.]".toRegex(), "").replace("[-]".toRegex(), "")
            .replace("[/]".toRegex(), "").replace("[(]".toRegex(), "")
            .replace("[)]".toRegex(), "").replace("[+]".toRegex(), "")
            .replace("[,]".toRegex(), "").replace("[$]".toRegex(), "")
            .replace("[ ]".toRegex(), "")
    }

    fun mask(mask: String): TextWatcher {
        return insert(
            mask,
        )
    }

    fun insert(mask: String): TextWatcher {
        return insert(
            arrayOf(mask),
        )
    }

    fun insert(masks: Array<String>) = object : TextWatcher {
        var old = ""

        override fun onTextChanged(
            s: CharSequence,
            start: Int,
            before: Int,
            count: Int,
        ) {
        }

        override fun beforeTextChanged(
            s: CharSequence,
            start: Int,
            count: Int,
            after: Int,
        ) {
            old = s.toString()
        }

        override fun afterTextChanged(s: Editable) {
            var mask = masks[masks.size - 1]

            for (other in masks) {
                if (unmask(
                        s.toString(),
                        other,
                    ).length <= unmask(
                        other,
                        other,
                    ).length
                ) {
                    mask = other
                    break
                }
            }

            val crude =
                unmask(
                    s.toString(),
                    mask,
                )
            val str =
                unmask(
                    s.toString(),
                    mask,
                )
            var mascara = ""

            var i = 0
            for (m in mask.toCharArray()) {
                if (m != '#' && (str.length > old.length || crude.length != i)) {
                    mascara += m
                    continue
                }
                try {
                    mascara += str.get(i)
                } catch (e: Exception) {
                    break
                }

                i++
            }

            if (s.toString() != mascara) {
                val filters = s.filters
                s.filters = arrayOf()
                s.replace(0, s.length, mascara)
                s.filters = filters
            }
        }
    }

    fun addMask(textToFormat: String, mask: String): String? {
        var formatado: String? = ""
        var i = 0
        for (m in mask.toCharArray()) {
            if (m != '#') {
                formatado += m
                continue
            }
            formatado += try {
                textToFormat[i]
            } catch (e: java.lang.Exception) {
                break
            }
            i++
        }
        return formatado
    }
}
