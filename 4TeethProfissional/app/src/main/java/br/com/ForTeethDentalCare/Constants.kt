package br.com.ForTeethDentalCare

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.navigation.Navigation
import br.com.ForTeethDentalCare.dataStore.Emergency
import br.com.ForTeethDentalCare.screens.menu.MenuActivity
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.functions.ktx.functions
import com.google.firebase.ktx.Firebase
import com.google.gson.GsonBuilder
import kotlinx.coroutines.tasks.await

object Constants {
    private lateinit var functions: FirebaseFunctions
    private val gson = GsonBuilder().enableComplexMapKeySerialization().create()
    private lateinit var auth: FirebaseAuth

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

    suspend fun patientsList(): List<Emergency> {

        functions = Firebase.functions("southamerica-east1")

        return try {
            val result = functions.getHttpsCallable("getPatientData")
                .call().await()
            Log.d("paciente", "passouaqui")
            result.data as List<Emergency>
        } catch (e: Exception) {
            println("Erro ao chamar a função: $e")
            emptyList()
        }

        //return listOf(
        //    Emergency(name = "Loren"),//, distance = "50km"),
        //    Emergency(name = "Luan"),//, distance = "15km"),
        //    Emergency(name = "Luiza"),//, distance = "5km"),
        //    Emergency(name = "Duda"),//, distance = "95km"),
        //    Emergency(name = "Matheus"),//, distance = "80km"),
        //)
    }

    fun answerEmergency(
        check: Boolean,
        emergencyId: String,
        view: View,
        context: Context
    ): Task<CustomResponse> {
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
                    Navigation.findNavController(view).navigate(R.id.emergencyFragment_to_MenuFragment)
                } else {
                    val intentMenuActivity = Intent(context, MenuActivity::class.java)
                    context.startActivity(intentMenuActivity)
                }
            } else {
                Snackbar.make(view, "Ocorreu um erro ao executar a ação", Snackbar.LENGTH_LONG).show()
            }
        }
        return task
    }
}