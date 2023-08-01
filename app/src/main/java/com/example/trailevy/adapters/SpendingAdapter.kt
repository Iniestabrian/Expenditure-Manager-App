package com.example.trailevy.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.trailevy.R
import com.example.trailevy.data.CategoriesRequest

class SpendingAdapter(val context: Context,val categoryList: List<CategoriesRequest>):
    RecyclerView.Adapter<SpendingAdapter.ViewHolder>() {

    private var listener: spendingItemClick_Listener? = null

    fun setOnItemClickListener(listener:spendingItemClick_Listener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpendingAdapter.ViewHolder {
        var itemView = LayoutInflater.from(context).inflate(R.layout.spending_category_design,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem : CategoriesRequest = categoryList[position]
        holder.tvcategoryName.text = currentItem.categoryName.toString()
        holder.firstLetter.text = currentItem.categoryName.first().toString()



    }

    override fun getItemCount(): Int {
        return categoryList.size
    }



    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        val tvcategoryName : TextView = itemView.findViewById(R.id.tvCategoryName)
        val firstLetter : TextView = itemView.findViewById(R.id.tvcategoryFirstLetter)



        init {
            itemView.setOnClickListener {
                listener?.onItemClick(adapterPosition)
            }
        }
    }


}