package br.com.ForTeethDentalCare

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.ForTeethDentalCare.databinding.ActivityCadastroEnderecoBinding
import com.google.firebase.functions.FirebaseFunctions
import kotlinx.coroutines.*
import java.io.*
import java.net.*

class CadastroEndereco : AppCompatActivity() {

    private lateinit var binding: ActivityCadastroEnderecoBinding
    private lateinit var functions: FirebaseFunctions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastroEnderecoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //binding.icAddAddress.setOnClickListener {

        //}

        binding.btnConfirmar.setOnClickListener {
            val finalizarCadastro = Intent(this, MainActivity::class.java)
            startActivities(arrayOf(finalizarCadastro))
        }
        binding.icNavegarVoltar.setOnClickListener {
            val voltarPagina = Intent (this, CadastroUm::class.java)
            startActivities(arrayOf(voltarPagina))
        }
    }
}