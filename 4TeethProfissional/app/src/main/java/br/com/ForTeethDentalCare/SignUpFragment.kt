package br.com.ForTeethDentalCare

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import br.com.ForTeethDentalCare.databinding.FragmentSignUpBinding
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

            val email = binding.EtEmail.text.toString()
            val name = binding.EtNome.text.toString()
            val phone = binding.EtTelefone.text.toString()
            val password = binding.EtPassword.text.toString()
            val passwordConfirm = binding.EtPasswordConfirm.text.toString()
            val curricullum = binding.EtCurriculo.text.toString()

            (activity as MainActivity).let {
                it.dentist.email = email
                it.dentist.nome = name
                it.dentist.telefone = phone
                it.dentist.senha = password
                it.dentist.curriculo = curricullum
            }

            if (name == "" || email == "" || phone == "" || password == "" || passwordConfirm == "" || curricullum == "" ) {
                binding.TvError.text = getString(R.string.inputsEmpty)
            } else if (password != passwordConfirm) {
                binding.TvError.text = getString(R.string.missmatchPasswords)
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

/*override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = FragmentSignUpBinding.inflate(layoutInflater)
    setContentView(binding.root)

    binding.btnContinuar.setOnClickListener {

        val name = findViewById<EditText>(R.id.EtNome)
        val email = findViewById<EditText>(R.id.EtEmail)
        val phone = findViewById<EditText>(R.id.EtTelefone)
        val password = findViewById<EditText>(R.id.EtPassword)
        val passwordConfirm = findViewById<EditText>(R.id.EtPasswordConfirm)

        if (
            name.text.isEmpty() ||
            email.text.isEmpty() ||
            phone.text.isEmpty() ||
            password.text.isEmpty() ||
            passwordConfirm.text.isEmpty()
        ) {
            findViewById<TextView>(R.id.TvError).text = "Preencha todos os campos"
        } else {
            if (password.text.toString() == passwordConfirm.text.toString()) {
                val continuarCadastro = Intent(this, CadastroEnderecoFragment::class.java)
                startActivities(arrayOf(continuarCadastro))
            } else {
                findViewById<TextView>(R.id.TvError).text = "As senhas não coincidem"
            }
        }
    }

    binding.icNavegarVoltar.setOnClickListener {
        val voltarTela = Intent (this, MainActivity ::class.java)
        startActivities(arrayOf(voltarTela))
    }
}*/