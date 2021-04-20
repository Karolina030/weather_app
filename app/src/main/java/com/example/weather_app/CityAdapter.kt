package com.example.weather_app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CityAdapter(var dataSet: Array<CityWeather>): RecyclerView.Adapter<CityAdapter.ViewHolder>()  {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val day:TextView = view.findViewById(R.id.day)
        val temp:TextView = view.findViewById(R.id.tempText)
        val image:ImageView = view.findViewById(R.id.imageDay)
        val desc:TextView = view.findViewById(R.id.descriptionText)

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
        val city = dataSet[position]
        viewHolder.day.text = city.day
        viewHolder.temp.text = city.temperature


        if (city.imageID == 800){
            viewHolder.image.setImageResource(R.drawable.sunny)
        } else if  (city.imageID in 200..299){
            viewHolder.image.setImageResource(R.drawable.thunder)
        } else if  (city.imageID in 300..499){
            viewHolder.image.setImageResource(R.drawable.drizzel)
        } else if  (city.imageID in 500..502){
            viewHolder.image.setImageResource(R.drawable.rainy)
        } else if  (city.imageID in 503..599){
            viewHolder.image.setImageResource(R.drawable.rain_clouds)
        } else if  (city.imageID in 600..699){
            viewHolder.image.setImageResource(R.drawable.snowy)
        } else if  (city.imageID in 700..799){
            viewHolder.image.setImageResource(R.drawable.fog)
        } else if  (city.imageID == 804 ){
            viewHolder.image.setImageResource(R.drawable.full_cloud)
        }else{
            viewHolder.image.setImageResource(R.drawable.cloudy)
        }

        viewHolder.desc.text = city.description

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}
