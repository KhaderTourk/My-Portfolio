package com.example.finalproject.Activites

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.activity_sign_up.ed_address
import kotlinx.android.synthetic.main.activity_sign_up.ed_email
import kotlinx.android.synthetic.main.activity_sign_up.ed_name
import kotlinx.android.synthetic.main.activity_sign_up.ed_phone
import www.sanju.motiontoast.MotionToast
import java.util.*


class SignUpActivity : AppCompatActivity(), IPickResult {

    private var fileURI: Uri? = null

    val TAG = "kad"

    var storage: FirebaseStorage? = null
    var reference: StorageReference? = null
    private lateinit var auth: FirebaseAuth
    private var db: FirebaseFirestore? = null
    lateinit var progressDialog: ProgressDialog
    var path: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        db = Firebase.firestore
        auth = Firebase.auth
        storage = Firebase.storage
        reference = storage!!.reference
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("جاري التحميل")
        progressDialog.setCancelable(false)

        addImage.setOnClickListener {
            PickImageDialog.build(PickSetup()).show(this)
        }

        btn_signup.setOnClickListener {
            showDialog()

            if (ed_email.text.toString().contains("@cost") || ed_email.text.toString().contains("@admn")) {
                createNewAccount(ed_email.text.toString(), ed_pass.text.toString())
                    hideDialog()
                MotionToast.createColorToast(
                    this, "SignUp Successfully.",
                    MotionToast.TOAST_SUCCESS,
                    MotionToast.GRAVITY_BOTTOM,
                    MotionToast.LONG_DURATION,
                    ResourcesCompat.getFont(
                        this,
                        www.sanju.motiontoast.R.font.helvetica_regular
                    )
                )
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
            }else{
                MotionToast.createColorToast(
                    this, "Email should have ( @cost or @admn )",
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

    private fun createNewAccount(email: String, pass: String) {
        auth.createUserWithEmailAndPassword(email, pass).addOnSuccessListener { task ->
             val user = auth.currentUser
                Log.e("TestAuthn", "user ${user!!.uid} + ${user.email}")
                addUser(
                    user.uid,
                    ed_name.text.toString(),
                    user.email!!,
                    ed_phone.text.toString(),
                    ed_address.text.toString()
                )
        }.addOnFailureListener { exception ->
        }
    }
    private fun addUser(id: String, name: String, email: String, phone: String, address: String ) {
        val user =
            hashMapOf(
                "id" to id,
                "name" to name,
                "email" to email,
                "phone" to phone,
                "address" to address,
                "image" to fileURI.toString()
            )

        db!!.collection("users").add(user)

    }
    override fun onPickResult(r: PickResult?) {
        addImage.setImageBitmap(r!!.bitmap)
        fileURI = r.uri
        ComanionClass.userImageUri = r.uri.toString()
        uploadImage(r.uri)
    }
    private fun uploadImage(uri: Uri?) {
        progressDialog.show()
        reference!!.child("profile/" + UUID.randomUUID().toString()).putFile(uri!!)
            .addOnSuccessListener { taskSnapshot ->
                progressDialog.dismiss()
                taskSnapshot.storage.downloadUrl.addOnSuccessListener { uri ->
                    path = uri.toString()
                }
                MotionToast.createColorToast(
                    this, "Successfully.",
                    MotionToast.TOAST_SUCCESS,
                    MotionToast.GRAVITY_BOTTOM,
                    MotionToast.LONG_DURATION,
                    ResourcesCompat.getFont(
                        this,
                        www.sanju.motiontoast.R.font.helvetica_regular
                    )
                )
            }.addOnFailureListener { exception ->
            }
    }

    private fun showDialog() {
        progressDialog = ProgressDialog(MainActivity@this)
        progressDialog.setMessage("Uploading image ...")
        progressDialog.setCancelable(false)
        progressDialog.show()
    }
    private fun hideDialog(){
        if(progressDialog.isShowing)
            progressDialog.dismiss()
    }
}
