package br.com.ForTeethDentalCare.screens.menu

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import br.com.ForTeethDentalCare.dataStore.UserPreferencesRepository
import br.com.ForTeethDentalCare.databinding.FragmentUserBinding
import br.com.ForTeethDentalCare.screens.login.LoginActivity
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage

class UserFragment : Fragment() {

    private var _binding: FragmentUserBinding? = null
    private lateinit var auth: FirebaseAuth
    private lateinit var functions: FirebaseFunctions
    private val db = Firebase.firestore
    private val storage = Firebase.storage
    private var user = FirebaseAuth.getInstance().currentUser
    private val email = user!!.email
    private val binding get() = _binding!!
    private lateinit var userPreferencesRepository: UserPreferencesRepository

    private val cameraProviderResult =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if(it) {
                abrirTelaDePreview()
            } else {
                Snackbar.make(
                    binding.root,
                    "Não foi possível iniciar a câmera",
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }

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
        binding.userPicture.setOnClickListener {
            cameraProviderResult.launch(android.Manifest.permission.CAMERA)
        }
    }

    private fun abrirTelaDePreview() {
        val intentCameraPreview = Intent(requireContext(), CameraPreviewActivity::class.java)
        startActivity(intentCameraPreview)
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
                    val storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(
                        document.data["foto"].toString()
                    )
                    val imageView: ImageView = binding.userPicture

                    storageReference.downloadUrl.addOnSuccessListener {
                        val imageUrl = it.toString()
                        Glide.with(requireContext())
                            .load(imageUrl)
                            .circleCrop()
                            .into(imageView)
                    }.addOnFailureListener {
                        Log.e("Imagem", "A imagem não pôde ser recuperada")
                    }

//                    Glide.with(requireContext())
//                        .load(gsReference)
//                        .into(imageView)

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