package br.com.ForTeethDentalCare

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.navigation.Navigation
import br.com.ForTeethDentalCare.dataStore.Emergency
import br.com.ForTeethDentalCare.screens.login.LoginActivity
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.functions.ktx.functions
import com.google.firebase.ktx.Firebase
import com.google.gson.GsonBuilder
import kotlinx.coroutines.tasks.await
import org.json.JSONObject

object Constants {
    private lateinit var functions: FirebaseFunctions
    private val gson = GsonBuilder().enableComplexMapKeySerialization().create()
    private lateinit var auth: FirebaseAuth
    private var user = FirebaseAuth.getInstance().currentUser
    val uid = user!!.uid
    private var userData: String = ""

    interface UserCallback {
        fun onCallback(value: String)
    }

    fun sendMessage(textContent: String, fcmToken: String) : Task<CustomResponse> {
        val data = hashMapOf(
            "textContent" to textContent,
            "fcmToken" to fcmToken
        )
        // enviar a mensagem, invocando a function...
        functions = Firebase.functions("southamerica-east1")
        return functions.getHttpsCallable("sendFcmMessage")
            .call(data)
            .continueWith { task ->
                val result =
                    gson.fromJson((task.result?.data as String), CustomResponse::class.java)
                result
            }
    }

    fun answerEmergency(check: Boolean, emergencyId: String, view: View, context: Context) : Task<CustomResponse> {
        functions = Firebase.functions("southamerica-east1")
        auth = Firebase.auth

        val status = if (check) "1" else "0"

        val emergencyData = hashMapOf(
            "dentist" to auth.currentUser!!.uid,
            "emergency" to emergencyId,
            "status" to status
        )

        val task = functions
            .getHttpsCallable("answerEmergency")
            .call(emergencyData)
            .continueWith { task ->
                val result = gson.fromJson((task.result?.data as String), CustomResponse::class.java)
                result
            }

        task.addOnCompleteListener { res ->
            if (res.result.status == "1") {
                if (check) {
                    Navigation.findNavController(view)
                        .navigate(R.id.emergencyFragment_to_MenuFragment)
                } else {
                    val intentLoginActivity = Intent(context, LoginActivity::class.java)
                    context.startActivity(intentLoginActivity)
                }
            } else {
                Log.d("REQ EMERGENCY", "Ocorreu um erro ao enviar as informações")
            }
        }
        return task
    }

    fun getUserData(info: String, callback: UserCallback) {
        val uid = user!!.uid
        val functions = FirebaseFunctions.getInstance("southamerica-east1")

        functions.getHttpsCallable("getUserProfile")
            .call(mapOf("uid" to uid))
            .continueWith { task ->
                if (task.isSuccessful) {
                    val result = task.result.data as String
                    Log.d("getUserData", "Resultado da função: $result")

                    val jsonResult = JSONObject(result)
                    val userDoc = jsonResult.getString("payload")
                    val userJson = JSONObject(userDoc)
                    val userData = userJson.getString(info)
                    Log.d(info, userData)

                    userData
                } else {
                    Log.e("Firebase", "Erro ao chamar a função", task.exception)
                    "erro ao buscar informações"
                }
            }
    }
}