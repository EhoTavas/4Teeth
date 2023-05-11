package br.com.ForTeethDentalCare

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.ForTeethDentalCare.databinding.FragmentLoginBinding
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.functions.ktx.functions
import com.google.firebase.ktx.Firebase
import com.google.gson.GsonBuilder

class LoginFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = requireActivity() as MainActivity

        binding.EtEmailLogin.setText(activity.dentist.email)

        // evento para tratar o login com auth.
        binding.btnLogin.setOnClickListener {
            if (binding.EtEmailLogin.text.toString() == "" || binding.EtPasswordLogin.text.toString() == "") {
                Snackbar.make(requireView(),"Não foi possível fazer o login, verifique os dados e tente novamente.", Snackbar.LENGTH_LONG).show()
            } else {
//                Constants.sendMessage(binding.EtEmailLogin.text.toString(),(activity).getFcmToken())
//                    .addOnCompleteListener(requireActivity()) { res ->
//                        // conta criada com sucesso.
//                        if(res.result.status == "SUCCESS"){
//                            hideKeyboard()
//                            Snackbar.make(requireView(),"Mensagem Enviada!", Snackbar.LENGTH_LONG).show()
//                        }
//                    }
                login(binding.EtEmailLogin.text.toString(), binding.EtPasswordLogin.text.toString())
            }
        }

        binding.btnSignUp.setOnClickListener {
            findNavController().navigate(R.id.Login_to_SignUp)
        }
    }

    private fun hideKeyboard(){
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
    }

    private fun login(email: String, password: String){
        hideKeyboard()
        // inicializando o auth.
        auth = Firebase.auth

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    // login completado com sucesso.
                    findNavController().navigate(R.id.Login_to_menuFragment)
                } else {
                    if (it.exception is FirebaseAuthException) {
                        Snackbar.make(requireView(),"Não foi possível fazer o login, verifique os dados e tente novamente.", Snackbar.LENGTH_LONG).show()
                    }
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}