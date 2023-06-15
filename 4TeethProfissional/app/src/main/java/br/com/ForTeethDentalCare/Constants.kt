package br.com.ForTeethDentalCare

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.Location
import android.util.Log
import android.view.View
import androidx.navigation.Navigation
import br.com.ForTeethDentalCare.screens.login.LoginActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.functions.ktx.functions
import com.google.firebase.ktx.Firebase
import com.google.gson.GsonBuilder
import java.time.LocalDateTime

object Constants {
    private lateinit var functions: FirebaseFunctions
    private val gson = GsonBuilder().enableComplexMapKeySerialization().create()
    private lateinit var auth: FirebaseAuth

    fun updateDentistData(data: String, field: String) : Task<CustomResponse> {
        functions = Firebase.functions("southamerica-east1")
        auth = Firebase.auth

        val dentistData = hashMapOf(
            "uid" to auth.currentUser!!.uid,
            field to data
        )

        val task = functions
            .getHttpsCallable("updateUserProfile")
            .call(dentistData)
            .continueWith { task ->
                val result = gson.fromJson((task.result?.data as String), CustomResponse::class.java)
                result
            }
        return task
    }


    fun updateAdress(data: String, field: String) : Task<CustomResponse> {
        functions = Firebase.functions("southamerica-east1")
        auth = Firebase.auth

        val dentistData = hashMapOf(
            "uid" to auth.currentUser!!.uid,
            field to data
        )

        val task = functions
            .getHttpsCallable("setUserAddresses")
            .call(dentistData)
            .continueWith { task ->
                val result = gson.fromJson((task.result?.data as String), CustomResponse::class.java)
                result
            }
        return task
    }

    fun answerEmergency(
        check: Boolean,
        emergencyId: String,
        view: View,
        context: Context,
        latitude: Double,
        longitude: Double
    ) : Task<CustomResponse> {

        functions = Firebase.functions("southamerica-east1")
        auth = Firebase.auth

        val status = if (check) "1" else "0"

        val emergencyData = hashMapOf(
            "dentist" to auth.currentUser!!.uid,
            "emergency" to emergencyId,
            "status" to status,
            "latitude" to latitude,
            "longitude" to longitude
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
                Log.e("REQ EMERGENCY", "Ocorreu um erro ao enviar as informações")
            }
        }
        return task
    }

}