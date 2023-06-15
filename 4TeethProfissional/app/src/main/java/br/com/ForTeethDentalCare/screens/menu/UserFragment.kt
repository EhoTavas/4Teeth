package br.com.ForTeethDentalCare.screens.menu

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.navigation.fragment.findNavController
import br.com.ForTeethDentalCare.Constants
import br.com.ForTeethDentalCare.CustomResponse
import br.com.ForTeethDentalCare.R
import br.com.ForTeethDentalCare.dataStore.UserPreferencesRepository
import br.com.ForTeethDentalCare.databinding.FragmentUserBinding
import br.com.ForTeethDentalCare.screens.login.LoginActivity
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.Task
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
    private lateinit var status: Task<CustomResponse>
    private val db = Firebase.firestore
    private val storage = Firebase.storage
    private var user = FirebaseAuth.getInstance().currentUser
    private val email = user!!.email
    private val binding get() = _binding!!
    private lateinit var userPreferencesRepository: UserPreferencesRepository

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val fineLocationGranted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false
            val coarseLocationGranted = permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false
            if (fineLocationGranted && coarseLocationGranted) {
                // Ambas as permissões foram concedidas
                findNavController().navigate(R.id.userFragment_to_mapsFragment)
            } else {
                // Ao menos uma das permissões não foi concedida
                Toast.makeText(context, "Permissão de localização é necessária para abrir a tela.", Toast.LENGTH_SHORT).show()
            }
        }

    private val cameraProviderResult =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if(it) {
                cameraScreen()
            } else {
                Snackbar.make(binding.root, "Não foi possível iniciar a câmera", Snackbar.LENGTH_LONG).show()
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
        val statusBtn = binding.BtnSwitch

        loadUserData()

        binding.btnLocation.setOnClickListener {
            requestPermissionLauncher.launch(arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ))
        }
        binding.btnLogout.setOnClickListener {
            auth.signOut()
            startActivity(Intent(requireContext(), LoginActivity::class.java))
        }
        binding.userPicture.setOnClickListener {
            cameraProviderResult.launch(Manifest.permission.CAMERA)
        }

        statusBtn.setOnCheckedChangeListener { _, isChecked ->
            statusBtn.isEnabled = false
            status = if (isChecked) {
                Constants.updateDentistData("1", "status")
            } else {
                Constants.updateDentistData("0", "status")
            }
            status.addOnSuccessListener {
                statusBtn.isEnabled = true
            }.addOnFailureListener {e ->
                statusBtn.isEnabled = true
                Log.w("Firebase", "erro ao alterar status", e)
            }
        }
    }

    private fun cameraScreen() {
        val intentCameraPreview = Intent(requireContext(), CameraActivity::class.java)
        startActivity(intentCameraPreview)
    }

    private fun loadUserData() {
        val collection = db.collection("Dentista")
        collection.addSnapshotListener { value, e ->
            if (e != null) {
                Log.e("FirestoreListener", "Erro ao recuperar dados do dentista", e)
                return@addSnapshotListener
            }

            if (_binding == null) {
                Log.e("Binding", "Erro ao recuperar o binding")
                return@addSnapshotListener
            }

            for (document in value!!) {
                if (document.data["email"].toString() == email) {
                    if (document.data["foto"] != null) {
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
                    }
                    if (document.data["status"].toString() == "1") {
                        binding.BtnSwitch.isChecked = true
                    }
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