package br.com.ForTeethDentalCare

import br.com.ForTeethDentalCare.dataStore.Patient
import com.google.android.gms.tasks.Task
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.functions.ktx.functions
import com.google.firebase.ktx.Firebase
import com.google.gson.GsonBuilder

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

    fun patientsList(): List<Patient> {
        return listOf(
            Patient(name = "Loren Tavolaro", distance = "50km"),
            Patient(name = "Luan Magri", distance = "15km"),
            Patient(name = "Luiza Límoli", distance = "5km"),
            Patient(name = "Maria Eduarda", distance = "95km"),
            Patient(name = "Matheus Taveira", distance = "80km"),
        )
    }
}