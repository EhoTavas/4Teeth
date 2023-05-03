package br.com.ForTeethDentalCare

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.ForTeethDentalCare.databinding.ActivityCadastroEnderecoBinding

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
        binding.icNavBack.setOnClickListener {
            val voltarPagina = Intent (this, SignUpFragment::class.java)
            startActivities(arrayOf(voltarPagina))
        }
    }
}