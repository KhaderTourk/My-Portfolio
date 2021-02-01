package com.example.finaldemo.screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.finaldemo.MainActivity
import com.example.finaldemo.R

class AddProduct : AppCompatActivity() {

    lateinit var et_p_name:EditText
    lateinit var et_p_price:EditText
    lateinit var et_p_desc:EditText
    lateinit var btn_add_p:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)
        supportActionBar?.hide()

        et_p_name = findViewById(R.id.et_add_p_name)
        et_p_price = findViewById(R.id.et_add_p_price)
        et_p_desc = findViewById(R.id.et_add_p_desc)
        btn_add_p = findViewById(R.id.btn_add_p)

        btn_add_p.setOnClickListener {
            val intent = Intent(this , MainActivity::class.java)
            if (et_p_name.text.toString().isNotEmpty() && et_p_price.text.toString().isNotEmpty() &&et_p_desc.text.toString().isNotEmpty() ){
                intent.putExtra("add_new_product_name",et_p_name.text.toString())
                intent.putExtra("add_new_product_price",(et_p_price.text.toString()+"$"))
                intent.putExtra("add_new_product_desc",et_p_desc.text.toString())
            }
            startActivity(intent)
        }
    }
}