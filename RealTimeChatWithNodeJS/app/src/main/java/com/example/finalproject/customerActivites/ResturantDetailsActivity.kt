package com.example.finalproject.customerActivites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.example.finalproject.Activites.ComanionClass
import com.example.finalproject.Activites.MapsActivity
import com.example.finalproject.R
import com.example.finalproject.adminActivites.AdminMealsActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_resturant_details.*
import kotlinx.android.synthetic.main.resturant_item_admin.tv_resurantName

class ResturantDetailsActivity : AppCompatActivity() {

    lateinit var db: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resturant_details)
        db = Firebase.firestore

        location.setOnClickListener {
            val intent = Intent(this , MapsActivity::class.java)
            startActivity(intent)
        }
        tv_resurantName.text = ComanionClass.resturantName
        rat_bar.rating = ComanionClass.resturantRate.toFloat()
        // image_car.setImageResource(getIntent().getStringExtra("CARLOGO").toInt())


        btn_Menu.setOnClickListener {
            if (ComanionClass.user == 1){
            val intent = Intent(this , AdminMealsActivity::class.java)
            startActivity(intent)
        }else if (ComanionClass.user == 2){
                val intent = Intent(this , CustomerMealsActivity::class.java)
                startActivity(intent)
            }
        }
        rat_bar.setOnRatingBarChangeListener { _, rating, _ ->

        db.collection("Resturants").whereEqualTo("Name", ComanionClass.resturantName).get()
            .addOnSuccessListener { querySnapshot ->
                db.collection("Resturants").document(querySnapshot.documents[0].id)
                    .update("Rate", rating.toString() ).addOnCompleteListener {
                        Log.e("rate" , rating.toString())
                    }

            }.addOnFailureListener { exception ->

            }
    }}

    override fun onStart() {
        super.onStart()
        Glide.with(this).load(ComanionClass.resturantImage).into(img_Details)
        Glide.with(this).load(ComanionClass.resturantImageM).into(btn_Menu)

    }
}
