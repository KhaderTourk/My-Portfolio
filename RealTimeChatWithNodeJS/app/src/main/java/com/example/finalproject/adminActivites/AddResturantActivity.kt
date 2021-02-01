package com.example.finalproject.adminActivites

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.example.finalproject.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_add.*
import www.sanju.motiontoast.MotionToast

class AddResturantActivity : AppCompatActivity() {

    lateinit var db:FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        db = Firebase.firestore

        add_button.setOnClickListener {
            addResturantToDB(et_name_add.text.toString(),et_address_add.text.toString() , et_lat_add.text.toString(), et_lat_add.text.toString())
        }

    }
    private fun addResturantToDB(name: String, address: String , lat:String , long:String) {

        val resturant = hashMapOf("Name" to name, "Address" to address, "Lat" to lat, "Long" to long)
        db.collection("Resturants").add(resturant)
            .addOnSuccessListener { documentReference ->
                MotionToast.createColorToast(
                    this, "Add successfully",
                    MotionToast.TOAST_SUCCESS,
                    MotionToast.GRAVITY_BOTTOM,
                    MotionToast.LONG_DURATION,
                    ResourcesCompat.getFont(
                        this@AddResturantActivity,
                        www.sanju.motiontoast.R.font.helvetica_regular
                    )
                )
                val intent = Intent(this,AdminResturantActivity::class.java)
                startActivity(intent)
            }
            .addOnFailureListener { exception ->
                MotionToast.createColorToast(
                    this, "Add Fal",
                    MotionToast.TOAST_ERROR,
                    MotionToast.GRAVITY_BOTTOM,
                    MotionToast.LONG_DURATION,
                    ResourcesCompat.getFont(
                        this@AddResturantActivity,
                        www.sanju.motiontoast.R.font.helvetica_regular
                    )
                )
            }
    }

}