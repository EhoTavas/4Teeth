package br.com.ForTeethDentalCare.screens.menu

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import br.com.ForTeethDentalCare.ServicesAdapter
import br.com.ForTeethDentalCare.dataStore.Service
import br.com.ForTeethDentalCare.databinding.FragmentServicesBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ServicesFragment : Fragment() {

    private var _binding: FragmentServicesBinding? = null
    private val binding get() = _binding!!
    private lateinit var servicesAdapter: ServicesAdapter
    private val db = Firebase.firestore
    private var user = FirebaseAuth.getInstance().currentUser
    private var allServices = ArrayList<Service>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentServicesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        allServices.clear()
        servicesAdapter = ServicesAdapter(allServices)

        binding.rvAttendedEmergencies.layoutManager = GridLayoutManager(binding.root.context, 1)
        binding.rvAttendedEmergencies.adapter = servicesAdapter
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
        val collection = db.collection("Atendimentos")
            .whereEqualTo("dentist", user!!.uid)
            .orderBy("time", Query.Direction.DESCENDING)
        var service: Service
        collection.addSnapshotListener { value, e ->
            allServices.clear()
            if (e!= null) {
                Log.e("FirestoreListener", "Erro ao ler emergencias atendidas", e)
                return@addSnapshotListener
            }

            for (document in value!!) {
                if (document.data["status"].toString() == "0" || document.data["status"].toString() == "3") {
                    service = Service(
                        document.data["dentist"].toString(),
                        document.data["emergency"].toString(),
                        document.data["status"].toString()
                    )
                    allServices.add(service)
                }

            }

            servicesAdapter.notifyDataSetChanged()
        }
    }

}