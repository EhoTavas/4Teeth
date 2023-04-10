package br.com.ForTeethDentalCare

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.ForTeethDentalCare.databinding.ActivityCadastroEnderecoBinding
import br.com.ForTeethDentalCare.databinding.ActivityMainBinding

class CadastroEndereco : AppCompatActivity() {

    private lateinit var binding: ActivityCadastroEnderecoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastroEnderecoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.icNavegarVoltar.setOnClickListener {
            val continuarCadastro = Intent (this, MainActivity::class.java)
            startActivities(arrayOf(continuarCadastro))
        }
    }
}