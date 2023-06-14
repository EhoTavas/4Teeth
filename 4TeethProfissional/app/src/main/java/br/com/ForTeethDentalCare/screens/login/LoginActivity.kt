package br.com.ForTeethDentalCare.screens.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import br.com.ForTeethDentalCare.databinding.ActivityLoginBinding
import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.pm.PackageManager
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.ui.navigateUp
import br.com.ForTeethDentalCare.R
import br.com.ForTeethDentalCare.dataStore.Address
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseApp
import com.google.firebase.appcheck.FirebaseAppCheck
import com.google.firebase.appcheck.debug.DebugAppCheckProviderFactory
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import br.com.ForTeethDentalCare.dataStore.UserPreferencesRepository
import br.com.ForTeethDentalCare.dataStore.Dentist
import br.com.ForTeethDentalCare.screens.menu.MenuActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityLoginBinding
    private lateinit var navController: NavController
    private lateinit var userPreferencesRepository: UserPreferencesRepository

    public var dentist: Dentist = Dentist("", "", "", "", "", "", "1")
    public var address: Address = Address("", "", "", "", "", "", "", "", "", "","", "", "", "", "", "", "", "", "", "","")

    fun storeUserId(uid: String){
        userPreferencesRepository.uid = uid
    }

    private fun storeFcmToken() {
        Firebase.messaging.token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@OnCompleteListener
            }
            userPreferencesRepository.fcmToken = task.result
        })
    }

    fun getFcmToken(): String {
        return userPreferencesRepository.fcmToken
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        //NOTIFICAÇÕES
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        userPreferencesRepository = UserPreferencesRepository.getInstance(this)
        //Cria um canal de notificação
        val channel = NotificationChannel(
            getString(R.string.default_notification_channel_id),
            "new emergency",
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "4teeth emergency"
        }
        channel.enableVibration(true)
        channel.setBypassDnd(true)
        channel.vibrationPattern = longArrayOf(0, 500, 500, 500)
        notificationManager.createNotificationChannel(channel)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_login)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
        storeFcmToken()
    }

    override fun onStart() {
        super.onStart()
        val currentUser = FirebaseAuth.getInstance().currentUser
        if(currentUser != null) {
            startActivity(Intent(this, MenuActivity::class.java))
            finish()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_login)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}