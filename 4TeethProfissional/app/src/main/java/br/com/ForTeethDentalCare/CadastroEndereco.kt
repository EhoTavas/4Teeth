package br.com.ForTeethDentalCare

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import br.com.ForTeethDentalCare.databinding.ActivityCadastroEnderecoBinding
import com.google.android.gms.tasks.Task
import com.google.firebase.functions.FirebaseFunctions

class CadastroEndereco : AppCompatActivity() {

    private lateinit var binding: ActivityCadastroEnderecoBinding
    private lateinit var functions: FirebaseFunctions

    private fun addDentist(): Task<String> {
        // Create the arguments to the callable function.
        val data = hashMapOf(
            "Nome" to "Luan",
            "Email" to "luan@gmail.com",
            "Telefone" to "(19)91125-0750",
            "Endereco1" to "Rua São Jorge",
            "Endereco2" to "Rua São José",
            "Endereco3" to "Rua São Joaquim",
            "MiniCurriculo" to "SimSimSimSimSimSalabim"
        )

        return functions
            .getHttpsCallable("addDentist")
            .call(data)
            .continueWith { task ->
                // This continuation runs on either success or failure, but if the task
                // has failed then result will throw an Exception which will be
                // propagated down.
                val result = task.result?.data as String
                result
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastroEnderecoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnConfirmar.setOnClickListener {
            addDentist().addOnSuccessListener {
                val finalizarCadastro = Intent(this, MainActivity::class.java)
                startActivities(arrayOf(finalizarCadastro))
            }.addOnFailureListener {
                val minhaLabel = findViewById<TextView>(R.id.TvEndereco2)
                minhaLabel.setText("Minha mensagem")
            }
        }
        binding.icNavegarVoltar.setOnClickListener {
            val voltarPagina = Intent (this, CadastroUm::class.java)
            startActivities(arrayOf(voltarPagina))
        }
    }
}