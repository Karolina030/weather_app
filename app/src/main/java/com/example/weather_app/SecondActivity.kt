package com.example.weather_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

class SecondActivity : AppCompatActivity() {
    internal lateinit var recyclerView: RecyclerView
    internal lateinit var adapter: CityAdapter
    internal lateinit var latitude: String
    internal lateinit var longitude: String

    internal lateinit var backButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        CitySingleton.prepereSingleton(applicationContext)
        latitude = intent.getStringExtra("latitude").toString()
        longitude = intent.getStringExtra("longitude").toString()
        val city = intent.getStringExtra("cityName")

        recyclerView = findViewById(R.id.recyclerID)
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        adapter = CityAdapter(CitySingleton.getData(), this)
        recyclerView.adapter = adapter

        backButton = findViewById(R.id.backButton)

        backButton.setOnClickListener{
            val intent = Intent(this@SecondActivity, MainActivity::class.java).apply {
                putExtra("cityName", city.toString())
            }
            startActivity(intent)
        }

        makeRequest()

    }

    fun makeRequest(){
        val queue = Volley.newRequestQueue(this)
        val url = "https://api.openweathermap.org/data/2.5/onecall?lat=$latitude&lon=$longitude&exclude=current&units=metric&appid=c834cca8309bc772c056e3d4b6a411b0"

        val stringRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                CitySingleton.loadData(response)
                adapter.dataSet = CitySingleton.getData()
                adapter.notifyDataSetChanged()
            },
            Response.ErrorListener { error ->

            })
        queue.add(stringRequest)

    }




    }

