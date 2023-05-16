package br.com.ForTeethDentalCare

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.ForTeethDentalCare.dataStore.UserPreferencesRepository
import br.com.ForTeethDentalCare.databinding.FragmentLoginBinding
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
        val activity = requireActivity() as MainActivity
        userPrefRep = UserPreferencesRepository.getInstance(requireContext())

        Log.d("login", userPrefRep.username)
        Log.d("senha", userPrefRep.password)
        Log.d("primeira vez", userPrefRep.firstTime.toString())

        if (activity.dentist.email != "") {
            binding.EtEmailLogin.setText(activity.dentist.email)
        } else if (userPrefRep.username != "" && userPrefRep.password != ""){
            login(userPrefRep.username, userPrefRep.password)
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

    private fun showCustomDialog() {
        val builder = AlertDialog.Builder(requireContext())
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.pop_up_notifications_blocked, null)
        builder.setView(dialogView)

        val dialogTitle = dialogView.findViewById<TextView>(R.id.dialog_title)
        val dialogMessage = dialogView.findViewById<TextView>(R.id.dialog_message)
        val dialogOkButton = dialogView.findViewById<Button>(R.id.dialog_ok)

        dialogTitle.text = "Título do diálogo"
        dialogMessage.text = "Mensagem do diálogo"

        val dialog = builder.create()

        dialogOkButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
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
                    val intent = Intent(requireContext(), LoggedActivity::class.java)
                    startActivity(intent)
                    //findNavController().navigate(R.id.Login_to_menuFragment)
                    Constants.sendMessage(email, userPrefRep.fcmToken)
                    Snackbar.make(requireView(),R.string.logged_in, Snackbar.LENGTH_LONG).show()
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