package br.com.ForTeethDentalCare

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.*
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat.getColor
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.ForTeethDentalCare.dataStore.Emergency
import br.com.ForTeethDentalCare.screens.emergency.RequestedEmergencyActivity

class AttendedEmergenciesAdapter(private var dataSet: List<Emergency>) :
    ListAdapter<Emergency, AttendedEmergenciesAdapter.AttendedEmergencyViewHolder>(AttendedEmergencyDiffCallback) {

    class AttendedEmergencyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val patientName: AppCompatTextView = itemView.findViewById(R.id.tvPatientName)
        private val serviceStatus: AppCompatTextView = itemView.findViewById(R.id.tvServiceStatus)
        private val patientDistance: AppCompatTextView = itemView.findViewById(R.id.tvPatientDistance)
        private val imgMapPing: AppCompatImageView = itemView.findViewById(R.id.imgMapPing)
        private var thisEmergency: Emergency? = null

        fun bind(t: Emergency) {
            thisEmergency = t
            patientName.text = t.name
            serviceStatus.text = itemView.context.getString(R.string.service_finished)
            serviceStatus.setTextColor(ContextCompat.getColor(itemView.context, R.color.btnAccept))
            patientDistance.visibility = View.GONE
            imgMapPing.visibility = View.GONE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttendedEmergencyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_emergency, parent, false)
        return AttendedEmergencyViewHolder(view)
    }

    override fun onBindViewHolder(holder: AttendedEmergencyViewHolder, position: Int) {
        val t = dataSet[position]
        holder.bind(t)
    }

    override fun getItemCount() = dataSet.size
}

object AttendedEmergencyDiffCallback : DiffUtil.ItemCallback<Emergency>() {
    override fun areItemsTheSame(oldItem: Emergency, newItem: Emergency): Boolean {
        return oldItem == newItem
    }
    override fun areContentsTheSame(oldItem: Emergency, newItem: Emergency): Boolean {
        return oldItem.name == newItem.name
    }
}