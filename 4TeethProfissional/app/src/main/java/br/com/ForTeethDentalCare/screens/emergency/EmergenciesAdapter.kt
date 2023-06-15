package br.com.ForTeethDentalCare

import android.content.Intent
import android.location.Location
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.ForTeethDentalCare.dataStore.Emergency
import br.com.ForTeethDentalCare.screens.emergency.RequestedEmergencyActivity
import java.text.DecimalFormat

class EmergenciesAdapter(private var dataSet: List<Emergency>,
                         private val currentLatitude: Double,
                         private val currentLongitude: Double) :
    ListAdapter<Emergency, EmergenciesAdapter.EmergencyViewHolder>(EmergencyDiffCallback) {

    class EmergencyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val patientName: AppCompatTextView = itemView.findViewById(R.id.tvPatientName)
        private val dentistStatus: AppCompatTextView = itemView.findViewById(R.id.tvDentistStatus)
        private val rescuerStatus: AppCompatTextView = itemView.findViewById(R.id.tvRescuerStatus)
        private val serviceStatus: AppCompatTextView = itemView.findViewById(R.id.tvServiceStatus)
        val patientDistance: AppCompatTextView = itemView.findViewById(R.id.tvPatientDistance)
        private var thisEmergency: Emergency? = null

        fun bind(t: Emergency) {
            thisEmergency = t
            patientName.text = t.name
            if (t.status == "0") {
                dentistStatus.text = itemView.context.getString(R.string.dentist_waiting)
            } else if (t.status == "1") {
                dentistStatus.setTextColor(ContextCompat.getColor(itemView.context, R.color.btnAccept))
                dentistStatus.text = itemView.context.getString(R.string.dentist_accepted)
                rescuerStatus.text = itemView.context.getString(R.string.rescuer_waiting)
            } else if (t.status == "2") {
                dentistStatus.setTextColor(ContextCompat.getColor(itemView.context, R.color.btnAccept))
                dentistStatus.setTextColor(ContextCompat.getColor(itemView.context, R.color.btnAccept))
                dentistStatus.text = itemView.context.getString(R.string.dentist_accepted)
                rescuerStatus.text = itemView.context.getString(R.string.rescuer_accepted)
                serviceStatus.text = itemView.context.getString(R.string.service_waiting)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmergencyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_emergency, parent, false)
        return EmergencyViewHolder(view)
    }

    private fun calculateDistance(startLatitude: Double, startLongitude: Double, endLatitude: Double, endLongitude: Double): Float {
        val startPoint = Location("locationA")
        startPoint.latitude = startLatitude
        startPoint.longitude = startLongitude

        val endPoint = Location("locationB")
        endPoint.latitude = endLatitude
        endPoint.longitude = endLongitude

        return startPoint.distanceTo(endPoint)
    }

    override fun onBindViewHolder(holder: EmergencyViewHolder, position: Int) {
        val t = dataSet[position]
        holder.bind(t)
        val distance = calculateDistance(t.latitude, t.longitude, currentLatitude, currentLongitude) / 1000
        val formattedDistance = DecimalFormat("#.##").format(distance)

        holder.patientDistance.text = "$formattedDistance km"

        holder.itemView.setOnClickListener {
            val intentPatientData = Intent(it.context, RequestedEmergencyActivity::class.java)

            intentPatientData.putExtra("name", t.name)
            intentPatientData.putExtra("phone", t.phone)
            intentPatientData.putExtra("fotoBoca", t.mouth)
            intentPatientData.putExtra("fotoCrianca", t.document)
            intentPatientData.putExtra("fotoDocumento", t.child)
            intentPatientData.putExtra("status", t.status)
//                intentPatientData.putExtra("time", t.time)
            intentPatientData.putExtra("id", t.id)

            it.context.startActivity(intentPatientData)
        }
    }

    override fun getItemCount() = dataSet.size
}

object EmergencyDiffCallback : DiffUtil.ItemCallback<Emergency>() {
    override fun areItemsTheSame(oldItem: Emergency, newItem: Emergency): Boolean {
        return oldItem == newItem
    }
    override fun areContentsTheSame(oldItem: Emergency, newItem: Emergency): Boolean {
        return oldItem.name == newItem.name
    }
}