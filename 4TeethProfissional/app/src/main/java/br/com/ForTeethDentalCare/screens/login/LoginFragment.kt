package br.com.ForTeethDentalCare.screens.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.ForTeethDentalCare.R
import br.com.ForTeethDentalCare.dataStore.UserPreferencesRepository
import br.com.ForTeethDentalCare.databinding.FragmentLoginBinding
import br.com.ForTeethDentalCare.screens.menu.MenuActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.ktx.Firebase

class LoginFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var userPrefRep: UserPreferencesRepository

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
        val activity = requireActivity() as LoginActivity
        userPrefRep = UserPreferencesRepository.getInstance(requireContext())

        if (activity.dentist.email != "") {
            binding.EtEmailLogin.setText(activity.dentist.email)
        }

        // evento para tratar o login com auth.
        binding.btnLogin.setOnClickListener {
            if (binding.EtEmailLogin.text.toString() == "" || binding.EtPasswordLogin.text.toString() == "") {
                Snackbar.make(requireView(),"Não foi possível fazer o login, verifique os dados e tente novamente.", Snackbar.LENGTH_LONG).show()
            } else {
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
                    val sharedPref = UserPreferencesRepository.getInstance(requireContext())
                    sharedPref.updateUsername(email)
                    sharedPref.updatePassword(password)
                    val intent = Intent(requireContext(), MenuActivity::class.java)
                    startActivity(intent)
                    //findNavController().navigate(R.id.Login_to_menuFragment)
                    //Constants.sendMessage(email, userPrefRep.fcmToken)
                    Snackbar.make(requireView(), R.string.logged_in, Snackbar.LENGTH_LONG).show()
                } else {
                    if (it.exception is FirebaseAuthException) {
                        Snackbar.make(requireView(), R.string.unable_to_login, Snackbar.LENGTH_LONG).show()
                    }
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}