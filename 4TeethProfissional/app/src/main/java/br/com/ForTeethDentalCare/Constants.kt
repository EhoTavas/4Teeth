package br.com.ForTeethDentalCare

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.navigation.Navigation
import br.com.ForTeethDentalCare.screens.login.LoginActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.functions.ktx.functions
import com.google.firebase.ktx.Firebase
import com.google.gson.GsonBuilder

object Constants {
    private lateinit var functions: FirebaseFunctions
    private val gson = GsonBuilder().enableComplexMapKeySerialization().create()
    private lateinit var auth: FirebaseAuth
    private var user = FirebaseAuth.getInstance().currentUser
    val uid = user!!.uid
    private var userData: String = ""

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

//    fun updateDentistData(data: String, field: String, view: View, context: Context) : Task<CustomResponse> {
//        functions = Firebase.functions("southamerica-east1")
//        auth = Firebase.auth
//
//        val dentistData = hashMapOf(
//            "uid" to auth.currentUser!!.uid,
//            field to data
//        )
//
//        val task = functions
//            .getHttpsCallable("updateUserProfile")
//            .call(dentistData)
//            .continueWith { task ->
//                val result = gson.fromJson((task.result?.data as String), CustomResponse::class.java)
//                result
//            }
//
//        task.addOnCompleteListener { res ->
//
//        }
//    }

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

}