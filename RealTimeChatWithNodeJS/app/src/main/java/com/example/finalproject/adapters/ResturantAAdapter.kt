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
import com.example.finalproject.model.Resturant
import kotlinx.android.synthetic.main.resturant_item_admin.view.*

class ResturantAAdapter (val context: Context, val list: java.util.ArrayList<Resturant>, val activity: Activity, var clickListner: OnRestItemClickListner) :
    RecyclerView.Adapter<ResturantAAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.resturant_item_admin, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.initialize(list[position],clickListner)
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
            //img.setImageResource(item.resturantImage.toInt())

            itemView.setOnClickListener{
                action.onItemClick(item,adapterPosition)
            }

        }
    }
    interface OnRestItemClickListner{
        fun onItemClick(item: Resturant, position: Int)
    }



}
