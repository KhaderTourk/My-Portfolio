package com.example.finaldemo.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.finaldemo.R
import com.example.finaldemo.SearshScreen
import com.example.finaldemo.models.Product
import java.util.ArrayList

/**
 * Created by Parsania Hardik on 26-Jun-17.
 */
class SearchAdapter(ctx: Context, private val imageModelArrayList: ArrayList<Product>) :
    RecyclerView.Adapter<SearchAdapter.MyViewHolder>() {

    private val inflater: LayoutInflater
    private val arraylist: ArrayList<Product>

    init {

        inflater = LayoutInflater.from(ctx)
        this.arraylist = ArrayList<Product>()
        this.arraylist.addAll(SearshScreen.usersArray)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchAdapter.MyViewHolder {

        val view = inflater.inflate(R.layout.product_item, parent, false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchAdapter.MyViewHolder, position: Int) {

        holder.name.text = imageModelArrayList[position].name
        holder.phone.text = imageModelArrayList[position].price
        holder.img.setImageResource(imageModelArrayList[position].img)
    }

    override fun getItemCount(): Int {
        return imageModelArrayList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var name: TextView
        var phone: TextView
        var img: ImageView

        init {
            name = itemView.findViewById(R.id.product_name) as TextView
            phone = itemView.findViewById(R.id.product_price) as TextView
            img = itemView.findViewById(R.id.imageView) as ImageView
        }

    }
}