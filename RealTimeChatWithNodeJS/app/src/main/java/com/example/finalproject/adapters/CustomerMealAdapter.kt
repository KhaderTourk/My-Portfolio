package com.example.finalproject.adapters

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalproject.R
import com.example.finalproject.model.Meal
import kotlinx.android.synthetic.main.a_m_item.view.*

class CustomerMealAdapter (val context: Context, val list: java.util.ArrayList<Meal>, val activity: Activity, var clickListner: OnMealItemClickListner) :
    RecyclerView.Adapter<CustomerMealAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.a_m_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.initialize(list[position],clickListner)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.tv_A_M_N
        val img = itemView.img_A_M
        val price = itemView.tv_A_M_price
        val mainLayout = itemView.mainLayout_M
        init {
            //Animate Recyclerview
            val translate_anim =
                AnimationUtils.loadAnimation(context,
                    R.anim.translate_anim
                )
            mainLayout.animation = translate_anim
        }
        fun initialize(item: Meal, action: OnMealItemClickListner){
            name.text = item.mealName
            price.text = item.mealPrice.toString()
           // Glide.with(context).load(item.image).into(img)
            //img.setImageResource(item.mealImage.toInt())

            itemView.setOnClickListener{
                action.onItemClick(item,adapterPosition)
            }

        }
    }
    interface OnMealItemClickListner{
        fun onItemClick(item: Meal, position: Int)
    }



}
