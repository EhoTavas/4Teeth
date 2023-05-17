package br.com.ForTeethDentalCare.dataStore

import android.media.Image
import br.com.ForTeethDentalCare.R

data class Dentist(var nome: String,
                   var telefone: String,
                   var email: String,
                   var senha: String,
                   var curriculo: String,
                   var cep1: String,
                   var endereco1: String,
                   var cep2: String,
                   var endereco2: String,
                   var cep3: String,
                   var endereco3: String,
                   var fcmToken: String,
                   var status: Float,
                   var picture: Int = R.drawable.ic_man_red)
