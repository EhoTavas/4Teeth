package br.com.ForTeethDentalCare.screens.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import br.com.ForTeethDentalCare.R
import br.com.ForTeethDentalCare.databinding.FragmentSignUpBinding
import br.com.ForTeethDentalCare.screens.menu.MenuActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.functions.FirebaseFunctions
import com.google.gson.GsonBuilder

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
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = requireActivity() as MenuActivity

        binding.EtEmail.setText(activity.dentist.email)
        binding.EtNome.setText(activity.dentist.nome)
        binding.EtTelefone.setText(activity.dentist.telefone)
        binding.EtCurriculo.setText(activity.dentist.curriculo)

//        binding.icNavBackLogin.setOnClickListener {
//            findNavController().navigate(R.id.SignUp_to_Login)
//        }

        binding.btnContinuar.setOnClickListener {

            val email = binding.EtEmail.text.toString()
            val name = binding.EtNome.text.toString()
            val phone = binding.EtTelefone.text.toString()
            val password = binding.EtPassword.text.toString()
            val passwordConfirm = binding.EtPasswordConfirm.text.toString()
            val curricullum = binding.EtCurriculo.text.toString()

            (activity as MenuActivity).let {
                it.dentist.email = email
                it.dentist.nome = name
                it.dentist.telefone = phone
                it.dentist.senha = password
                it.dentist.curriculo = curricullum
            }

            if (name == "" || email == "" || phone == "" || password == "" || passwordConfirm == "" || curricullum == "" ) {
                binding.TvError.text = getString(R.string.inputsEmpty)
            } else if (password != passwordConfirm) {
                binding.TvError.text = getString(R.string.mismatchPasswords)
            } else if (password.length < 6) {
                binding.TvError.text = getString(R.string.passwordLength)
            } else if ((!email.endsWith(".com") && !email.endsWith(".br")) || !email.contains("@")) {
                binding.TvError.text = getString(R.string.invalidEmail)
            } else {
                findNavController().navigate(R.id.SignUp_to_Addresses)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}