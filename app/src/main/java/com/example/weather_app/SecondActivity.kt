package com.example.weather_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.net.URL
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

class SecondActivity : AppCompatActivity() {
    internal lateinit var recyclerView: RecyclerView
    internal lateinit var adapter: CityAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        CitySingleton.prepereSingleton(applicationContext)

        recyclerView = findViewById(R.id.recyclerID)
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        adapter = CityAdapter(CitySingleton.getData(), this)
        recyclerView.adapter = adapter

        makeRequest()

    }

    fun makeRequest(){
        val queue = Volley.newRequestQueue(this)
        //TODO: latitide and longitude
        val url = "https://api.openweathermap.org/data/2.5/onecall?lat=33.441792&lon=-94.037689&exclude=current&appid=c834cca8309bc772c056e3d4b6a411b0"

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

