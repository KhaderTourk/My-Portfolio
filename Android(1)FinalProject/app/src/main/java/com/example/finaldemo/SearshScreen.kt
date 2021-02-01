package com.example.finaldemo

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finaldemo.adapters.SearchAdapter
import com.example.finaldemo.models.Product
import com.example.finaldemo.screens.ProductDetails
import java.util.ArrayList

class SearshScreen : AppCompatActivity() {

    private var recyclerView: RecyclerView? = null
    private var adapter: SearchAdapter? = null
    private var etsearch: EditText? = null
    private var btnBack: ImageView? = null
    internal var textlength = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_searsh_screen)
        supportActionBar?.hide()
        recyclerView = findViewById(R.id.recycle_search) as RecyclerView
        btnBack = findViewById(R.id.btn_back_from_search)
        btnBack!!.setOnClickListener {
            val intent = Intent(this , MainActivity::class.java)
            startActivity(intent)
        }


        usersArray.add(Product("برجر الطابون","20$",R.drawable.b1))
        usersArray.add(Product("بيتزا بالتونة","50$",R.drawable.b2))
        usersArray.add(Product("بيتزا بالدرة","14$",R.drawable.b3))
        usersArray.add(Product("بيتزا بالنقانق","16$",R.drawable.b4))
        usersArray.add(Product("بيتزا بصدر الجاج","24$",R.drawable.b5))
        usersArray.add(Product("بيتزا دبل جبنة","29$",R.drawable.b6))
        usersArray.add(Product("ستيك جاج بصوص مشرووم","17$",R.drawable.b7))
        usersArray.add(Product("بيتزا قمح بالخضار","19$",R.drawable.b8))
        usersArray.add(Product("بيتزا لحم العجل","31$",R.drawable.b9))
        usersArray.add(Product("تشكين برجر","24$",R.drawable.b10))

        adapter = SearchAdapter(this,  usersArray)

        recyclerView!!.layoutManager = GridLayoutManager(applicationContext, 2)
        recyclerView!!.adapter = adapter


        etsearch = findViewById(R.id.editText) as EditText
        array_sort = ArrayList<Product>()
        array_sort = populateList()

        recyclerView!!.addOnItemTouchListener(
                RecyclerTouchListener(
                        applicationContext,
                        recyclerView!!,
                        object : ClickListener {

                            override fun onClick(view: View, position: Int) {
                                val intent = Intent(this@SearshScreen, ProductDetails::class.java)
                                intent.putExtra("P_Name",usersArray[position].name)
                                intent.putExtra("P_Price",usersArray[position].price)
                                intent.putExtra("P_Image",usersArray[position].img)
                                startActivity(intent)
                            }

                            override fun onLongClick(view: View?, position: Int) {

                            }
                        })
        )


        etsearch!!.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                textlength = etsearch!!.text.length
                array_sort.clear()
                for (i in  usersArray.indices) {
                    if (textlength <=  usersArray[i].name.length) {
                        if ( usersArray[i].name.toLowerCase().trim().contains(
                                        etsearch!!.text.toString().toLowerCase().trim { it <= ' ' })
                        ) {
                            array_sort.add(usersArray[i])
                        }
                    }
                }
                adapter = SearchAdapter(this@SearshScreen, Companion.array_sort)
                recyclerView!!.adapter = adapter
                recyclerView!!.layoutManager =
                        LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)

            }
        })

    }

    private fun populateList(): ArrayList<Product> {

        val list = ArrayList<Product>()

        for (i in 0 until  usersArray.size) {
            val imageModel = Product(usersArray[i].name,usersArray[i].price,usersArray[i].img)
            list.add(imageModel)
        }

        return list
    }

    interface ClickListener {
        fun onClick(view: View, position: Int)

        fun onLongClick(view: View?, position: Int)
    }

    internal class RecyclerTouchListener(
            context: Context,
            recyclerView: RecyclerView,
            private val clickListener: ClickListener?
    ) : RecyclerView.OnItemTouchListener {

        private val gestureDetector: GestureDetector

        init {
            gestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
                override fun onSingleTapUp(e: MotionEvent): Boolean {
                    return true
                }

                override fun onLongPress(e: MotionEvent) {
                    val child = recyclerView.findChildViewUnder(e.x, e.y)
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child))
                    }
                }
            })
        }

        override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
            val child = rv.findChildViewUnder(e.x, e.y)
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child))
            }
            return false
        }

        override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}

        override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {

        }
    }
    companion object {

        var usersArray = ArrayList<Product>()
        lateinit var array_sort: ArrayList<Product>
    }
    }
