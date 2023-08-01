package com.example.trailevy.fragments

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.trailevy.data.CurrencyValueFormatter
import com.example.trailevy.data.GetTatalsRequest
import com.example.trailevy.data.PercentValueFormatter
import com.example.trailevy.data.PieChartDataRequest
import com.example.trailevy.databinding.FragmentAnalyticsBinding
import com.example.trailevy.viewModels.AnalyticsViewModel
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.ColorTemplate

class AnalyticsFragment : Fragment(), OnChartValueSelectedListener {

     private lateinit var binding: FragmentAnalyticsBinding
    private lateinit var pieChart: PieChart
    private lateinit var analyticsMvvm: AnalyticsViewModel
    private var centerText: String = "Expenses"
    private  var dataSet: PieDataSet?= null







    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAnalyticsBinding.inflate(inflater,container,false)


        analyticsMvvm = ViewModelProvider(this)[AnalyticsViewModel::class.java]



        pieChart = binding.pieChart

        analyticsMvvm.getPieData()

            observeTotals()
            setupPieChart()
            observePieChartData()
            loadPieChartData()



        pieChart.setOnChartValueSelectedListener(this)



        return binding.root
    }


    @Override
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        analyticsMvvm.getTatals()

    }





    private fun setupPieChart() {



        pieChart.setUsePercentValues(true)
        pieChart.description.isEnabled = false
        pieChart.setExtraOffsets(5f, 10f, 5f, 5f)
        pieChart.dragDecelerationFrictionCoef = 0.95f
        pieChart.isDrawHoleEnabled = true
        pieChart.centerText = centerText
        pieChart.setHoleColor(android.R.color.transparent)
        pieChart.transparentCircleRadius = 61f
        pieChart.setCenterTextSize(18f)


        pieChart.legend.isEnabled = true
        pieChart.legend.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        pieChart.legend.horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
        pieChart.legend.orientation = Legend.LegendOrientation.HORIZONTAL
        pieChart.legend.setDrawInside(false)
        pieChart.legend.isWordWrapEnabled = true
        pieChart.legend.formSize = 12f
        pieChart.legend.formToTextSpace = 4f
        pieChart.legend.xEntrySpace = 8f
        pieChart.legend.yEntrySpace = 0f
        pieChart.legend.yOffset = 16f


        dataSet = PieDataSet(null, null)





    }



    private  fun observePieChartData(){
        val mutablePieEntries: MutableList<PieEntry> = mutableListOf()

        analyticsMvvm.observePieChartLiveData().observe(viewLifecycleOwner,object :Observer<List<PieChartDataRequest>>{
            override fun onChanged(t: List<PieChartDataRequest>?) {
                mutablePieEntries.clear()
                t?.let { pieChartDataRequests ->

                    if (pieChartDataRequests.isNotEmpty()) {
                        for (dataRequest in pieChartDataRequests) {
                            val pieEntry = PieEntry(dataRequest.value.toFloat(), dataRequest.label)
                            mutablePieEntries.add(pieEntry)
                        }
                        dataSet?.values = mutablePieEntries
                        pieChart.notifyDataSetChanged()
                        pieChart.invalidate()
                        Toast.makeText(context,"success!!",Toast.LENGTH_LONG).show()
                    } else {
                        // Handle the case when there is no data available

                        pieChart.visibility = View.GONE
                        Toast.makeText(context, "No chart data available", Toast.LENGTH_SHORT).show()
                    }}


            }


            })
    }

    private fun observeTotals(){
        analyticsMvvm.observeTotalsLiveData().observe(viewLifecycleOwner,object :Observer<GetTatalsRequest>{
            override fun onChanged(t: GetTatalsRequest?) {
                if (t != null) {
                    val valueFormatter = CurrencyValueFormatter()
                    val balance = valueFormatter.getFormattedValue(t.balance?.toFloatOrNull() ?: 0f)
                    val expense = valueFormatter.getFormattedValue(t.expenses?.toFloatOrNull() ?: 0f)



                    binding.tvValueBalance.text = balance
                    binding.tvValueExpense.text = expense





                } else {

                }


            }

        })
    }





    private fun loadPieChartData() {


            // Create a custom color list without repetitions
            val colors = ArrayList<Int>()

            colors.add(Color.rgb(255, 108, 139))
            colors.add( Color.rgb(0, 205, 255))
            colors.add( Color.rgb(177, 159, 249))
            colors.add( Color.rgb(222, 217, 62))
            colors.add( Color.rgb(68, 238, 119))

            for (c in ColorTemplate.COLORFUL_COLORS) {
                if (!colors.contains(c)) {
                    colors.add(c)
                }
            }


            dataSet?.colors = colors
            dataSet?.valueTextSize = 14f
            dataSet?.valueFormatter = PercentValueFormatter()

            val data = PieData(dataSet)
            pieChart.data = data


        }








    override fun onValueSelected(e: Entry?, h: Highlight?) {
        // Show popup or perform action when a slice is selected
        if (e != null) {
            val dataSetIndex = h?.dataSetIndex ?: 0
            val label = pieChart.data.getDataSetByIndex(dataSetIndex).getEntryForIndex(h?.x?.toInt() ?: 0).label
            val value = pieChart.data.getDataSetByIndex(dataSetIndex).getEntryForIndex(h?.x?.toInt() ?: 0).y



            val valueFormatter = CurrencyValueFormatter()
            val formattedValue = valueFormatter.getFormattedValue(value)


            showPopup(label, formattedValue)
        }
    }




    override fun onNothingSelected() {
        hidePopup()
    }






    private fun showPopup(label: String, value: String) {
        // Create and show the popup with the label and value information

        pieChart.centerText = value

    }


    private fun hidePopup() {
        pieChart.centerText = centerText
    }


}