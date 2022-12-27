package com.sokamn.earthquakeviewer

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sokamn.earthquakeviewer.databinding.ItemEarthquakeBinding

class EarthquakeAdapter : androidx.recyclerview.widget.ListAdapter<Earthquake, EarthquakeAdapter.RazaViewHolder>(DiffCallBack){
    lateinit var onItemClickListener: (Earthquake) -> Unit
    inner class RazaViewHolder(view:View): RecyclerView.ViewHolder(view) {
        val binding = ItemEarthquakeBinding.bind(view)
        fun render(earthquake: Earthquake){
            binding.txvMagnitude.text = earthquake.magnitude.toString()
            binding.txvPlace.text = earthquake.place

            itemView.setOnClickListener {
                if (::onItemClickListener.isInitialized){
                    onItemClickListener(earthquake)
                }else{
                    Log.d("Earthquake", "Te olvidaste de implementar el listener del adapter")
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RazaViewHolder {
        val view: View = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_earthquake,parent,false)
        return RazaViewHolder(view)
    }

    override fun onBindViewHolder(holder: RazaViewHolder, position: Int) {
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