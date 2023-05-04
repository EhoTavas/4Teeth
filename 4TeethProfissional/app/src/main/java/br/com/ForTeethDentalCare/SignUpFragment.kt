package br.com.ForTeethDentalCare

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import br.com.ForTeethDentalCare.databinding.FragmentSignUpBinding
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import br.com.ForTeethDentalCare.CustomResponse
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.functions.ktx.functions
import com.google.firebase.ktx.Firebase
import com.google.gson.GsonBuilder
import org.json.JSONArray
import org.json.JSONObject

class SignUpFragment : Fragment() {


    private val TAG = "SignUpFragment"
    private lateinit var auth: FirebaseAuth
    private lateinit var functions: FirebaseFunctions
    private val gson = GsonBuilder().enableComplexMapKeySerialization().create()

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.icNavBackLogin.setOnClickListener {
            findNavController().navigate(R.id.SignUp_to_Login)
        }

        binding.btnContinuar.setOnClickListener {
            (activity as MainActivity).let {
                it.dentist.email = binding.EtEmail.text.toString()
                it.dentist.nome = binding.EtNome.text.toString()
                it.dentist.telefone = binding.EtTelefone.text.toString()
                it.dentist.senha = binding.EtPassword.text.toString()
                it.dentist.curriculo = binding.EtCurriculo.text.toString()
            }

            findNavController().navigate(R.id.SignUp_to_Addresses)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}