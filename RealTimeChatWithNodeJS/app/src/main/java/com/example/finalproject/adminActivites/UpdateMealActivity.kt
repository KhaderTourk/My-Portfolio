package com.example.finalproject.adminActivites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.core.content.res.ResourcesCompat
import com.example.finalproject.Activites.ComanionClass
import com.example.finalproject.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_update_meal.*
import www.sanju.motiontoast.MotionToast

class UpdateMealActivity : AppCompatActivity() {

    lateinit var db:FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_meal)

        db = Firebase.firestore

        et_U_M_name.setText(ComanionClass.mealName)
        et_U_M_description.setText(ComanionClass.mealDescription)
        et_U_M_price.setText(ComanionClass.mealPrice)

        btn_U_M.setOnClickListener {
            MotionToast.createColorToast(
                this, "Not work now.",
                MotionToast.TOAST_INFO,
                MotionToast.GRAVITY_BOTTOM,
                MotionToast.LONG_DURATION,
                ResourcesCompat.getFont(
                    this,
                    www.sanju.motiontoast.R.font.helvetica_regular
                )
            )
            val intent = Intent(this,AdminMealsActivity::class.java)
            startActivity(intent)
        }

        btn_D_M.setOnClickListener {
            confirmDialog()
        }
    }
    fun confirmDialog() {
        val builder =
            AlertDialog.Builder(this)
        builder.setTitle("Delete $title ?")
        builder.setMessage("Are you sure you want to delete $title ?")
        builder.setPositiveButton("Yes") { dialogInterface, i ->

            db.collection("Resturants").document(ComanionClass.resturantID)
                .collection("Meal").whereEqualTo("Name" , ComanionClass.mealName)
                .get().addOnSuccessListener {snapshot ->

                    db.collection("Resturants").document(ComanionClass.resturantID)
                        .collection("Meal").document(snapshot.documents[0].id)
                        .delete()
                        .addOnSuccessListener {
                            MotionToast.createColorToast(
                                this, "Delete Successfully.",
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
                }.addOnFailureListener { exception ->

                }

        }
        builder.setNegativeButton("No") { dialogInterface, i -> }
        builder.create().show()
    }
}
