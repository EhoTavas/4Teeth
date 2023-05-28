package br.com.ForTeethDentalCare.screens.menu

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.ForTeethDentalCare.dataStore.UserPreferencesRepository
import br.com.ForTeethDentalCare.databinding.FragmentUserBinding
import br.com.ForTeethDentalCare.screens.login.LoginActivity
import br.com.ForTeethDentalCare.screens.menu.MenuActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.ktx.Firebase
import org.json.JSONObject

class UserFragment : Fragment() {

    private var _binding: FragmentUserBinding? = null
    private lateinit var auth: FirebaseAuth
    private lateinit var functions: FirebaseFunctions
    private var user = FirebaseAuth.getInstance().currentUser
    private val binding get() = _binding!!
    private lateinit var userPreferencesRepository: UserPreferencesRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val auth = FirebaseAuth.getInstance()
        val email = user!!.email
        val functions = FirebaseFunctions.getInstance("southamerica-east1")
        userPreferencesRepository = UserPreferencesRepository.getInstance(requireContext())

        functions.getHttpsCallable("getUserProfile")
            .call(mapOf("uid" to userPreferencesRepository.uid))
            .continueWith { task ->
                if (task.isSuccessful) {
                    val result = task.result.data as String
                    Log.d("Firebase", "Resultado da função: $result")

                    val jsonResult = JSONObject(result)

                    val userData = jsonResult.getString("payload")

                    val userJson = JSONObject(userData)
                    val userName = userJson.getString("nome")
                    val userMail = userJson.getString("email")
                    val userPhone = userJson.getString("telefone")

                    binding.tvUserName.text = userName
                    binding.tvUserMail.text = userMail
                    binding.tvUserPhone.text = userPhone
                } else {
                    Log.e("Firebase", "Erro ao chamar a função", task.exception)
                }
            }

        binding.btnLogout.setOnClickListener {
            auth.signOut()
            startActivity(Intent(requireContext(), LoginActivity::class.java))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}