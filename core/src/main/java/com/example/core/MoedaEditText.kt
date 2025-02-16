package com.jamiltondamasceno.core

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.text.NumberFormat
import java.util.Locale
import kotlin.math.pow

fun EditText.adicionarMascaraMoeda(
    local: Locale = Locale("pt", "BR"),
    maximoDigitosDecimais: Int = 2,
    simboloMoedaCustomizado: String = "R$",
    maximoDigitos: Int = 7
) {
    var currentText = ""

    val currencyFormat = NumberFormat.getCurrencyInstance(local).apply {
        maximumFractionDigits = maximoDigitosDecimais
    }

    val watcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            // Nenhuma ação necessária aqui
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            // Nenhuma ação necessária aqui
        }

        override fun afterTextChanged(s: Editable?) {
            if (s.toString() != currentText) {
                removeTextChangedListener(this)

                // Remove caracteres não numéricos
                val cleanString = s.toString().replace("\\D".toRegex(), "")

                // Limita o número máximo de dígitos conforme definido em maxDigits
                val limitedString = if (maximoDigitos != null && cleanString.length > maximoDigitos) {
                    cleanString.take(maximoDigitos)
                } else {
                    cleanString
                }

                // Converte para decimal
                val parsed = limitedString.toDoubleOrNull()?.div(10.0.pow(maximoDigitosDecimais.toDouble())) ?: 0.0

                // Aplica o símbolo customizado, se fornecido
                val currencySymbol = simboloMoedaCustomizado ?: currencyFormat.currency?.symbol ?: ""

                // Formata o valor com o símbolo sempre antes do número
                var formatted = currencyFormat.format(parsed)
                formatted = "$currencySymbol ${formatted.replace(currencyFormat.currency?.symbol ?: "", "").trim()}"

                currentText = formatted
                setText(formatted)
                setSelection(formatted.length)

                addTextChangedListener(this)
            }
        }
    }

    // Adiciona o TextWatcher ao EditText
    this.addTextChangedListener(watcher)
}

// Função para obter o valor atual como Double, acessível em qualquer EditText
fun EditText.moedaToDouble(maximoDigitosDecimais: Int = 2): Double {
    val cleanString = this.text.toString().replace("\\D".toRegex(), "")
    return cleanString.toDoubleOrNull()?.div(10.0.pow(maximoDigitosDecimais.toDouble())) ?: 0.0
}

// Função para formatar um Double como moeda
fun Double.formatarComoMoeda(
    local: Locale = Locale("pt", "BR"),
    maximoDigitosDecimais: Int = 2,
    simboloMoedaCustomizado: String = "R$"
): String {
    val currencyFormat = NumberFormat.getCurrencyInstance(local).apply {
        maximumFractionDigits = maximoDigitosDecimais
    }

    // Aplica o símbolo customizado, se fornecido
    val currencySymbol = simboloMoedaCustomizado ?: currencyFormat.currency?.symbol ?: ""

    // Formata o valor com o símbolo sempre antes do número
    val formatted = currencyFormat.format(this)
    return "$currencySymbol ${formatted.replace(currencyFormat.currency?.symbol ?: "", "").trim()}"
}