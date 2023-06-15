package br.com.ForTeethDentalCare.screens.menu

import android.annotation.SuppressLint
import android.app.Activity
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import br.com.ForTeethDentalCare.EmergenciesAdapter
import br.com.ForTeethDentalCare.dataStore.Emergency
import br.com.ForTeethDentalCare.databinding.FragmentEmergenciesListBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class EmergenciesListFragment : Fragment() {

    private var _binding: FragmentEmergenciesListBinding? = null
    private val binding get() = _binding!!
    private lateinit var emergenciesAdapter: EmergenciesAdapter
    private lateinit var service: Task<QuerySnapshot>
    private val db = Firebase.firestore
    private var user = FirebaseAuth.getInstance().currentUser
    private var allEmergencies = ArrayList<Emergency>()
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEmergenciesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("MissingPermission")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        allEmergencies.clear()
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location ->
            emergenciesAdapter = EmergenciesAdapter(
                allEmergencies,
                location.latitude,
                location.longitude
            )
            binding.rvEmergencies.layoutManager = GridLayoutManager(binding.root.context, 1)
            binding.rvEmergencies.adapter = emergenciesAdapter
        }
    }

    override fun onStart() {
        super.onStart()
        loadEmergencies()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun loadEmergencies() {
        val dentistUid = user!!.uid
        val emergencyCollection = db
            .collection("Emergencias")
            .whereEqualTo("status", "0")
            .orderBy("time", Query.Direction.DESCENDING)
        val serviceCollection = db.collection("Atendimentos")

        emergencyCollection.addSnapshotListener { emergencias, e ->
            allEmergencies.clear()

            if (e != null) {
                Log.e("FirestoreListener", "Erro ao ler novas emergências", e)
                return@addSnapshotListener
            }

            for (emergencyDocument in emergencias!!) {
                val emergencyId = emergencyDocument.data["id"].toString()
                Log.d("EmergencyId", emergencyId)

                serviceCollection
                    .whereEqualTo("emergency", emergencyId)
                    .whereEqualTo("dentist", dentistUid)
                    .addSnapshotListener { atendimento, _ ->
                        if (!atendimento!!.isEmpty) {
                            for (serviceDocument in atendimento) {
                                val status = serviceDocument.data["status"].toString()

                                if (status == "1" || status == "2") {
                                    val location: GeoPoint = emergencyDocument.data["location"] as GeoPoint

                                    val emergency = Emergency(
                                        emergencyDocument.data["name"].toString(),
                                        emergencyDocument.data["phone"].toString(),
                                        emergencyDocument.data["id"].toString(),
                                        emergencyDocument.data["fotoBoca"].toString(),
                                        emergencyDocument.data["fotoCrianca"].toString(),
                                        emergencyDocument.data["fotoDocumento"].toString(),
                                        emergencyDocument.data["time"] as Timestamp,
                                        status,
                                        location.latitude,
                                        location.longitude,
                                        emergencyDocument.data["atendimento"].toString()
                                    )
                                    Log.d("emergencyStatus", status)
                                    allEmergencies.add(emergency)
                                }
                            }
                        } else {
                            val location: GeoPoint = emergencyDocument.data["location"] as GeoPoint

                            val emergency = Emergency(
                                emergencyDocument.data["name"].toString(),
                                emergencyDocument.data["phone"].toString(),
                                emergencyDocument.data["id"].toString(),
                                emergencyDocument.data["fotoBoca"].toString(),
                                emergencyDocument.data["fotoCrianca"].toString(),
                                emergencyDocument.data["fotoDocumento"].toString(),
                                emergencyDocument.data["time"] as Timestamp,
                                "0",
                                location.latitude,
                                location.longitude,
                                ""
                            )
                            allEmergencies.add(emergency)
                        }

                        allEmergencies.sortByDescending { it.time }
                        emergenciesAdapter.notifyDataSetChanged()
                    }
            }
        }
    }
}