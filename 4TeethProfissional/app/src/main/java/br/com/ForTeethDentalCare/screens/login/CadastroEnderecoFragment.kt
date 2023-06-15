package br.com.ForTeethDentalCare.screens.login

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import br.com.ForTeethDentalCare.CustomResponse
import br.com.ForTeethDentalCare.R
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.functions.ktx.functions
import com.google.firebase.ktx.Firebase
import com.google.gson.GsonBuilder
import org.json.JSONArray
import org.json.JSONObject
import br.com.ForTeethDentalCare.databinding.FragmentCadastroEnderecoBinding

class CadastroEnderecoFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var functions: FirebaseFunctions
    private val gson = GsonBuilder().enableComplexMapKeySerialization().create()
    private var _binding: FragmentCadastroEnderecoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCadastroEnderecoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnConfirmar.setOnClickListener {

            (activity as LoginActivity).let {

                it.address.cep1 = binding.EtCep1.text.toString()
                it.address.enderecoNumero1 = binding.EtNum1.text.toString()
                it.address.complemento1 = binding.EtComp1.text.toString()
                it.address.rua1 = binding.EtStreet1.text.toString()
                it.address.bairro1 = binding.EtNh1.text.toString()
                it.address.cidade1 = binding.EtCity1.text.toString()
                it.address.estado1 = binding.EtState1.text.toString()
                it.address.cep2 = binding.EtCep2.text.toString()
                it.address.enderecoNumero2 = binding.EtNum2.text.toString()
                it.address.complemento2 = binding.EtComp2.text.toString()
                it.address.rua2 = binding.EtStreet2.text.toString()
                it.address.bairro2 = binding.EtNh2.text.toString()
                it.address.cidade2 = binding.EtCity2.text.toString()
                it.address.estado2 = binding.EtState2.text.toString()
                it.address.cep3 = binding.EtCep3.text.toString()
                it.address.enderecoNumero3 = binding.EtNum3.text.toString()
                it.address.complemento3 = binding.EtComp3.text.toString()
                it.address.rua3 = binding.EtStreet3.text.toString()
                it.address.bairro3 = binding.EtNh3.text.toString()
                it.address.cidade3 = binding.EtCity3.text.toString()
                it.address.estado3 = binding.EtState3.text.toString()

                it.dentist.fcmToken = it.getFcmToken()
            }
            if (
                binding.EtStreet1.text.toString() == "" ||
                binding.EtNum1.text.toString() == "" ||
                binding.EtNh1.text.toString() == "" ||
                binding.EtCity1.text.toString() == "" ||
                binding.EtState1.text.toString() == ""
            ) {
                binding.TvError.text = getString(R.string.inputsEmpty)
            } else {
                signUpNewAccount(
                    (activity as LoginActivity).dentist.nome,
                    (activity as LoginActivity).dentist.telefone,
                    (activity as LoginActivity).dentist.email,
                    (activity as LoginActivity).dentist.senha,
                    (activity as LoginActivity).dentist.curriculo,
                    (activity as LoginActivity).dentist.fcmToken,
                    (activity as LoginActivity).dentist.status,
                )

                setAdress(
                    (activity as LoginActivity).address.cep1,
                    (activity as LoginActivity).address.enderecoNumero1,
                    (activity as LoginActivity).address.complemento1,
                    (activity as LoginActivity).address.rua1,
                    (activity as LoginActivity).address.bairro1,
                    (activity as LoginActivity).address.cidade1,
                    (activity as LoginActivity).address.estado1,
                    (activity as LoginActivity).dentist.email,
                )

                if (
                    binding.EtStreet2.text.toString() != "" &&
                    binding.EtNum2.text.toString() != "" &&
                    binding.EtNh2.text.toString() != "" &&
                    binding.EtCity2.text.toString() != "" &&
                    binding.EtState2.text.toString() != ""
                ) {
                    setAdress(
                        (activity as LoginActivity).address.cep2,
                        (activity as LoginActivity).address.enderecoNumero2,
                        (activity as LoginActivity).address.complemento2,
                        (activity as LoginActivity).address.rua2,
                        (activity as LoginActivity).address.bairro2,
                        (activity as LoginActivity).address.cidade2,
                        (activity as LoginActivity).address.estado2,
                        (activity as LoginActivity).dentist.email,
                    )
                }
                if (
                    binding.EtStreet3.text.toString() != "" &&
                    binding.EtNum3.text.toString() != "" &&
                    binding.EtNh3.text.toString() != "" &&
                    binding.EtCity3.text.toString() != "" &&
                    binding.EtState3.text.toString() != ""
                ) {
                    setAdress(
                        (activity as LoginActivity).address.cep3,
                        (activity as LoginActivity).address.enderecoNumero3,
                        (activity as LoginActivity).address.complemento3,
                        (activity as LoginActivity).address.rua3,
                        (activity as LoginActivity).address.bairro3,
                        (activity as LoginActivity).address.cidade3,
                        (activity as LoginActivity).address.estado3,
                        (activity as LoginActivity).dentist.email,
                    )
                }
            }

        }
    }

    fun JSONObject.toMap(): Map<String, *> = keys().asSequence().associateWith { it ->
        when (val value = this[it])
        {
            is JSONArray ->
            {
                val map = (0 until value.length()).associate { Pair(it.toString(), value[it]) }
                JSONObject(map).toMap().values.toList()
            }
            is JSONObject -> value.toMap()
            JSONObject.NULL -> null
            else            -> value
        }
    }

    private fun hideKeyboard(){
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
    }

    private fun signUpNewAccount(
        nome: String, telefone: String,
        email: String, password: String, curriculo: String,
        fcmToken: String, status: String
    ) {
        auth = Firebase.auth

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    Log.d("SignUpFragment", "createUserWithEmail:success")
                    val user = auth.currentUser
                    (activity as LoginActivity).storeUserId(user!!.uid)
                    // atualizar o perfil do usuário com os dados chamando a function.
                    updateUserProfile(nome, telefone, email, curriculo, user.uid, fcmToken, status)
                        .addOnCompleteListener(requireActivity()) { res ->
                            if(res.result.status == "SUCCESS"){
                                hideKeyboard()
                                Snackbar.make(requireView(),"Conta cadastrada! Pode fazer o login!",Snackbar.LENGTH_LONG).show()
                                findNavController().navigate(R.id.Addresses_to_Login)
                            }
                        }

                } else {
                    Log.w("SignUpFragment", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(requireActivity(), "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }

            }

    }

    private fun updateUserProfile(
        nome: String,
        telefone: String,
        email: String,
        curriculo: String,
        uid: String,
        fcmToken: String,
        status: String
    ) : Task<CustomResponse>{
        functions = Firebase.functions("southamerica-east1")

        val data = hashMapOf(
            "nome" to nome, "telefone" to telefone,
            "email" to email, "curriculo" to curriculo,
            "uid" to uid, "fcmToken" to fcmToken,
            "status" to status,
        )

        return functions
            .getHttpsCallable("setUserProfile")
            .call(data)
            .continueWith { task ->
                val result = gson.fromJson((task.result?.data as String), CustomResponse::class.java)
                result
            }
    }

    private fun setAdress(
        cep: String,
        numero: String,
        complemento: String,
        rua: String,
        bairro: String,
        cidade: String,
        estado: String,
        email: String
    ) : Task<CustomResponse>{
        functions = Firebase.functions("southamerica-east1")

        val data = hashMapOf(
            "Cep" to cep, "numero" to numero,
            "complemento" to complemento, "rua" to rua,
            "bairro" to bairro, "cidade" to cidade,
            "estado" to estado, "dentista" to email,
        )
        val task = functions
            .getHttpsCallable("setUserAddresses")
            .call(data)
            .continueWith { task ->
                val result = gson.fromJson((task.result?.data as String), CustomResponse::class.java)
                result
            }

        task.addOnCompleteListener {
            Log.d("ADDRESSES", "Endereços inseridos com sucesso")
        }.addOnFailureListener { exception ->
            Log.e("ADDRESSES", "Falha ao inserir endereços no banco de dados", exception)
        }
        return task
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}