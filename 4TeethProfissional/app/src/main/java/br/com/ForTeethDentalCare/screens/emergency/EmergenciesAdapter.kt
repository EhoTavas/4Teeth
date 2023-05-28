package br.com.ForTeethDentalCare

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.*
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.ForTeethDentalCare.dataStore.Emergency
import br.com.ForTeethDentalCare.screens.emergency.RequestedEmergencyActivity
class EmergenciesAdapter(private var dataSet: List<Emergency>) :
    ListAdapter<Emergency, EmergenciesAdapter.EmergencyViewHolder>(EmergencyDiffCallback) {

        class EmergencyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private val patientName: AppCompatTextView = itemView.findViewById(R.id.tvPatientName)
            private val serviceStatus: AppCompatTextView = itemView.findViewById(R.id.tvServiceStatus)
            //private val patientPicture: AppCompatImageView = itemView.findViewById(R.id.imgPatientPicture)
            //private val patientDistance: AppCompatTextView = itemView.findViewById(R.id.tvPatientDistance)
            private var emergencyActual: Emergency? = null

            fun bind(t: Emergency) {
                emergencyActual = t
                patientName.text = t.name
                serviceStatus.text = getString(R.string.service_status)
                //patientDistance.text = t.distance
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmergencyViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_emergency, parent, false)
            return EmergencyViewHolder(view)
        }

        override fun onBindViewHolder(holder: EmergencyViewHolder, position: Int) {
            val t = dataSet[position]
            holder.bind(t)

            holder.itemView.setOnClickListener{
                val intentPatientData = Intent(it.context, RequestedEmergencyActivity::class.java)

                intentPatientData.putExtra("name", t.name.toString())
                intentPatientData.putExtra("phone", t.phone.toString())
//                intentPatientData.putExtra("photos", t.photos.toString())
//                intentPatientData.putExtra("time", t.time.toString())
                intentPatientData.putExtra("id", t.id.toString())

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