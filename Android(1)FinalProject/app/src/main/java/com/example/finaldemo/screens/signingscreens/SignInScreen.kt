package com.example.finaldemo.screens.signingscreens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.finaldemo.MainActivity
import com.example.finaldemo.R

class SignInScreen : AppCompatActivity() {

     var btn_si : TextView? = null
     var btn_su : TextView? = null
    lateinit var et_name: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in_screen)
        supportActionBar?.hide()


        btn_si = findViewById(R.id.btn_in_i)
        btn_su = findViewById(R.id.btn_up_i)
        et_name = findViewById(R.id.user_name_si)

        val name = intent.getStringExtra("name")
        if (name != null){
            et_name.setText(name)
        }

        var intent : Intent
        btn_si!!.setOnClickListener {
            intent = Intent(this , MainActivity::class.java)
            startActivity(intent)
        }
        btn_su!!.setOnClickListener {
            intent = Intent(this , SignUpScreen::class.java)
            startActivity(intent)
        }

    }
}