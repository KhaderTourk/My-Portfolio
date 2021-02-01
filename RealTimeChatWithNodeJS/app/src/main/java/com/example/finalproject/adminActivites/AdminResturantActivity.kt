package com.example.finalproject.adminActivites

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalproject.Activites.ComanionClass
import com.example.finalproject.Activites.DashboardActivity
import com.example.finalproject.Activites.ProfileActivity
import com.example.finalproject.adapters.CustomAdapter
import com.example.finalproject.R
import com.example.finalproject.customerActivites.ResturantDetailsActivity
import com.example.finalproject.model.Resturant
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_admin_resturant.*
import kotlinx.android.synthetic.main.activity_admin_resturant.add_button

class AdminResturantActivity : AppCompatActivity(),
    CustomAdapter.OnRestItemClickListner {
    private lateinit var customAdapter: CustomAdapter

    lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_resturant)

        db = Firebase.firestore
        ComanionClass.resturants.clear()
        if (ComanionClass.resturants.isEmpty()) {
            getAllUsers()
        }

        add_button.setOnClickListener {
            val intent = Intent(this@AdminResturantActivity, AddResturantActivity::class.java)
            startActivity(intent)
        }

        customAdapter = CustomAdapter(
            this,
            ComanionClass.resturants,
            this,
            this
        )

        recyclerView?.adapter = customAdapter
        recyclerView?.layoutManager = LinearLayoutManager(this@AdminResturantActivity)
    }

    private fun getAllUsers() {

        db.collection("Resturants")
            .get()
            .addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot) {
                    val name: String = document.getString("Name")!!
                    val address: String = document.getString("Address")!!
                    val lat: String = document.getString("Lat")!!
                    val long: String = document.getString("Long")!!
                    val imgm: String = document.getString("ImageM")!!
                    val img: String = document.getString("Image")!!
                    if (document.getString("Rate") == null || document.getString("Rate") == ""){
                        val p = Resturant(name, 3.3, address,lat,long,img)
                        ComanionClass.resturants.add(p)
                    }else{
                        val rate: String = document.getString("Rate")!!
                        val p = Resturant(name, rate.toDouble(), address,lat,long,img)
                        ComanionClass.resturants.add(p)
                    }
                }
                customAdapter.notifyDataSetChanged()
            }.addOnFailureListener { exception ->
                Log.e("ttr", exception.message!!)
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            recreate()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.my_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.Dashboard) {
            val intent = Intent(
                this,
                DashboardActivity::class.java
            )
            startActivity(intent)
            //  confirmDialog()
        } else if (item.itemId == R.id.Profile) {
            val intent = Intent(
                this,
                ProfileActivity::class.java
            )
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onItemClick(item: Resturant, position: Int) {
        ComanionClass.resturantName = item.resturantName
        ComanionClass.resturantAddress = item.resturantAddress
        ComanionClass.resturantRate = item.resturantRate.toString()
        ComanionClass.lat = item.lat
        ComanionClass.long = item.long
       // ComanionClass.resturantID = item.resturantID
        db.collection("Resturants").whereEqualTo("Name" , item.resturantName)
            .get() .addOnSuccessListener { querySnapshot ->
                ComanionClass.resturantID = querySnapshot.documents[0].id
                ComanionClass.resturantImageM = querySnapshot.documents[0].getString("ImageM").toString()
                ComanionClass.resturantImage = querySnapshot.documents[0].getString("Image").toString()
            }

        val intent = Intent(this, ResturantDetailsActivity::class.java)
        intent.putExtra("resurantName", item.resturantName)
        intent.putExtra("resturantRate", item.resturantRate)
        intent.putExtra("resturantAddress", item.resturantAddress)
       // intent.putExtra("resturantID", item.resturantID)
       // intent.putExtra("resturantImage", item.resturantImage)
        // intent.putExtra("CARLOGO", item.logo.toString())
        startActivity(intent)


    }
}