package com.example.finalproject.adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalproject.Activites.ComanionClass
import com.example.finalproject.adminActivites.UpdateResturantActivity
import com.example.finalproject.R
import com.example.finalproject.model.Resturant
import kotlinx.android.synthetic.main.resturant_item_admin.view.*
import java.util.*

class CustomAdapter (val context: Context, val list: ArrayList<Resturant>, val activity: Activity, var clickListner: OnRestItemClickListner) :
    RecyclerView.Adapter<CustomAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.resturant_item_admin, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.initialize(list[position],clickListner)

            //Recyclerview onClickListener
            holder.mainLayout.setOnLongClickListener {
                val intent = Intent(context, UpdateResturantActivity::class.java)
                intent.putExtra("name", list[position].toString())
                intent.putExtra("address", list[position].toString())
                activity.startActivityForResult(intent, 1)
                return@setOnLongClickListener false
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

     inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
         val name = itemView.tv_resurantName
         val img = itemView.img_admin_resturants
         val mainLayout = itemView.mainLayout
        init {
            //Animate Recyclerview
            val translate_anim =
                AnimationUtils.loadAnimation(context,
                    R.anim.translate_anim
                )
            mainLayout.animation = translate_anim
        }
         fun initialize(item: Resturant, action: OnRestItemClickListner){
             name.text = item.resturantName
             Glide.with(context).load(item.image).into(img)

             itemView.setOnClickListener{
                 action.onItemClick(item,adapterPosition)
             }

         }
    }
    interface OnRestItemClickListner{
        fun onItemClick(item: Resturant, position: Int)
    }


}