package com.example.weather_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class SearchActivity : AppCompatActivity() {
    private lateinit var button: Button
    private lateinit var city: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        title = "Search"
        button = findViewById(R.id.button)
        city = findViewById(R.id.editText)

        button.setOnClickListener{
            val intent = Intent(this@SearchActivity, MainActivity::class.java).apply {
                putExtra("cityName", city.text.toString())
            }
            startActivity(intent)
        }
    }
}