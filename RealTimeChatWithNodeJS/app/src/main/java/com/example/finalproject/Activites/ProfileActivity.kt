package com.example.finalproject.Activites

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.finalproject.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    lateinit var db: FirebaseFirestore
    lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        db = Firebase.firestore
        auth = Firebase.auth
        getProfileData()
        btn_edit.setOnClickListener {
            val intent = Intent(this , EditProfileActivity::class.java)
            startActivity(intent)
        }

    }
    private fun getProfileData() {
        showDialog()
        db.collection("users").whereEqualTo("email", auth.currentUser!!.email).get()
            .addOnSuccessListener { querySnapshot ->
                db.collection("users").document(ComanionClass.userId).get()
                    .addOnSuccessListener {
                        for (document in querySnapshot){
                            if (document.getString("email") == auth.currentUser!!.email){
                                tv_name.text = document.get("name").toString()
                                tv_phone.text = document.get("phone").toString()
                                tv_address.text = document.get("address").toString()
                                Picasso.get()
                                    .load(ComanionClass.userImageUri)
                                    .placeholder(R.drawable.ic_empty)
                                    .error(R.drawable.ic_error_)
                                    .into(image_profile)
                                tv_email.text = auth.currentUser!!.email
                                Log.e("hzm","exception.message")
                            }
                        }
                    hideDialog()
                }
            }.addOnFailureListener { exception ->

            }
    }
    private fun showDialog() {
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Uploading image ...")
        progressDialog.setCancelable(false)
        progressDialog.show()
    }
    private fun hideDialog(){
        if(progressDialog.isShowing)
            progressDialog.dismiss()
    }
}
