package br.com.ForTeethDentalCare.screens.menu

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import br.com.ForTeethDentalCare.R
import br.com.ForTeethDentalCare.dataStore.UserPreferencesRepository
import br.com.ForTeethDentalCare.databinding.FragmentMenuBinding

class MenuFragment : Fragment() {

    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!
    private lateinit var userPrefRep: UserPreferencesRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userPrefRep = UserPreferencesRepository.getInstance(requireContext())

        Log.d("FcmToken", userPrefRep.fcmToken)

        binding.availableEmergencies.setOnClickListener{
            findNavController().navigate(R.id.menuFragment_to_emergencyFragment)
        }
        binding.attendedEmergencies.setOnClickListener{
            findNavController().navigate(R.id.menuFragment_to_attendedEmergenciesFragment)
        }
        binding.serviceAddresses.setOnClickListener{
            findNavController().navigate(R.id.menuFragment_to_serviceAddressesFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}