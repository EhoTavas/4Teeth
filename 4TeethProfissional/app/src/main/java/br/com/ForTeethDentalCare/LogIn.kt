package br.com.ForTeethDentalCare

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.ForTeethDentalCare.databinding.ActivityCadastroUmBinding
import br.com.ForTeethDentalCare.databinding.ActivityLogInBinding
import br.com.ForTeethDentalCare.databinding.ActivityMainBinding
import com.google.android.gms.tasks.Task
import com.google.firebase.functions.FirebaseFunctions

class LogIn : AppCompatActivity() {
    private lateinit var binding: ActivityLogInBinding
    private lateinit var functions: FirebaseFunctions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.icNavegarVoltar.setOnClickListener {
            val voltarTela = Intent (this, MainActivity::class.java)
            startActivities(arrayOf(voltarTela))
        }
    }

}