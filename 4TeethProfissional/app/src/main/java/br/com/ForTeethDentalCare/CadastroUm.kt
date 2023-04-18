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

            val name = findViewById<EditText>(R.id.EtNome)
            val email = findViewById<EditText>(R.id.EtEmail)
            val phone = findViewById<EditText>(R.id.EtTelefone)
            val password = findViewById<EditText>(R.id.EtPassword)
            val passwordConfirm = findViewById<EditText>(R.id.EtPasswordConfirm)

            if (
                name.text.isEmpty() ||
                email.text.isEmpty() ||
                phone.text.isEmpty() ||
                password.text.isEmpty() ||
                passwordConfirm.text.isEmpty()
            ) {
                findViewById<TextView>(R.id.TvErro).text = "Preencha todos os campos"
            } else {
                if (password.text.toString() == passwordConfirm.text.toString()) {
                    val continuarCadastro = Intent(this, CadastroEndereco::class.java)
                    startActivities(arrayOf(continuarCadastro))
                } else {
                    findViewById<TextView>(R.id.TvErro).text = "As senhas não coincidem"
                }
            }
        }

        binding.icNavegarVoltar.setOnClickListener {
            val voltarTela = Intent (this, MainActivity ::class.java)
            startActivities(arrayOf(voltarTela))
        }

    }
}