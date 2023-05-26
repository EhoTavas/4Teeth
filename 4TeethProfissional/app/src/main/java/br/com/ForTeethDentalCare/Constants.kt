package br.com.ForTeethDentalCare

import android.util.Log
import br.com.ForTeethDentalCare.dataStore.Emergency
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
}