package com.example.weather_app

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CityAdapter(var dataSet: Array<CityWeather>, val context: Context): RecyclerView.Adapter<CityAdapter.ViewHolder>()  {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val day:TextView
        val temp:TextView
        val image:ImageView

        val desc:TextView

        init {
            day = view.findViewById(R.id.day)
            temp = view.findViewById(R.id.tempText)
            image = view.findViewById(R.id.imageDay)
            desc = view.findViewById(R.id.descriptionText)
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
        viewHolder.day.text = city.day
        viewHolder.temp.text = city.temperature

      //  viewHolder.image.setImageResource(city.imageID)

        if (city.imageID == 800){
            viewHolder.image.setImageResource(R.drawable.sunny)
        } else if  (city.imageID >= 200 && city.imageID <=299){
            viewHolder.image.setImageResource(R.drawable.thunder)
        } else if  (city.imageID >= 300 && city.imageID <=499){
            viewHolder.image.setImageResource(R.drawable.drizzel)
        } else if  (city.imageID >= 500 && city.imageID <=502){
            viewHolder.image.setImageResource(R.drawable.rainy)
        } else if  (city.imageID >= 503 && city.imageID <=599){
            viewHolder.image.setImageResource(R.drawable.rain_clouds)
        } else if  (city.imageID >= 600 && city.imageID <=699){
            viewHolder.image.setImageResource(R.drawable.snowy)
        } else if  (city.imageID >= 700 && city.imageID <=799){
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
