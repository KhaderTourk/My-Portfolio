package com.example.finalproject.customerActivites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.finalproject.Activites.ComanionClass
import com.example.finalproject.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_meal_details.*

class MealDetailsActivity : AppCompatActivity() {

    lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meal_details)

        db = Firebase.firestore

        tv_M_price.text = ComanionClass.mealPrice
        rb_M_details.rating = ComanionClass.mealRate.toFloat()
        tv_M_N.text = ComanionClass.mealName
        tv_M_description.text = ComanionClass.mealDescription
        image_M_detail

        rb_M_details.setOnRatingBarChangeListener { _, rating, _ ->

            db.collection("Resturants").document(ComanionClass.resturantID)
                .collection("Meal").whereEqualTo("Name" , ComanionClass.mealName)
                .get().addOnSuccessListener {snapshot ->

                    db.collection("Resturants").document(ComanionClass.resturantID)
                        .collection("Meal").document(snapshot.documents[0].id)
                        .update("Rate", rating.toString() )
                        .addOnCompleteListener {
                            Log.e("rate" , rating.toString())
                        }

                }.addOnFailureListener { exception ->

                }
        }
    }
}
