package br.com.ForTeethDentalCare.screens.emergency

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import br.com.ForTeethDentalCare.R
import com.bumptech.glide.Glide
import com.google.firebase.storage.StorageReference

class GalleryAdapter(private val images: List<String>) : RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_emergency_picture, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(images[position])
            .into(holder.imageView)
    }

    override fun getItemCount() = images.size
}