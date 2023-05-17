package br.com.ForTeethDentalCare

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.ForTeethDentalCare.dataStore.Patient

class EmergenciesAdapter(private val dataSet: List<Patient>) :
    ListAdapter<Patient, EmergenciesAdapter.EmergencyViewHolder>(EmergencyDiffCallback) {

        class EmergencyViewHolder(itemView: View) :
            RecyclerView.ViewHolder(itemView) {
                private val patientName: AppCompatTextView = itemView.findViewById(R.id.tvPatientName)
                //private val patientPicture: AppCompatImageView = itemView.findViewById(R.id.imgPatientPicture)
                private val patientDistance: AppCompatTextView = itemView.findViewById(R.id.tvPatientDistance)
                private var patientActual: Patient? = null

                fun bind(t: Patient) {
                    patientActual = t
                    patientName.text = t.name
                    patientDistance.text = t.distance
                }
            }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmergencyViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.patient_button, parent, false)
            return EmergencyViewHolder(view)
        }

        override fun onBindViewHolder(holder: EmergencyViewHolder, position: Int) {
            val t = dataSet[position]
            holder.bind(t)
        }

        override fun getItemCount() = dataSet.size

}

object EmergencyDiffCallback : DiffUtil.ItemCallback<Patient>() {
    override fun areItemsTheSame(oldItem: Patient, newItem: Patient): Boolean {
        return oldItem == newItem
    }
    override fun areContentsTheSame(oldItem: Patient, newItem: Patient): Boolean {
        return oldItem.name == newItem.name
    }
}