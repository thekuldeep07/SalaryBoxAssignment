package com.example.assignmentsalarybox.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.assignmentsalarybox.R
import com.example.assignmentsalarybox.data.Store

const val TYPE_ITEM = 2
const val TYPE_FOOTER = 1

class RecyclerViewAdapter(
    private val context: Context,
    private var data: Store = Store()
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder {
        return if (viewType == TYPE_FOOTER) {
            FooterViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_footer, parent, false)
            )

        } else {
            val view: View = LayoutInflater.from(context).inflate(R.layout.food_item, parent, false)
            return DataHolder(view)
        }

    }

    override fun getItemCount(): Int {
        return data.foods.size + 1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is DataHolder) {
            val dataMap = data.foods[position]
            holder.food_name.text = dataMap.name
            holder.food_price.text = "₹${dataMap.price}"
            holder.foodDescription.text = dataMap.desc
            holder.foodImage.setImageResource(dataMap.image)
            holder.typeImageIcon.setImageResource(if (dataMap.type == "veg") R.drawable.veg_icon else R.drawable.non_veg_icon)
            holder.bestSellerIcon.visibility =
                if (dataMap.bestSellerFlag == true) View.VISIBLE else View.INVISIBLE
        } else if (holder is FooterViewHolder) {
        }

    }

    override fun getItemViewType(position: Int): Int {
        return if (position == data.foods.size) {
            TYPE_FOOTER
        } else {
            TYPE_ITEM
        }
    }

    class DataHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var food_name: TextView
        var food_price: TextView
        var typeImageIcon: ImageView
        var foodImage: ImageView
        var bestSellerIcon: TextView
        var foodDescription: TextView

        init {
            food_name = itemView.findViewById<View>(R.id.food_name) as TextView
            food_price = itemView.findViewById<View>(R.id.food_price) as TextView
            typeImageIcon = itemView.findViewById<View>(R.id.type_icon) as ImageView
            bestSellerIcon = itemView.findViewById<View>(R.id.bestSellerIcon) as TextView
            foodDescription = itemView.findViewById<View>(R.id.food_description) as TextView
            foodImage = itemView.findViewById<View>(R.id.food_image) as ImageView
        }
    }

    class FooterViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }

    fun changeData(newData: Store) {
        data = newData
        notifyDataSetChanged()
    }

}