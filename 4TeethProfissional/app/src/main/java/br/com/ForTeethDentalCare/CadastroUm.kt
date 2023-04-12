package br.com.ForTeethDentalCare

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.ForTeethDentalCare.databinding.ActivityCadastroUmBinding
import br.com.ForTeethDentalCare.databinding.ActivityMainBinding

class CadastroUm : AppCompatActivity() {
    private lateinit var binding: ActivityCadastroUmBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastroUmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnContinuar.setOnClickListener {
            val continuarCadastro = Intent (this, CadastroEndereco::class.java)
            startActivities(arrayOf(continuarCadastro))
        }

        binding.icNavegarVoltar.setOnClickListener {
            val voltarTela = Intent (this, MainActivity ::class.java)
            startActivities(arrayOf(voltarTela))
        }

    }
}