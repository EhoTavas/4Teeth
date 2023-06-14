package br.com.ForTeethDentalCare.dataStore

import com.google.firebase.Timestamp

data class Emergency(
    val name: String,
    val phone: String,
    val id: String,
    val mouth: String,
    val document: String,
    val child: String,
    val time: Timestamp
    //val distance: String
)
