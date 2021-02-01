package com.example.finalproject.adminActivites

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.example.finalproject.Activites.ComanionClass
import com.example.finalproject.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_update.*
import www.sanju.motiontoast.MotionToast


class UpdateResturantActivity : AppCompatActivity() {

    var db: FirebaseFirestore? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        db = Firebase.firestore

        et_U_R_name.setText(ComanionClass.resturantName)
        et_U_R_address.setText(ComanionClass.resturantAddress)
        et_lat_add.setText(ComanionClass.lat)
        et_long_add.setText(ComanionClass.long)

        btn_U_R.setOnClickListener {
            MotionToast.createColorToast(
                this, "Not work yet.",
                MotionToast.TOAST_INFO,
                MotionToast.GRAVITY_BOTTOM,
                MotionToast.LONG_DURATION,
                ResourcesCompat.getFont(
                    this,
                    www.sanju.motiontoast.R.font.helvetica_regular
                )
            )
            val intent = Intent(this,AdminResturantActivity::class.java)
            startActivity(intent)
        }
        btn_D_R.setOnClickListener { confirmDialog() }
    }

    fun confirmDialog() {
        val builder =
            AlertDialog.Builder(this)
        builder.setTitle("Delete $title ?")
        builder.setMessage("Are you sure you want to delete $title ?")
        builder.setPositiveButton("Yes") { dialogInterface, i ->
        db!!.collection("Resturants").document(ComanionClass.resturantID).delete().addOnSuccessListener {
            MotionToast.createColorToast(
                this, "Upload Completed!",
                MotionToast.TOAST_SUCCESS,
                MotionToast.GRAVITY_BOTTOM,
                MotionToast.LONG_DURATION,
                ResourcesCompat.getFont(
                    this,
                    www.sanju.motiontoast.R.font.helvetica_regular
                )
            )
                val intent = Intent(this , AdminResturantActivity::class.java)
                startActivity(intent)
        }
        }
        builder.setNegativeButton("No") { dialogInterface, i -> }
        builder.create().show()
    }
}