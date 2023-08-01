package com.example.trailevy.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.trailevy.R
import com.example.trailevy.data.BudgetedCategoriesRequest
import com.example.trailevy.data.CurrencyValueFormatter

class BudgetedAdapter(val context: Context, val categoryList: List<BudgetedCategoriesRequest>) : RecyclerView.Adapter<BudgetedAdapter.ViewHolder>() {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemView = LayoutInflater.from(context).inflate(R.layout.budgeted_design,parent,false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
       return  categoryList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem:BudgetedCategoriesRequest = categoryList[position]
        val valueFormatter = CurrencyValueFormatter()

        val formattedLimit = valueFormatter.getFormattedValue(currentItem.Limit.toFloat())
        val formattedSpent = valueFormatter.getFormattedValue(currentItem.Spent.toFloat())
        val formattedRemaining = valueFormatter.getFormattedValue(currentItem.remaining.toFloat())

        holder.tvcategoryName.text =  currentItem.categoryName
        holder.Limit.text =formattedLimit
        holder.firstLetter.text = currentItem.categoryName.first().toString()
        holder.spent.text = formattedSpent
        holder.remaining.text = formattedRemaining

    }



    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){

        val tvcategoryName : TextView = itemView.findViewById(R.id.tvCategoryName)
        val firstLetter : TextView = itemView.findViewById(R.id.tvcategoryFirstLetter)
        val Limit : TextView = itemView.findViewById(R.id.tvValueLimit)
        val spent : TextView = itemView.findViewById(R.id.tvValueSpent)
        val remaining : TextView = itemView.findViewById(R.id.tvValueRemaining)

    }
}