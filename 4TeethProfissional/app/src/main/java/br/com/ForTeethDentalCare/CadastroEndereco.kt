package br.com.ForTeethDentalCare

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.ForTeethDentalCare.databinding.ActivityCadastroEnderecoBinding

class CadastroEndereco : AppCompatActivity() {

    private lateinit var binding: ActivityCadastroEnderecoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastroEnderecoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnConfirmar.setOnClickListener {
            val finalizarCadastro = Intent (this, MainActivity::class.java)
            startActivities(arrayOf(finalizarCadastro))
        }
        binding.icNavegarVoltar.setOnClickListener {
            val voltarPagina = Intent (this, CadastroUm::class.java)
            startActivities(arrayOf(voltarPagina))
        }
    }
}