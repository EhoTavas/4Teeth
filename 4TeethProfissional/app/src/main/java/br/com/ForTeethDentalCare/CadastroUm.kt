package br.com.ForTeethDentalCare

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import br.com.ForTeethDentalCare.databinding.ActivityCadastroUmBinding
import br.com.ForTeethDentalCare.databinding.ActivityMainBinding

class CadastroUm : AppCompatActivity() {
    private lateinit var binding: ActivityCadastroUmBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastroUmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnContinuar.setOnClickListener {

            var name = findViewById<EditText>(R.id.EtNome)
            var email = findViewById<EditText>(R.id.EtEmail)
            var phone = findViewById<EditText>(R.id.EtTelefone)
            var password = findViewById<EditText>(R.id.EtPassword)
            var passwordConfirm = findViewById<EditText>(R.id.EtPasswordConfirm)

            if (
                name.text.isNotEmpty() &&
                email.text.isNotEmpty() &&
                phone.text.isNotEmpty() &&
                password.text.isNotEmpty() &&
                passwordConfirm.text.isNotEmpty()
            ) {
                val continuarCadastro = Intent(this, CadastroEndereco::class.java)
                startActivities(arrayOf(continuarCadastro))
            } else {
                findViewById<TextView>(R.id.TvErro).text = "Preencha todos os campos"
            }

            if (password.text == passwordConfirm.text) {
                val continuarCadastro = Intent(this, CadastroEndereco::class.java)
                startActivities(arrayOf(continuarCadastro))
            } else {
                findViewById<TextView>(R.id.TvErro).text = "As senhas não coincidem"
            }
        }

        binding.icNavegarVoltar.setOnClickListener {
            val voltarTela = Intent (this, MainActivity ::class.java)
            startActivities(arrayOf(voltarTela))
        }

    }
}