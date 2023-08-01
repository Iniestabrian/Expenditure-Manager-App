package com.example.trailevy.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.trailevy.R
import com.example.trailevy.data.CurrencyValueFormatter
import com.example.trailevy.data.RecordsRequest

class RecordsAdapter(val context: Context,val recordList:List<RecordsRequest>): RecyclerView.Adapter<RecordsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemView = LayoutInflater.from(context).inflate(R.layout.records_design,parent,false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
      return  recordList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = recordList[position]
        val valueFormatter = CurrencyValueFormatter()
        val formattedAmountSpent = valueFormatter.getFormattedValue(currentItem.amountSpent.toFloat())

        holder.dateSpent.text = currentItem.dateSpent
        holder.firstLetter.text = currentItem.categoryName.first().toString()
        holder.tvcategoryName.text = currentItem.categoryName
        holder.amountSpent.text = formattedAmountSpent
    }


    class ViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView){

        val tvcategoryName : TextView = itemView.findViewById(R.id.tvCategoryName)
        val firstLetter : TextView = itemView.findViewById(R.id.tvcategoryFirstLetter)
        val dateSpent : TextView = itemView.findViewById(R.id.tvDate)
        val amountSpent:TextView = itemView.findViewById(R.id.tvAmountSpent)


    }
}