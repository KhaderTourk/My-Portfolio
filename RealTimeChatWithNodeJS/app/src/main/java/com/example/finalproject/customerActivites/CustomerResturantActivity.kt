package com.example.finalproject.customerActivites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalproject.Activites.ComanionClass
import com.example.finalproject.Activites.DashboardActivity
import com.example.finalproject.Activites.ProfileActivity
import com.example.finalproject.R
import com.example.finalproject.adapters.ResturantAAdapter
import com.example.finalproject.model.Resturant
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_customer_resturant.*

class CustomerResturantActivity : AppCompatActivity(), ResturantAAdapter.OnRestItemClickListner{

    private lateinit var resturantAAdapter: ResturantAAdapter

    lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_resturant)

        db = Firebase.firestore
        ComanionClass.resturants.clear()
        if(ComanionClass.resturants.isEmpty()) {
            getAllUsers()
        }

        resturantAAdapter = ResturantAAdapter(
            this,
            ComanionClass.resturants,
            this,
            this
        )

        recyclerViewCustomerResturant?.adapter = resturantAAdapter
        recyclerViewCustomerResturant?.layoutManager = LinearLayoutManager(this)
    }

    private fun getAllUsers() {
        db.collection("Resturants")
            .get()
            .addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot){
                    val name:String = document.getString("Name")!!
                    val address:String = document.getString("Address")!!
                    val lat: String = document.getString("Lat")!!
                    val long: String = document.getString("Long")!!
                    // val id: String = document.getString("ID")!!
                     val img: String = document.getString("Image")!!

                    if (document.getString("Rate") == null || document.getString("Rate") == ""){
                        val p = Resturant(name, 5.0, address,lat,long,img)
                        ComanionClass.resturants.add(p)
                    }else{
                        val rate: String = document.getString("Rate")!!
                        val p = Resturant(name, rate.toDouble(), address,lat,long,img)
                        ComanionClass.resturants.add(p)
                    }

                }
                resturantAAdapter.notifyDataSetChanged()
            }.addOnFailureListener{exception ->
                Log.e("ttr",exception.message!!)
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
            val intent = Intent(this,
                DashboardActivity::class.java)
            startActivity(intent)
            //  confirmDialog()
        }else if (item.itemId == R.id.Profile){
            val intent = Intent(this,
                ProfileActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onItemClick(item: Resturant, position: Int) {
        ComanionClass.resturantName = item.resturantName
        ComanionClass.resturantAddress = item.resturantAddress
        ComanionClass.resturantRate = item.resturantRate.toString()
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
        // intent.putExtra("CARLOGO", item.logo.toString())
        startActivity(intent)
    }
}
