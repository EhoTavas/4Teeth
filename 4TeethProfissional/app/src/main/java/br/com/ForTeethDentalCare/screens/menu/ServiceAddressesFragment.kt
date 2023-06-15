package br.com.ForTeethDentalCare.screens.menu

import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.isInvisible
import br.com.ForTeethDentalCare.Constants
import br.com.ForTeethDentalCare.CustomResponse
import br.com.ForTeethDentalCare.databinding.FragmentServiceAddressesBinding
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.google.gson.GsonBuilder

class ServiceAddressesFragment : Fragment() {
    private var _binding: FragmentServiceAddressesBinding? = null
    private lateinit var auth: FirebaseAuth
    private lateinit var functions: FirebaseFunctions
    private val gson = GsonBuilder().enableComplexMapKeySerialization().create()
    private lateinit var status: Task<CustomResponse>
    private val db = Firebase.firestore
    private val storage = Firebase.storage
    private var user = FirebaseAuth.getInstance().currentUser
    private val email = user!!.email
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentServiceAddressesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val auth = FirebaseAuth.getInstance()

        loadUserData()

        binding.etCep2.visibility = View.INVISIBLE
        binding.etEnderecoNumero2.visibility = View.INVISIBLE
        binding.etComplemento2.visibility = View.INVISIBLE
        binding.etRua2.visibility = View.INVISIBLE
        binding.etBairro2.visibility = View.INVISIBLE
        binding.etCidade2.visibility = View.INVISIBLE
        binding.etEstado2.visibility = View.INVISIBLE
        binding.etCep3.visibility = View.INVISIBLE
        binding.etEnderecoNumero3.visibility = View.INVISIBLE
        binding.etComplemento3.visibility = View.INVISIBLE
        binding.etRua3.visibility = View.INVISIBLE
        binding.etBairro3.visibility = View.INVISIBLE
        binding.etCidade3.visibility = View.INVISIBLE
        binding.etEstado3.visibility = View.INVISIBLE


        binding.btnChangeData.setOnClickListener{
            binding.etCep1.isEnabled = true
            binding.etEnderecoNumero1.isEnabled = true
            binding.etComplemento1.isEnabled = true
            binding.etRua1.isEnabled = true
            binding.etBairro1.isEnabled = true
            binding.etCidade1.isEnabled = true
            binding.etEstado1.isEnabled = true
        }

        binding.btnsaveData.setOnClickListener{

            binding.etCep1.isEnabled = false
            binding.etEnderecoNumero1.isEnabled = false
            binding.etComplemento1.isEnabled = false
            binding.etRua1.isEnabled = false
            binding.etBairro1.isEnabled = false
            binding.etCidade1.isEnabled = false
            binding.etEstado1.isEnabled = false

            loadUserData()
        }

    }

    private fun loadUserData() {
        val collection = db.collection("Endereco")
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
                if ((document.data["dentista"].toString() == email) && (document.data["tipo"].toString() == "1")) {

                    binding.etCep1.setText (document.data["Cep"].toString())
                    binding.etEnderecoNumero1.setText (document.data["numero"].toString())
                    binding.etComplemento1.setText(document.data["complemento"].toString())
                    binding.etRua1.setText (document.data["rua"].toString())
                    binding.etBairro1.setText (document.data["bairro"].toString())
                    binding.etCidade1.setText(document.data["cidade"].toString())
                    binding.etEstado1.setText(document.data["estado"].toString())
                    binding.btnsaveData.isInvisible = true

                }

                if ((document.data["dentista"].toString() == email) && (document.data["tipo"].toString() == "2")) {

                    binding.etCep2.setText (document.data["Cep"].toString())
                    binding.etEnderecoNumero2.setText (document.data["numero"].toString())
                    binding.etComplemento2.setText(document.data["complemento"].toString())
                    binding.etRua2.setText (document.data["rua"].toString())
                    binding.etBairro2.setText (document.data["bairro"].toString())
                    binding.etCidade2.setText(document.data["cidade"].toString())
                    binding.etEstado2.setText(document.data["estado"].toString())

                    binding.etCep2.visibility = View.VISIBLE
                    binding.etEnderecoNumero2.visibility = View.VISIBLE
                    binding.etComplemento2.visibility = View.VISIBLE
                    binding.etRua2.visibility = View.VISIBLE
                    binding.etBairro2.visibility = View.VISIBLE
                    binding.etCidade2.visibility = View.VISIBLE
                    binding.etEstado2.visibility = View.VISIBLE

                }

                if ((document.data["dentista"].toString() == email) && (document.data["tipo"].toString() == "3")) {

                    binding.etCep3.setText (document.data["Cep"].toString())
                    binding.etEnderecoNumero3.setText (document.data["numero"].toString())
                    binding.etComplemento3.setText(document.data["complemento"].toString())
                    binding.etRua3.setText (document.data["rua"].toString())
                    binding.etBairro3.setText (document.data["bairro"].toString())
                    binding.etCidade3.setText(document.data["cidade"].toString())
                    binding.etEstado3.setText(document.data["estado"].toString())

                    binding.etCep3.visibility = View.VISIBLE
                    binding.etEnderecoNumero3.visibility = View.VISIBLE
                    binding.etComplemento3.visibility = View.VISIBLE
                    binding.etRua3.visibility = View.VISIBLE
                    binding.etBairro3.visibility = View.VISIBLE
                    binding.etCidade3.visibility = View.VISIBLE
                    binding.etEstado3.visibility = View.VISIBLE

                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}