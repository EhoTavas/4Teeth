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

class SignUpFragment : Fragment() {


    private val TAG = "SignUpFragment"
    private lateinit var auth: FirebaseAuth
    private lateinit var functions: FirebaseFunctions
    private val gson = GsonBuilder().enableComplexMapKeySerialization().create()

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.icNavBackLogin.setOnClickListener {
            findNavController().navigate(R.id.SignUp_to_Login)
        }

        binding.btnContinuar.setOnClickListener {
            // criar a conta...
            signUpNewAccount(
                binding.EtNome.text.toString(),
                binding.EtTelefone.text.toString(),
                binding.EtEmail.text.toString(),
                binding.EtPassword.text.toString(),
                (activity as MainActivity).getFcmToken()
            );
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

    private fun signUpNewAccount(nome: String, telefone: String, email: String, password: String, fcmToken: String) {
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
                    updateUserProfile(nome, telefone, email, user!!.uid, fcmToken)
                        .addOnCompleteListener(requireActivity()) { res ->
                            // conta criada com sucesso.
                            if(res.result.status == "SUCCESS"){
                                hideKeyboard()
                                Snackbar.make(requireView(),"Conta cadastrada! Pode fazer o login!",Snackbar.LENGTH_LONG).show()
                                findNavController().navigate(R.id.SignUp_to_Login)
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

    private fun updateUserProfile(nome: String, telefone: String, email: String, uid: String, fcmToken: String) : Task<CustomResponse>{
        // chamar a function para atualizar o perfil.
        functions = Firebase.functions("southamerica-east1")

        // Create the arguments to the callable function.
        val data = hashMapOf(
            "nome" to nome,
            "telefone" to telefone,
            "email" to email,
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
    /*
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentSignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnContinuar.setOnClickListener {

            val name = findViewById<EditText>(R.id.EtNome)
            val email = findViewById<EditText>(R.id.EtEmail)
            val phone = findViewById<EditText>(R.id.EtTelefone)
            val password = findViewById<EditText>(R.id.EtPassword)
            val passwordConfirm = findViewById<EditText>(R.id.EtPasswordConfirm)

            if (
                name.text.isEmpty() ||
                email.text.isEmpty() ||
                phone.text.isEmpty() ||
                password.text.isEmpty() ||
                passwordConfirm.text.isEmpty()
            ) {
                findViewById<TextView>(R.id.TvErro).text = "Preencha todos os campos"
            } else {
                if (password.text.toString() == passwordConfirm.text.toString()) {
                    val continuarCadastro = Intent(this, CadastroEnderecoFragment::class.java)
                    startActivities(arrayOf(continuarCadastro))
                } else {
                    findViewById<TextView>(R.id.TvErro).text = "As senhas não coincidem"
                }
            }
        }

        binding.icNavegarVoltar.setOnClickListener {
            val voltarTela = Intent (this, MainActivity ::class.java)
            startActivities(arrayOf(voltarTela))
        }
    }
    */
}