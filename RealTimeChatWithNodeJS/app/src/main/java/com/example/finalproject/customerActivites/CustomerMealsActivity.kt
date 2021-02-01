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
import com.example.finalproject.adapters.CustomerMealAdapter
import com.example.finalproject.R
import com.example.finalproject.model.Meal
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_customer_meals.*

class CustomerMealsActivity : AppCompatActivity() , CustomerMealAdapter.OnMealItemClickListner{

    private lateinit var adapter: CustomerMealAdapter

    lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_meals)

        db = Firebase.firestore
        //    getAllUser()
        ComanionClass.Meals.clear()
        if(ComanionClass.Meals.isEmpty()) {
            getAllUsers()
        }

        adapter = CustomerMealAdapter(
            this,
            ComanionClass.Meals,
            this,
            this
        )

        recyclerViewCustomerMeals?.adapter = adapter
        recyclerViewCustomerMeals?.layoutManager = LinearLayoutManager(this)
    }

    private fun getAllUsers() {
        db.collection("Resturants").document(ComanionClass.resturantID).collection("Meal")
            .get()
            .addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot){
                    val name: String = document.getString("Name")!!
                    val description: String = document.getString("Description")!!
                    val price: String = document.getString("Price")!!
                    val img:String = document.getString("Image")!!

                    val meal = Meal(name, 1, description, price.toDouble(),img)
                    ComanionClass.Meals.add(meal)
                }
                adapter.notifyDataSetChanged()
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

    override fun onItemClick(item: Meal, position: Int) {
        ComanionClass.mealName = item.mealName
        ComanionClass.mealDescription = item.mealDescription
        ComanionClass.mealPrice = item.mealPrice.toString()
        ComanionClass.mealRate = item.mealRate.toString()


        val intent = Intent(this, MealDetailsActivity::class.java)
        intent.putExtra("mealName", item.mealName)
        intent.putExtra("mealDescription", item.mealDescription)
        intent.putExtra("mealPrice", item.mealPrice)
        // intent.putExtra("CARLOGO", item.logo.toString())
        startActivity(intent)
    }
}
