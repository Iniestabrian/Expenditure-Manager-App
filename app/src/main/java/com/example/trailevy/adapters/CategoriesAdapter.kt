package com.example.trailevy.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.trailevy.R
import com.example.trailevy.data.CategoriesRequest

class CategoriesAdapter(val context: Context, val categoryList: List<CategoriesRequest>) : RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemView = LayoutInflater.from(context).inflate(R.layout.categories_design,parent,false)
        return  ViewHolder(itemView)

    }

    override fun getItemCount(): Int {

        return  categoryList.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem : CategoriesRequest = categoryList[position]
        holder.tvcategoryName.text  = currentItem.categoryName.toString()
        holder.firstLetter.text = currentItem.categoryName.first().toString()

    }





    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvcategoryName : TextView = itemView.findViewById(R.id.tvCategoryName)
        val firstLetter : TextView = itemView.findViewById(R.id.tvcategoryFirstLetter)

    }


}