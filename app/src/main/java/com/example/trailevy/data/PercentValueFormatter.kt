package com.example.trailevy.data

import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.DecimalFormat

class PercentValueFormatter : ValueFormatter() {

    private val decimalFormat = DecimalFormat("###,###,##0.0")

    override fun getFormattedValue(value: Float): String {
        return "${decimalFormat.format(value)}%"
    }
}