package com.example.trailevy.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.trailevy.R
import com.example.trailevy.data.NotBudgetedCategories

class NotBudgetedAdapter(val context: Context, val categoryList: List<NotBudgetedCategories>, private val onButtonClickListener: SetLimitClickListener )
    : RecyclerView.Adapter<NotBudgetedAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemView = LayoutInflater.from(context).inflate(R.layout.not_budgeted_design,parent,false)
        return NotBudgetedAdapter.ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
       return categoryList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem : NotBudgetedCategories = categoryList[position]
        holder.tvcategoryName.text  = currentItem.categoryName.toString()
        holder.firstLetter.text = currentItem.categoryName.first().toString()


        holder.btnSetLimit.setOnClickListener {
            onButtonClickListener.onButtonClick(currentItem)

        }



    }




    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val tvcategoryName : TextView = itemView.findViewById(R.id.tvCategoryName)
        val firstLetter : TextView = itemView.findViewById(R.id.tvcategoryFirstLetter)
        val btnSetLimit : Button = itemView.findViewById(R.id.btn_setLimit)
    }




    interface SetLimitClickListener {

        fun onButtonClick(category: NotBudgetedCategories)


    }



}
