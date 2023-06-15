package br.com.ForTeethDentalCare.screens.emergency

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import br.com.ForTeethDentalCare.databinding.FragmentEmergencyBinding
import br.com.ForTeethDentalCare.Constants.answerEmergency
import br.com.ForTeethDentalCare.screens.menu.MenuActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.storage.FirebaseStorage
import com.google.gson.GsonBuilder

class EmergencyFragment : Fragment() {

    private lateinit var functions: FirebaseFunctions
    private val gson = GsonBuilder().enableComplexMapKeySerialization().create()
    private lateinit var auth: FirebaseAuth
    private lateinit var id: String
    private lateinit var name: String
    private lateinit var mouth: String
    private lateinit var document: String
    private lateinit var child: String
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var _binding: FragmentEmergencyBinding? = null
    private val binding get() = _binding!!

    private fun getInfo(name: String) : String {
        return (activity as RequestedEmergencyActivity).intent.getStringExtra(name).toString()
    }

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

        id = getInfo("id")
        name = getInfo("name")
        mouth = getInfo("fotoBoca")
        document = getInfo("fotoDocumento")
        child = getInfo("fotoCrianca")
        binding.tvPatientName.text = name
        val imageRefs = listOf(mouth, document, child)

        binding.recyclerView.adapter = GalleryAdapter(imageRefs)
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        binding.btnDecline.setOnClickListener {
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                answerEmergency(
                    false, id, it,
                    requireActivity(),
                    location?.latitude!!,
                    location.longitude
                )
            }
                requireActivity().finish()
        }
        binding.btnAccept.setOnClickListener {
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                answerEmergency(
                    true, id, it,
                    requireActivity(),
                    location?.latitude!!,
                    location.longitude
                )
            }
            requireActivity().finish()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}