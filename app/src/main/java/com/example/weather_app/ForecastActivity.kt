package com.example.weather_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

class ForecastActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CityAdapter
    private lateinit var latitude: String
    private lateinit var longitude: String
    private lateinit var backButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forecast)
        title = "7-days forecast"
        CitySingleton.prepareSingleton()
        latitude = intent.getStringExtra("latitude").toString()
        longitude = intent.getStringExtra("longitude").toString()
        val city = intent.getStringExtra("cityName1")

        recyclerView = findViewById(R.id.recyclerID)
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        adapter = CityAdapter(CitySingleton.getData())
        recyclerView.adapter = adapter

        backButton = findViewById(R.id.backButton)

        backButton.setOnClickListener{
            val intent = Intent(this@ForecastActivity, MainActivity::class.java).apply {
                putExtra("cityName", city.toString())
            }
            startActivity(intent)
        }
        makeRequest()

    }

    private fun makeRequest(){
        val queue = Volley.newRequestQueue(this)
        val url = "https://api.openweathermap.org/data/2.5/onecall?lat=$latitude&lon=$longitude&exclude=current&units=metric&appid=${MainActivity().tokenAPI}"
        val stringRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                CitySingleton.loadData(response)
                adapter.dataSet = CitySingleton.getData()
                adapter.notifyDataSetChanged()
            },
            Response.ErrorListener {
                Toast.makeText(this@ForecastActivity, "Sorry, something went wrong.", Toast.LENGTH_LONG).show()
                val intent = Intent(this@ForecastActivity, MainActivity::class.java).apply {
                }
                startActivity(intent)
            })
        queue.add(stringRequest)
    }
}

