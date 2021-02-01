package com.example.finalproject.Activites

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import com.example.finalproject.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.vansuita.pickimage.bean.PickResult
import com.vansuita.pickimage.bundle.PickSetup
import com.vansuita.pickimage.dialog.PickImageDialog
import com.vansuita.pickimage.listeners.IPickResult
import kotlinx.android.synthetic.main.activity_edit_profile.*
import www.sanju.motiontoast.MotionToast
import java.util.*

class EditProfileActivity : AppCompatActivity(), IPickResult {

    var storage: FirebaseStorage? = null
    var reference: StorageReference? = null
    lateinit var auth: FirebaseAuth
    var db: FirebaseFirestore? = null
    lateinit var progressDialog: ProgressDialog
    var path: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        db = Firebase.firestore
        auth = Firebase.auth
        storage = Firebase.storage
        reference = storage!!.reference
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("جاري التحميل")
        progressDialog.setCancelable(false)
        getProfileData()

        image_edit_profile.setOnClickListener {
            PickImageDialog.build(PickSetup()).show(this)
        }

        btn_save.setOnClickListener {
            updateImage(path)
        }

    }

    private fun updateImage(path: String?) {
        db!!.collection("users").whereEqualTo("email", auth.currentUser!!.email).get()
            .addOnSuccessListener { querySnapshot ->
                db!!.collection("users").document(querySnapshot.documents[0].id)
                    .update("image", path )
                ComanionClass.userId = db!!.collection("users").document(querySnapshot.documents[0].id).id

                MotionToast.createColorToast(
                    this, "Edit Successfully.",
                    MotionToast.TOAST_SUCCESS,
                    MotionToast.GRAVITY_BOTTOM,
                    MotionToast.LONG_DURATION,
                    ResourcesCompat.getFont(
                        this,
                        www.sanju.motiontoast.R.font.helvetica_regular
                    )
                )
                val intent = Intent(this , ProfileActivity::class.java)
                startActivity(intent)
            }.addOnFailureListener { exception ->

                MotionToast.createColorToast(
                    this, "Edit Fail.",
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

    fun getProfileData() {
        db!!.collection("users").whereEqualTo("email",auth.currentUser!!.email).get()
            .addOnSuccessListener { querySnapshot ->
                ed_name.setText(querySnapshot.documents[0].get("name").toString())
                ed_phone.setText(querySnapshot.documents[0].get("phone").toString())
                ed_address.setText(querySnapshot.documents[0].get("address").toString())
                ed_email.setText(auth.currentUser!!.email)
            }
    }

    override fun onPickResult(r: PickResult?) {
        image_edit_profile.setImageBitmap(r!!.bitmap)
        ComanionClass.userImageUri = r.uri.toString()
        uploadImage(r.uri)
    }

    private fun uploadImage(uri: Uri?) {
        progressDialog.show()
        reference!!.child("profile/" + UUID.randomUUID().toString()).putFile(uri!!)
            .addOnSuccessListener { taskSnapshot ->

                taskSnapshot.storage.downloadUrl.addOnSuccessListener { uri ->
                    path = uri.toString()
                }
                progressDialog.dismiss()

                MotionToast.createColorToast(
                    this, "Image Uploaded Successfully.",
                    MotionToast.TOAST_SUCCESS,
                    MotionToast.GRAVITY_BOTTOM,
                    MotionToast.LONG_DURATION,
                    ResourcesCompat.getFont(
                        this,
                        www.sanju.motiontoast.R.font.helvetica_regular
                    )
                )
            }.addOnFailureListener { exception ->
                MotionToast.createColorToast(
                    this, "Image upload fial.",
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
