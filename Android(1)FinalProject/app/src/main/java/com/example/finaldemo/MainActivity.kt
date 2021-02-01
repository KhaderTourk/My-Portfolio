package com.example.finaldemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finaldemo.adapters.ProductAdapter
import com.example.finaldemo.models.Product
import com.example.finaldemo.screens.AddProduct
import com.example.finaldemo.screens.ProductDetails
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(),ProductAdapter.onClick {

    override fun onClickItem(position: Int) {
        val intent = Intent(this , ProductDetails::class.java)
        intent.putExtra("P_Name",productList[position].name)
        intent.putExtra("P_Price",productList[position].price)
       intent.putExtra("P_Image",productList[position].img)
        startActivity(intent)
    }

    lateinit var fab : FloatingActionButton
    lateinit var search : ImageView

    lateinit var productList: MutableList<Product>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        search = findViewById(R.id.iv_search)
        fab = findViewById(R.id.floating_action_button)
        fab.setOnClickListener {
            val intent = Intent(this , AddProduct::class.java)
            startActivity(intent)
        }
        search.setOnClickListener {
            val intent = Intent(this , SearshScreen::class.java)
            startActivity(intent)
        }

        productList = mutableListOf()

        productList.add(Product("برجر الطابون","20$",R.drawable.b1))
        productList.add(Product("بيتزا بالتونة","50$",R.drawable.b2))
        productList.add(Product("بيتزا بالدرة","14$",R.drawable.b3))
        productList.add(Product("بيتزا بالنقانق","16$",R.drawable.b4))
        productList.add(Product("بيتزا بصدر الجاج","24$",R.drawable.b5))
        productList.add(Product("بيتزا دبل جبنة","29$",R.drawable.b6))
        productList.add(Product("ستيك جاج بصوص مشرووم","17$",R.drawable.b7))
        productList.add(Product("بيتزا قمح بالخضار","19$",R.drawable.b8))
        productList.add(Product("بيتزا لحم العجل","31$",R.drawable.b9))
        productList.add(Product("تشكين برجر","24$",R.drawable.b10))
        val newPName = intent.getStringExtra("add_new_product_name")
        val newPPrice = intent.getStringExtra("add_new_product_price")
        if (newPName != null && newPPrice != null){
            productList.add(Product(newPName!! ,newPPrice!!, R.drawable.b3))}


        val recycle: RecyclerView = findViewById(R.id.recycle)

        val adapter = ProductAdapter(this,productList,this)
        recycle.layoutManager = GridLayoutManager(this, 2)
        recycle.adapter = adapter
    }


}