package com.moclam1905.cryptotrackerbasic.crypto.presentation.coin_detail

import java.text.NumberFormat
import java.util.Locale

data class ValueLabel(
    val value: Float,
    val unit: String
) {
    fun formattedValue(): String {
        val formatter = NumberFormat.getNumberInstance(Locale.getDefault()).apply {
            val fractionDigits = when {
               value > 1000 -> 0
                value in 2f..999f -> 2
                else -> 3
            }
            maximumFractionDigits = 2
            minimumFractionDigits = 0
        }

        return "${formatter.format(value)}$unit"
    }
}
