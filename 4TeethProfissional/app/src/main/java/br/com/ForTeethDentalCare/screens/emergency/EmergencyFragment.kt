package br.com.ForTeethDentalCare.screens.emergency

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import br.com.ForTeethDentalCare.CustomResponse
import br.com.ForTeethDentalCare.R
import br.com.ForTeethDentalCare.databinding.FragmentEmergencyBinding
import br.com.ForTeethDentalCare.Constants.answerEmergency
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.functions.FirebaseFunctions
import com.google.gson.GsonBuilder

class EmergencyFragment : Fragment() {

    private lateinit var functions: FirebaseFunctions
    private val gson = GsonBuilder().enableComplexMapKeySerialization().create()
    private lateinit var auth: FirebaseAuth
    private lateinit var id: String

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

        id = (activity as RequestedEmergencyActivity).intent.getStringExtra("id").toString()

//        Glide.with(this)
//            .load((activity as RequestedEmergencyActivity).intent.getStringExtra("photos"))
//            .into(binding.ivEmergencyPhotos)
        //(activity as RequestedEmergencyActivity).intent.getStringExtra("id")
        binding.btnDecline.setOnClickListener {
            answerEmergency(false, id, it, requireActivity())
        }
        binding.btnAccept.setOnClickListener {
            answerEmergency(true, id, it, requireActivity())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}