package com.example.weather_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SecondActivity : AppCompatActivity() {
    internal lateinit var recyclerView: RecyclerView
    internal lateinit var adapter: CityAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        recyclerView = findViewById(R.id.recyclerID)
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        adapter = CityAdapter(emptyArray(), this)
        recyclerView.adapter = adapter


    }
}