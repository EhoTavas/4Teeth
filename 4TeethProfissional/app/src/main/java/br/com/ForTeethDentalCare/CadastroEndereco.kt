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
    private lateinit var cepEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastroEnderecoBinding.inflate(layoutInflater)
        setContentView(binding.root)
/*
        binding.icAddAddress.setOnClickListener {
            val layout = findViewById<LinearLayout>(R.id.LayoutAddresses)
            val editText = EditText(this)
            ViewCompat.setStyle(editText, R.style.labelsMainScreen_LinearLayout)
            editText.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            editText.hint = "Digite aqui"
            editText.inputType = InputType.TYPE_CLASS_TEXT
            layout.addView(editText)
        }
*/
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