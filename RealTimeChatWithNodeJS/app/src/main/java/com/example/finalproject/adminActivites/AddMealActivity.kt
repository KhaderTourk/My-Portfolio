package com.example.finalproject.adminActivites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.res.ResourcesCompat
import com.example.finalproject.Activites.ComanionClass
import com.example.finalproject.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.activity_add_meal.*
import www.sanju.motiontoast.MotionToast

class AddMealActivity : AppCompatActivity() {

    lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_meal)

        db = Firebase.firestore

        add_button_A_M.setOnClickListener {
            addMealToDB(et_A_M_N.text.toString(),et_A_M_description.text.toString() , et_A_M_price.text.toString())
        }

    }
    private fun addMealToDB(name: String, description: String , price : String ) {

        val meal = hashMapOf("Name" to name, "Description" to description , "Price" to price , "Rate" to "3.0")
        db.collection("Resturants").document(ComanionClass.resturantID).collection("Meal")
            .add(meal)
            .addOnSuccessListener { documentReference ->
                MotionToast.createColorToast(
                    this, "Add Successfully",
                    MotionToast.TOAST_SUCCESS,
                    MotionToast.GRAVITY_BOTTOM,
                    MotionToast.LONG_DURATION,
                    ResourcesCompat.getFont(
                        this,
                        www.sanju.motiontoast.R.font.helvetica_regular
                    )
                )
                val intent = Intent(this , AdminMealsActivity::class.java)
                startActivity(intent)
            }
            .addOnFailureListener { exception ->
                MotionToast.createColorToast(
                    this, "Add Fal",
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