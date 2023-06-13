package br.com.ForTeethDentalCare

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.ForTeethDentalCare.dataStore.Emergency
import br.com.ForTeethDentalCare.dataStore.Service
import com.bumptech.glide.Glide
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

class ServicesAdapter(private var dataSet: List<Service>) :
    ListAdapter<Service, ServicesAdapter.ServicesViewHolder>(ServicesDiffCallback) {

    class ServicesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val patientName: AppCompatTextView = itemView.findViewById(R.id.tvPatientName)
        private val serviceStatus: AppCompatTextView = itemView.findViewById(R.id.tvServiceStatus)
        private val patientDistance: AppCompatTextView = itemView.findViewById(R.id.tvPatientDistance)
        private val imgMapPing: AppCompatImageView = itemView.findViewById(R.id.imgMapPing)
        private var thisEmergency: Service? = null
        private val db = Firebase.firestore

        fun bind(t: Service) {
            thisEmergency = t
            val collection = db.collection("Emergencias").document(t.emergency)
            collection.addSnapshotListener { document, e ->
                if (e != null) {
                    Log.e("FirestoreListener", "Erro ao recuperar dados da emergência", e)
                    return@addSnapshotListener
                }

                patientName.text = document?.get("name").toString()
            }
            if (t.status == "1") {
                serviceStatus.text = itemView.context.getString(R.string.service_finished)
                serviceStatus.setTextColor(ContextCompat.getColor(itemView.context, R.color.btnAccept))
            } else {
                serviceStatus.text = itemView.context.getString(R.string.service_declined)
                serviceStatus.setTextColor(ContextCompat.getColor(itemView.context, R.color.btnDecline))
            }
            patientDistance.visibility = View.GONE
            imgMapPing.visibility = View.GONE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServicesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_emergency, parent, false)
        return ServicesViewHolder(view)
    }

    override fun onBindViewHolder(holder: ServicesViewHolder, position: Int) {
        val t = dataSet[position]
        holder.bind(t)
    }

    override fun getItemCount() = dataSet.size
}

object ServicesDiffCallback : DiffUtil.ItemCallback<Service>() {
    override fun areItemsTheSame(oldItem: Service, newItem: Service): Boolean {
        return oldItem == newItem
    }
    override fun areContentsTheSame(oldItem: Service, newItem: Service): Boolean {
        return oldItem.emergency == newItem.emergency
    }
}