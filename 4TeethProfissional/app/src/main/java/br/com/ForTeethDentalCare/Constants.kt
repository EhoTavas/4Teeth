package br.com.ForTeethDentalCare

import android.util.Log
import br.com.ForTeethDentalCare.dataStore.Patient
import com.google.android.gms.tasks.Task
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.functions.ktx.functions
import com.google.firebase.ktx.Firebase
import com.google.gson.GsonBuilder
import kotlinx.coroutines.tasks.await

object Constants {
    private lateinit var functions: FirebaseFunctions
    private val gson = GsonBuilder().enableComplexMapKeySerialization().create()

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

    suspend fun patientsList(): List<Patient> {

        functions = Firebase.functions("southamerica-east1")

        return try {
            val result = functions.getHttpsCallable("getPatientData")
                .call().await()
            Log.d("paciente", "passouaqui")
            result.data as List<Patient>
        } catch (e: Exception) {
            println("Erro ao chamar a função: $e")
            emptyList()
        }

        //return listOf(
        //    Patient(name = "Loren"),//, distance = "50km"),
        //    Patient(name = "Luan"),//, distance = "15km"),
        //    Patient(name = "Luiza"),//, distance = "5km"),
        //    Patient(name = "Duda"),//, distance = "95km"),
        //    Patient(name = "Matheus"),//, distance = "80km"),
        //)
    }
}