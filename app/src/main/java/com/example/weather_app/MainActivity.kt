package com.example.weather_app

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager

import android.location.Geocoder
import android.location.Location
import android.location.LocationManager

import android.os.AsyncTask
import android.os.Bundle
import android.telecom.Call
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import org.json.JSONObject
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {
    val CITY:String = "krakow"
    val API: String = "c834cca8309bc772c056e3d4b6a411b0"
    internal lateinit var image: ImageView
    internal lateinit var details: Button

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val PERMISSION_ID = 1010
    private lateinit var locationButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestPermission()

        val city = intent.getStringExtra("cityName")
        details = findViewById(R.id.details)

        details.setOnClickListener{
            println("details")
            val intent = Intent(this@MainActivity, SecondActivity::class.java).apply {
            }
            startActivity(intent)
        }
//        locationButton = findViewById(R.id.locationButton)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

//        locationButton.setOnClickListener {
//            getLocation()
//        }


        if (city != null) {
            callWetherTask(city)
        } else {
            getLocation()
        }


    }


    private fun callWetherTask(city: String) {
        weatherTask(city).execute()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean{
        val id = item.itemId
        if (id == R.id.search){
            val intent = Intent(this@MainActivity, SearchActivity::class.java).apply {
            }
            startActivity(intent)
        } else if(id == R.id.location){
            getLocation()
        }
        return super.onOptionsItemSelected(item)
    }

    inner class weatherTask(city:String) : AsyncTask<String, Void, String>() {
        val cityIn: String
        init{
            cityIn = city
        }
        override fun onPreExecute() {
            super.onPreExecute()
        }

        override fun doInBackground(vararg params: String?): String? {
            var response:String?
            try{
                response = URL("https://api.openweathermap.org/data/2.5/weather?q=$cityIn&units=metric&appid=$API").readText(
                        Charsets.UTF_8
                )
            }catch (e: Exception){
                response = null
            }
            return response
        }

        override fun onPostExecute(response: String) {
            image = findViewById(R.id.image)

            super.onPostExecute(response)
            try {
                val jsonObject = JSONObject(response)
                val main = jsonObject.getJSONObject("main")
                val weather = jsonObject.getJSONArray("weather").getJSONObject(0)
                val updatedAt:Long = jsonObject.getLong("dt")
                val updatedAtText = SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(
                        Date(updatedAt*1000)
                )
                val temp = main.getString("temp").toDouble().toInt().toString()+"°C"
                val feltTemp = main.getString("feels_like").toString()+"°C"

                val pressure = main.getString("pressure").toDouble().toInt().toString()+" hPa"
                val humidity = main.getString("humidity").toDouble().toInt().toString()+"%"

                val weatherDescription = weather.getString("description")
                val id = weather.getString("id")

                val address = jsonObject.getString("name")
                val sys = jsonObject.getJSONObject("sys")
                val wind = jsonObject.getJSONObject("wind").getString("speed")+" km/h"

                findViewById<TextView>(R.id.address).text = address
                findViewById<TextView>(R.id.data).text = updatedAtText
                findViewById<TextView>(R.id.status).text = weatherDescription.capitalize()
                findViewById<TextView>(R.id.temperature).text = temp
                findViewById<TextView>(R.id.windText).text = wind
                findViewById<TextView>(R.id.pressureText).text = pressure
                findViewById<TextView>(R.id.humidityText).text = humidity
                findViewById<TextView>(R.id.feltTemp).text = feltTemp


                val intId: Int = id.toInt()

                if (intId == 800){
                    image.setImageResource(R.drawable.sunny)
                } else if  (intId >= 200 && intId <=299){
                    image.setImageResource(R.drawable.thunder)
                } else if  (intId >= 300 && intId <=599){
                    image.setImageResource(R.drawable.rainy)
                } else if  (intId >= 600 && intId <=699){
                    image.setImageResource(R.drawable.snowy)
                } else if  (intId >= 700 && intId <=799){
                    image.setImageResource(R.drawable.fog)
                } else{
                    image.setImageResource(R.drawable.cloudy)
                }



            } catch (e: Exception) {
                Toast.makeText(this@MainActivity, "Sorry, something went wrong.", Toast.LENGTH_LONG).show()
                val intent = Intent(this@MainActivity, SearchActivity::class.java).apply {
                }
                startActivity(intent)
            }

        }
    }


    private fun checkPermission():Boolean {
        if(
                ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        ){
            return true
        }

        return false
    }


    private fun requestPermission() {
        ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION,android.Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSION_ID
        )
    }

    @SuppressLint("ServiceCast")
    fun isLocationEnabled():Boolean{

        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        if(requestCode == PERMISSION_ID){
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            }
        }
    }




    private fun getCityName(lat: Double,long: Double):String{
        val geoCoder = Geocoder(this, Locale.getDefault())
        val Adress = geoCoder.getFromLocation(lat,long,3)

        return Adress[0].locality
    }


    private fun getLocation(){
        if(checkPermission()){
            if(isLocationEnabled()){
                if (ActivityCompat.checkSelfPermission(
                                this,
                                Manifest.permission.ACCESS_FINE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                this,
                                Manifest.permission.ACCESS_COARSE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return
                }
                fusedLocationProviderClient.lastLocation.addOnCompleteListener { task->
                    val location:Location? = task.result
                    if(location == null){
                        newLocationData()
                    }
                    else {
                        callWetherTask(getCityName(location.latitude,location.longitude))

                    }
                }
            }
            else {
                Toast.makeText(this,"Please Turn on Your device Location",Toast.LENGTH_SHORT).show()
                callWetherTask("Krakow")
            }
        }
        else {
            requestPermission()
        }
    }



    private fun newLocationData(){
        val locationRequest =  LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 0
        locationRequest.fastestInterval = 0
        locationRequest.numUpdates = 1
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
    }



}