package br.com.ForTeethDentalCare.screens.menu

import android.annotation.SuppressLint
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
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEmergenciesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        allEmergencies.clear()
        emergenciesAdapter = EmergenciesAdapter(allEmergencies)

        binding.rvEmergencies.layoutManager = GridLayoutManager(binding.root.context, 1)
        binding.rvEmergencies.adapter = emergenciesAdapter
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
        val collection = db.collection("Emergencias").orderBy("time", Query.Direction.DESCENDING)
        var emergency: Emergency
        collection.addSnapshotListener { value, e ->
            allEmergencies.clear()
            if (e != null) {
                Log.e("FirestoreListener", "Erro ao ler novas emergências", e)
                return@addSnapshotListener
            }

            for (document in value!!) {
                val emergencyId = document.data["id"].toString()
                val servicesRef = db.collection("Atendimentos").whereEqualTo("emergency", emergencyId)
                servicesRef.get().addOnSuccessListener { services ->
                    var addEmergency = true
                    for (service in services) {
                        val dentistUid = service.data["dentist"].toString()
                        val status = service.data["status"].toString()

                        if (dentistUid == user!!.uid && !(status == "1" || status == "2")) {
                            addEmergency = false
                            break
                        }
                    }

                    if (addEmergency) {
                        emergency = Emergency(
                            document.data["name"].toString(),
                            document.data["phone"].toString(),
                            document.data["id"].toString(),
                            document.data["fotoBoca"].toString(),
                            document.data["fotoCrianca"].toString(),
                            document.data["fotoDocumento"].toString(),
                        )
                        allEmergencies.add(emergency)
                        emergenciesAdapter.notifyDataSetChanged()
                    }
                }
            }
        }
    }
}