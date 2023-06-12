package br.com.ForTeethDentalCare.screens.emergency

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import br.com.ForTeethDentalCare.databinding.FragmentEmergencyBinding
import br.com.ForTeethDentalCare.Constants.answerEmergency
import br.com.ForTeethDentalCare.screens.menu.MenuActivity
import com.google.firebase.auth.FirebaseAuth
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
//tvPatientName
        id = getInfo("id")
        name = getInfo("name")
        mouth = getInfo("fotoBoca")
        document = getInfo("fotoDocumento")
        child = getInfo("fotoCrianca")
        binding.tvPatientName.text = name
        (requireActivity() as AppCompatActivity).supportActionBar?.title = name
        val imageRefs = listOf(mouth, document, child)

        binding.recyclerView.adapter = GalleryAdapter(imageRefs)
        binding.btnDecline.setOnClickListener {
            answerEmergency(false, id, it, requireActivity())
            val intent = Intent(requireContext(), MenuActivity::class.java)
            startActivity(intent)
            //requireActivity().finish()
        }
        binding.btnAccept.setOnClickListener {
            answerEmergency(true, id, it, requireActivity())
            val intent = Intent(requireContext(), MenuActivity::class.java)
            startActivity(intent)
            //requireActivity().finish()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}