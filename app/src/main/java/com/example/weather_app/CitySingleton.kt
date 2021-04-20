package com.example.weather_app
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*


object CitySingleton {

    private var data: Array<CityWeather>?=null

    fun prepareSingleton() {
        data = emptyArray()
    }

    fun loadData(response: JSONObject) {
        response.let {
            val daily = response.getJSONArray("daily")

            val tmpData = arrayOfNulls<CityWeather>(5)
            for(i in 0 until 5){

                val weather = daily.getJSONObject(i)
                val updatedAt:Long = weather.getLong("dt")
                val updatedAtText = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).format(
                        Date(updatedAt*1000)
                )
                val temp = weather.getJSONObject("temp")
                val tempText = temp.getString("day").toDouble().toInt().toString()+"°C"
                val description = weather.getJSONArray("weather").getJSONObject(0).getString("description")
                val id = weather.getJSONArray("weather").getJSONObject(0).getInt("id")
                val dayObject = CityWeather(updatedAtText, description, id, tempText)
                tmpData[i] = dayObject
            }
            val tmp = tmpData as Array<CityWeather>
            data = tmp
        }
    }

    fun getData():Array<CityWeather>{
        return data ?: emptyArray()
    }
}