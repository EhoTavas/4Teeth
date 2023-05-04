package br.com.ForTeethDentalCare

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.ForTeethDentalCare.databinding.FragmentCadastroEnderecoBinding

class CadastroEnderecoFragment : Fragment() {

    private var _binding: FragmentCadastroEnderecoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCadastroEnderecoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.icNavBackSignUp.setOnClickListener {
            findNavController().navigate(R.id.Addresses_to_SignUp)
        }

        binding.btnConfirmar.setOnClickListener {
            findNavController().navigate(R.id.Addresses_to_menuFragment)
        }
    }
    //btnContinuar

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}