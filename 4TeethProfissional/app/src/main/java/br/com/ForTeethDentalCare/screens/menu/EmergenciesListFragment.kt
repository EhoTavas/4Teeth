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
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class EmergenciesListFragment : Fragment() {

    private var _binding: FragmentEmergenciesListBinding? = null
    private val binding get() = _binding!!
    private lateinit var emergenciesAdapter: EmergenciesAdapter
    private val db = Firebase.firestore
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

        _binding!!.rvEmergencies.layoutManager = GridLayoutManager(binding.root.context, 2)
        _binding!!.rvEmergencies.adapter = emergenciesAdapter
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
        val collection = db.collection("Emergencias").orderBy("time")
        var emergency: Emergency
        collection.addSnapshotListener { value, e ->
            allEmergencies.clear()
            if (e != null) {
                Log.e("FirestoreListener", "Erro ao ler novas emergências", e)
                return@addSnapshotListener
            }

            for (document in value!!) {
                emergency = Emergency(
                    document.data["name"].toString(),
                    document.data["phone"].toString(),
                    //Adicionar aqui as outras informações que serão enviadas pelo socorrista
                )

                allEmergencies.add(emergency)
            }

            emergenciesAdapter.notifyDataSetChanged()
        }
    }
}