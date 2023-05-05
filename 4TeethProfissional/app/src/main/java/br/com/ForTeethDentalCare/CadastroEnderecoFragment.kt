package br.com.ForTeethDentalCare

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
import br.com.ForTeethDentalCare.databinding.FragmentSignUpBinding
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import br.com.ForTeethDentalCare.CustomResponse
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.functions.ktx.functions
import com.google.firebase.ktx.Firebase
import com.google.gson.GsonBuilder
import org.json.JSONArray
import org.json.JSONObject
import br.com.ForTeethDentalCare.databinding.FragmentCadastroEnderecoBinding

class CadastroEnderecoFragment : Fragment() {

    private val TAG = "SignUpFragment"
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

//        binding.icNavBackSignUp.setOnClickListener {
//            findNavController().navigate(R.id.Addresses_to_SignUp)
//        }

        binding.btnConfirmar.setOnClickListener {

            (activity as MainActivity).let {
                it.dentist.endereco1 = binding.EtStreet1.text.toString() + ", " + binding.EtNum1.text.toString() + ", " + binding.EtComp1.text.toString() + ", " + binding.EtNh1.text.toString() + ", " + binding.EtCity1.text.toString() + ", " + binding.EtState1.text.toString()
                it.dentist.endereco2 = binding.EtStreet2.text.toString() + ", " + binding.EtNum2.text.toString() + ", " + binding.EtComp2.text.toString() + ", " + binding.EtNh2.text.toString() + ", " + binding.EtCity2.text.toString() + ", " + binding.EtState2.text.toString()
                it.dentist.endereco3 = binding.EtStreet3.text.toString() + ", " + binding.EtNum3.text.toString() + ", " + binding.EtComp3.text.toString() + ", " + binding.EtNh3.text.toString() + ", " + binding.EtCity3.text.toString() + ", " + binding.EtState3.text.toString()
                it.dentist.cep1 = binding.EtCep1.text.toString()
                it.dentist.cep2 = binding.EtCep2.text.toString()
                it.dentist.cep3 = binding.EtCep3.text.toString()
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
                    (activity as MainActivity).dentist.nome,
                    (activity as MainActivity).dentist.telefone,
                    (activity as MainActivity).dentist.email,
                    (activity as MainActivity).dentist.senha,
                    (activity as MainActivity).dentist.curriculo,
                    (activity as MainActivity).dentist.cep1,
                    (activity as MainActivity).dentist.endereco1,
                    (activity as MainActivity).dentist.cep2,
                    (activity as MainActivity).dentist.endereco2,
                    (activity as MainActivity).dentist.cep3,
                    (activity as MainActivity).dentist.endereco3,
                    (activity as MainActivity).getFcmToken(),
                )
            }
        }
    }

    fun JSONObject.toMap(): Map<String, *> = keys().asSequence().associateWith {
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

    private fun signUpNewAccount(nome: String, telefone: String, email: String, password: String, curriculo: String, cep1: String, endereco1: String,cep2: String, endereco2: String,cep3: String, endereco3: String, fcmToken: String) {
        auth = Firebase.auth
        // auth.useEmulator("127.0.0.1", 5001)
        // invocar a função e receber o retorno fazendo Cast para "CustomResponse"

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    (activity as MainActivity).storeUserId(user!!.uid)
                    // atualizar o perfil do usuário com os dados chamando a function.
                    updateUserProfile(nome, telefone, email, curriculo, cep1, endereco1, cep2, endereco2, cep3, endereco3, user!!.uid, fcmToken)
                        .addOnCompleteListener(requireActivity()) { res ->
                            // conta criada com sucesso.
                            if(res.result.status == "SUCCESS"){
                                hideKeyboard()
                                Snackbar.make(requireView(),"Conta cadastrada! Pode fazer o login!",Snackbar.LENGTH_LONG).show()
                                findNavController().navigate(R.id.Addresses_to_Login)
                            }
                        }
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(requireActivity(), "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    // dar seguimento ao tratamento de erro.
                }
            }
    }

    private fun updateUserProfile(nome: String, telefone: String, email: String, curriculo: String, cep1: String, endereco1: String,cep2: String, endereco2: String,cep3: String, endereco3: String, uid: String, fcmToken: String) : Task<CustomResponse>{
        // chamar a function para atualizar o perfil.
        functions = Firebase.functions("southamerica-east1")

        // Create the arguments to the callable function.
        val data = hashMapOf(
            "nome" to nome,
            "telefone" to telefone,
            "email" to email,
            "curriculo" to curriculo,
            "cep1" to cep1,
            "endereco1" to endereco1,
            "cep2" to cep2,
            "endereco2" to endereco2,
            "cep3" to cep3,
            "endereco3" to endereco3,
            "uid" to uid,
            "fcmToken" to fcmToken
        )

        return functions
            .getHttpsCallable("setUserProfile")
            .call(data)
            .continueWith { task ->
                val result = gson.fromJson((task.result?.data as String), CustomResponse::class.java)
                result
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}