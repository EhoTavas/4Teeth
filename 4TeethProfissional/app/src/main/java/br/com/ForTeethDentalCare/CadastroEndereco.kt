package br.com.ForTeethDentalCare

import android.content.Intent
import android.os.Bundle
import android.text.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import br.com.ForTeethDentalCare.databinding.ActivityCadastroEnderecoBinding
import androidx.core.view.ViewCompat
import com.google.firebase.functions.FirebaseFunctions

class CadastroEndereco : AppCompatActivity() {

    private lateinit var binding: ActivityCadastroEnderecoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastroEnderecoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnConfirmar.setOnClickListener {
            val finalizarCadastro = Intent(this, LogIn::class.java)
            startActivities(arrayOf(finalizarCadastro))
        }
        binding.icNavegarVoltar.setOnClickListener {
            val voltarPagina = Intent (this, CadastroUm::class.java)
            startActivities(arrayOf(voltarPagina))
        }
    }
}