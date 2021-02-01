package com.example.finalproject.Activites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.res.ResourcesCompat
import com.example.finalproject.customerActivites.CustomerResturantActivity
import com.example.finalproject.R
import com.example.finalproject.adminActivites.AdminResturantActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import www.sanju.motiontoast.MotionToast

class MainActivity : AppCompatActivity() {
    lateinit var db: FirebaseFirestore
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = Firebase.firestore
        auth = Firebase.auth

        buttonLogin.setOnClickListener{
            LogInAccount(et_name.text.toString(), et_password.text.toString())
        }
        btn_signup.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

    }
    private fun LogInAccount(email: String, pass: String) {
        auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser
                Log.e("TestAuthn", "user ${user!!.uid} + ${user.email}")
                if (user.email!!.contains("@cost")){
                val intent = Intent(this, CustomerResturantActivity::class.java)
                    ComanionClass.user = 2
                startActivity(intent)
                }else if (user.email!!.contains("@admn")){
                    val intent = Intent(this, AdminResturantActivity::class.java)
                    ComanionClass.user = 1
                    startActivity(intent)
                }

            } else {
                MotionToast.createColorToast(
                    this, "Email or password error.",
                    MotionToast.TOAST_ERROR,
                    MotionToast.GRAVITY_BOTTOM,
                    MotionToast.LONG_DURATION,
                    ResourcesCompat.getFont(
                        this,
                        www.sanju.motiontoast.R.font.helvetica_regular
                    )
                )

            }
        }
    }


}
