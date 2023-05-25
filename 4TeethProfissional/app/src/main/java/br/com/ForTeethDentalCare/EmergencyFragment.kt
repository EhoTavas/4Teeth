package br.com.ForTeethDentalCare

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import br.com.ForTeethDentalCare.databinding.FragmentEmergencyBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EmergencyFragment : Fragment() {

    private var _binding: FragmentEmergencyBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEmergencyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val flowersAdapter = EmergenciesAdapter(emptyList()) // Adaptador inicial vazio
        val recyclerView: RecyclerView = binding.rvPatients

        // Chamada assíncrona da função patientsList()
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val dataSetPatients = withContext(Dispatchers.IO) {
                    Constants.patientsList()
                }
                Log.d("emergencia1", "passouaqui")
                flowersAdapter.setData(dataSetPatients) // Atualiza os dados do adaptador
            } catch (e: Exception) {
                // Trate o erro de forma adequada
                Log.e("Erro paciente", "Error fetching patient list: ${e.message}", e)
                Snackbar.make(binding.root, "Error fetching patient list", Snackbar.LENGTH_SHORT).show()
            }
        }
        recyclerView.adapter = flowersAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}