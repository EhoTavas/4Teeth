package br.com.ForTeethDentalCare

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.ForTeethDentalCare.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSignUp.setOnClickListener {
            val criarConta = Intent (this, CadastroUm::class.java)
            startActivities(arrayOf(criarConta))
        }

        binding.btnLogin.setOnClickListener {
            val entrarConta = Intent (this, LogIn::class.java)
            startActivities(arrayOf(entrarConta))
        }
    }
}