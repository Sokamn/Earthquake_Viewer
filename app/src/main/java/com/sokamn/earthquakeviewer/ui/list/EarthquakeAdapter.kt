package com.sokamn.earthquakeviewer.ui.list

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sokamn.earthquakeviewer.R
import com.sokamn.earthquakeviewer.databinding.ItemEarthquakeBinding
import com.sokamn.earthquakeviewer.domain.model.Earthquake
import java.text.Format
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class EarthquakeAdapter : ListAdapter<Earthquake, EarthquakeAdapter.EarthquakeViewHolder>(DiffCallBack){

    inner class EarthquakeViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val binding = ItemEarthquakeBinding.bind(view)
        fun render(earthquake: Earthquake){
            binding.txvMagnitude.text = String.format("%.2f", earthquake.magnitude)
            binding.txvPlace.text = earthquake.place
            val date = Date(earthquake.duracion)
            val format: Format = SimpleDateFormat("HH:mm", Locale.ROOT)
            val time = " ARG"
            binding.txvTime.text = format.format(date) + time

            itemView.setOnClickListener {
                onEarthquakeClickListener?.let { click ->
                    click(earthquake)
                }
            }
        }
    }

    protected var onEarthquakeClickListener : ((Earthquake) -> Unit)? = null

    fun setProductClickListener(listener: (Earthquake) -> Unit){
        onEarthquakeClickListener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EarthquakeViewHolder {
        val view: View = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_earthquake,parent,false)
        return EarthquakeViewHolder(view)
    }

    override fun onBindViewHolder(holder: EarthquakeViewHolder, position: Int) {
        val item = getItem(position)
        holder.render(item)
    }

    companion object DiffCallBack: DiffUtil.ItemCallback<Earthquake>() {
        override fun areItemsTheSame(oldItem: Earthquake, newItem: Earthquake): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Earthquake, newItem: Earthquake): Boolean {
            return oldItem == newItem
        }
    }
}