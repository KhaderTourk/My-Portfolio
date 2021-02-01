package com.example.finaldemo.screens.signingscreens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.finaldemo.MainActivity
import com.example.finaldemo.R

class SignUpScreen : AppCompatActivity() {

    lateinit var btn_si:TextView
    lateinit var btn_su:TextView
    lateinit var et_name:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_screen)
        supportActionBar?.hide()

        btn_si = findViewById(R.id.btn_in_u)
        btn_su = findViewById(R.id.btn_up_u)
        et_name = findViewById(R.id.user_name_su)


        val intent : Intent = Intent(this , SignInScreen::class.java)


        btn_si.setOnClickListener {
            val userName = et_name.text.toString()
            if(userName != ""){
                intent.putExtra("name",userName)
            }
            startActivity(intent)
        }
        btn_su.setOnClickListener {
            val userName = et_name.text.toString()
            if(userName != ""){
                intent.putExtra("name",userName)
            }
            startActivity(intent)
        }
    }
}