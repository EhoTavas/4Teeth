package br.com.ForTeethDentalCare.screens.menu

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.google.firebase.storage.StorageReference
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import br.com.ForTeethDentalCare.R
import br.com.ForTeethDentalCare.dataStore.UserPreferencesRepository
import br.com.ForTeethDentalCare.databinding.ActivityMenuBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.functions.ktx.functions
import com.google.firebase.messaging.ktx.messaging

class MenuActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMenuBinding
    lateinit var storage: FirebaseStorage
    private val functions = Firebase.functions
    private final lateinit var navController: NavController
    private lateinit var userPreferencesRepository: UserPreferencesRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userPreferencesRepository = UserPreferencesRepository.getInstance(this)

        storeFcmToken()

        val uid = userPreferencesRepository.uid
        val fcmToken = userPreferencesRepository.fcmToken

        updateFcmToken(uid, fcmToken)

        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        storage = Firebase.storage
        var storageRef = storage.reference
        var imagesRef: StorageReference? = storageRef.child("DentistUserPictures")

        val navController = findNavController(R.id.nav_host_fragment_content_menu)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_menu)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.appbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_menu)
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.profileButton -> {
                navController.navigate(R.id.userFragment)
                true
            }
            android.R.id.home -> {
                super.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun storeFcmToken() {
        Firebase.messaging.token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@OnCompleteListener
            }
            userPreferencesRepository.updateFcmToken(task.result)
        })
    }

    private fun updateFcmToken(uid: String, token: String) {
        val data = hashMapOf(
            "uid" to uid,
            "fcmToken" to token
        )

        functions
            .getHttpsCallable("updateFcmToken")
            .call(data)
            .addOnSuccessListener {
                Log.d("FCM", "FCM token updated successfully!")
            }
            .addOnFailureListener {
                // Aqui você pode manipular o erro
                Log.d("FCM", "Failed to update FCM token: ${it.message}")
            }
    }

    override fun onBackPressed() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_menu)
        val currentFragment = navHostFragment?.childFragmentManager?.fragments?.get(0)

        if (currentFragment is MenuFragment) {
            AlertDialog.Builder(this)
                .setMessage("Deseja mesmo sair?")
                .setPositiveButton("Sim") { _, _ -> super.onBackPressed() }
                .setNegativeButton("Não", null)
                .show()
        } else { super.onBackPressed() }
    }


    override fun isDestroyed(): Boolean {
        // TODO: encontrar uma forma de colocar um aviso "deseja mesmo sair do aplicativo?"
        return super.isDestroyed()
    }
}