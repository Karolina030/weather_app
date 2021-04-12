package com.example.weather_app

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CityAdapter(var dataSet: Array<CityWeather>, val context: Context): RecyclerView.Adapter<CityAdapter.ViewHolder>()  {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cityName:TextView
        val temp:TextView

        init {
            cityName = view.findViewById(R.id.cityName)
            temp = view.findViewById(R.id.tempText)

        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.city_row, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        //viewHolder.textView.text = dataSet[position]
        val city = dataSet[position]
        viewHolder.cityName.text = city.cityName
        viewHolder.temp.text = city.temperature

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}
