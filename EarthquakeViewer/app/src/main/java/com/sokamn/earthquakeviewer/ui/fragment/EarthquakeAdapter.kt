package com.sokamn.earthquakeviewer.ui.fragment

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sokamn.earthquakeviewer.databinding.ItemEarthquakeBinding
import com.sokamn.earthquakeviewer.model.Earthquake
import java.text.Format
import java.text.SimpleDateFormat
import java.util.*

class EarthquakeAdapter : androidx.recyclerview.widget.ListAdapter<Earthquake, EarthquakeAdapter.EarthquakeViewHolder>(DiffCallBack){
    lateinit var onItemClickListener: (Earthquake) -> Unit
    inner class EarthquakeViewHolder(view:View): RecyclerView.ViewHolder(view) {
        val binding = ItemEarthquakeBinding.bind(view)
        @SuppressLint("SetTextI18n", "SimpleDateFormat")
        fun render(earthquake: Earthquake){
            binding.txvMagnitude.text = earthquake.magnitude.toString()
            binding.txvPlace.text = earthquake.place
            val date = Date(earthquake.duracion)
            val format: Format = SimpleDateFormat("HH:mm")
            binding.txvTime.text = "${format.format(date)} ART"

            itemView.setOnClickListener {
                if (::onItemClickListener.isInitialized){
                    onItemClickListener(earthquake)
                }else{
                    Log.d("Earthquake", "Te olvidaste de implementar el listener del adapter")
                }
            }
        }
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

    companion object DiffCallBack: DiffUtil.ItemCallback<Earthquake>(){
        override fun areItemsTheSame(oldItem: Earthquake, newItem: Earthquake): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Earthquake, newItem: Earthquake): Boolean {
            return oldItem == newItem
        }

    }
}