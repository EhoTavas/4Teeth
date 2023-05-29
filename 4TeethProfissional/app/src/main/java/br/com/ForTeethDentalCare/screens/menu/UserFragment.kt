package br.com.ForTeethDentalCare.screens.menu

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.ForTeethDentalCare.dataStore.Dentist
import br.com.ForTeethDentalCare.dataStore.UserPreferencesRepository
import br.com.ForTeethDentalCare.databinding.FragmentUserBinding
import br.com.ForTeethDentalCare.screens.login.LoginActivity
import br.com.ForTeethDentalCare.screens.menu.MenuActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.ktx.Firebase
import org.json.JSONObject

class UserFragment : Fragment() {

    private var _binding: FragmentUserBinding? = null
    private lateinit var auth: FirebaseAuth
    private lateinit var functions: FirebaseFunctions
    private val db = Firebase.firestore
    private var user = FirebaseAuth.getInstance().currentUser
    private val email = user!!.email
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

        loadUserData()

        binding.btnLogout.setOnClickListener {
            auth.signOut()
            startActivity(Intent(requireContext(), LoginActivity::class.java))
        }
    }

    private fun loadUserData() {
        val collection = db.collection("Dentista")
        collection.addSnapshotListener { value, e ->
            if (e != null) {
                Log.e("FirestoreListener", "Erro ao recuperar dados do dentista", e)
                return@addSnapshotListener
            }

            for (document in value!!) {
                if (document.data["email"].toString() == email) {
                    binding.tvUserName.text = document.data["nome"].toString()
                    binding.tvUserMail.text = document.data["email"].toString()
                    binding.tvUserPhone.text = document.data["telefone"].toString()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}