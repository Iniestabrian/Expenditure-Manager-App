package com.example.trailevy.data

import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale


class CurrencyValueFormatter : ValueFormatter() {
    private val currencyFormat = DecimalFormat("Ksh #,###")

    override fun getFormattedValue(value: Float): String {
        return currencyFormat.format(value)
    }
}