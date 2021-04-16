package com.example.weather_app

import android.content.Context
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*


object CitySingleton {


    private var data: Array<CityWeather>?=null

    fun prepereSingleton(context: Context) {
        data = emptyArray()
    }

    fun loadData(response: JSONObject) {

        response?.let {
            val jsonObject = response
            println(jsonObject)
            val daily = jsonObject.getJSONArray("daily")

            val tmpData = arrayOfNulls<CityWeather>(5)
            for(i in 0 until 5){
//                val updatedAtText = jsonObject.getString("lat")
//                val tempText = jsonObject.getString("lon")

                val weather = daily.getJSONObject(i)

                val updatedAt:Long = weather.getLong("dt")
                val updatedAtText = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).format(
                    Date(updatedAt*1000)
                )
                val temp = weather.getJSONObject("temp")

                val tempText = temp.getString("day").toDouble().toInt().toString()+"°C"
                val desctiption = weather.getJSONArray("weather").getJSONObject(0).getString("description")
                val dayObject = CityWeather(updatedAtText, desctiption, tempText)
                tmpData[i] = dayObject
            }
            var tmp = tmpData as Array<CityWeather>
            data = tmp

        }
    }



    fun getData():Array<CityWeather>{
        return data ?: emptyArray()
    }
}