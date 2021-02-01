package com.example.finaldemo.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.example.finaldemo.R
import de.hdodenhof.circleimageview.CircleImageView

class ProductDetails : AppCompatActivity() {

    lateinit var img:CircleImageView
    lateinit var tv_name:TextView
    lateinit var tv_price:TextView
    lateinit var tv_desc:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)
        supportActionBar?.hide()

        img = findViewById(R.id.img_p_details)
        tv_name = findViewById(R.id.tv_p_name)
        tv_price = findViewById(R.id.tv_p_price)
        tv_desc = findViewById(R.id.tv_p_desc)


        val image = intent.getIntExtra("P_Image",R.drawable.b1)
        val name = intent.getStringExtra("P_Name")
        val price = intent.getStringExtra("P_Price")
        if (name != null){
          img.setImageResource(image)
            tv_name.setText(name)
            tv_price.setText(price)
        }
    }
}