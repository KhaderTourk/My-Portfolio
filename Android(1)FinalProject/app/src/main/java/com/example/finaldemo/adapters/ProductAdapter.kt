package com.example.finaldemo.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.finaldemo.R
import com.example.finaldemo.models.Product

class ProductAdapter(private val context: Context, private val list: MutableList<Product>, var click:onClick)
    :RecyclerView.Adapter<ProductAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.product_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = list[position].name
        holder.price.text = list[position].price
        holder.img.setImageResource(list[position].img)

        holder.card.setOnClickListener {
            click.onClickItem(holder.adapterPosition)
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(item : View):RecyclerView.ViewHolder(item){
        var card: CardView = item.findViewById(R.id.item_card)
        var img: ImageView = item.findViewById(R.id.imageView)
        var name: TextView = item.findViewById(R.id.product_name)
        var price: TextView = item.findViewById(R.id.product_price)
    }

    interface onClick{
        fun onClickItem(position: Int)
    }
}