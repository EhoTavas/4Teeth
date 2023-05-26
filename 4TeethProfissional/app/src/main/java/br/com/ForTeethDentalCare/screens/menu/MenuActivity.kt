package br.com.ForTeethDentalCare.screens.menu

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import br.com.ForTeethDentalCare.databinding.ActivityMainBinding
import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.pm.PackageManager
import android.content.Context
import android.os.Build
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.ui.navigateUp
import br.com.ForTeethDentalCare.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseApp
import com.google.firebase.appcheck.FirebaseAppCheck
import com.google.firebase.appcheck.debug.DebugAppCheckProviderFactory
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import br.com.ForTeethDentalCare.dataStore.UserPreferencesRepository
import br.com.ForTeethDentalCare.dataStore.Dentist

class MenuActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var userPreferencesRepository: UserPreferencesRepository

    public var dentist: Dentist = Dentist("", "", "", "", "", "", "", "", "", "", "", "", "1")

    private fun prepareFirebaseAppCheckDebug(){
        // Ajustando o AppCheck para modo depuração.
        FirebaseApp.initializeApp(this)
        val firebaseAppCheck = FirebaseAppCheck.getInstance()
        firebaseAppCheck.installAppCheckProviderFactory(
            DebugAppCheckProviderFactory.getInstance()
        )
    }

    fun storeUserId(uid: String){
        userPreferencesRepository.uid = uid
    }

    private fun storeFcmToken() {
        Firebase.messaging.token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@OnCompleteListener
            }
            // guardar esse token.
            userPreferencesRepository.fcmToken = task.result
        })
    }

    fun getFcmToken(): String {
        return userPreferencesRepository.fcmToken
    }

    // Declare the launcher at the top of your Activity/Fragment:
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (!isGranted) {
            // mostrar o fragment
            navController.navigate(R.id.Login_to_notificationsAreDisabledFragment)
        }
    }

    private fun askNotificationPermission() {
        // This is only necessary for API level >= 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED
            ) {
                // FCM SDK (and your app) can post notifications.
            } else {
                // Directly ask for the permission
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
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

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
        // disponibilizando o token (que deve ser colocado lá no APP CHECK do Firebase).
        prepareFirebaseAppCheckDebug()
        // guardar o token FCM pois iremos precisar.
        storeFcmToken()
        // invocar as permissões para notificar.
        askNotificationPermission()
    }

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        menuInflater.inflate(R.id., menu)
//        return true
//    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}