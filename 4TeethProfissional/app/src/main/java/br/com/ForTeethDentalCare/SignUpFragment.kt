package br.com.ForTeethDentalCare

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import br.com.ForTeethDentalCare.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {

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
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    /*
    override fun onCreate(savedInstanceState: Bundle?) {
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
                findViewById<TextView>(R.id.TvErro).text = "Preencha todos os campos"
            } else {
                if (password.text.toString() == passwordConfirm.text.toString()) {
                    val continuarCadastro = Intent(this, CadastroEndereco::class.java)
                    startActivities(arrayOf(continuarCadastro))
                } else {
                    findViewById<TextView>(R.id.TvErro).text = "As senhas não coincidem"
                }
            }
        }

        binding.icNavegarVoltar.setOnClickListener {
            val voltarTela = Intent (this, MainActivity ::class.java)
            startActivities(arrayOf(voltarTela))
        }
    }
    */
}