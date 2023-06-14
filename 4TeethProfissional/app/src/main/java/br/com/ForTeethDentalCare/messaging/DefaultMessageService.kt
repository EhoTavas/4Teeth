package br.com.ForTeethDentalCare.messaging

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.media.RingtoneManager
import android.util.Log
import androidx.core.app.NotificationCompat
import br.com.ForTeethDentalCare.screens.login.LoginActivity
import br.com.ForTeethDentalCare.R
import br.com.ForTeethDentalCare.dataStore.UserPreferencesRepository
import br.com.ForTeethDentalCare.screens.emergency.RequestedEmergencyActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class DefaultMessageService : FirebaseMessagingService() {
    private lateinit var userPreferencesRepository: UserPreferencesRepository

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        val msgData = remoteMessage.data
        Log.d("mensagem", "Message data payload: ${remoteMessage.data}")
        showNotification(msgData)
        remoteMessage.notification?.let {
            Log.d("notification body", "Message Notification Body: ${it.body}")
        }
    }

    override fun onNewToken(token: String) {
        Log.d("DefaultMessageService", "Refreshed token: $token")
        sendRegistrationToServer(token)
    }

    private fun sendRegistrationToServer(token: String?) {
        Log.d("DefaultMessageService", "sendRegistrationTokenToServer($token)")
        // Guardar apenas no DataStore. Vamos manipular isso sempre no Login ou criação de conta.
        userPreferencesRepository = UserPreferencesRepository.getInstance(this)
        userPreferencesRepository.updateFcmToken(token!!)

    }

    private fun showNotification(data: Map<String, String>) {
        val intent = Intent(this, RequestedEmergencyActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtra("name", data["name"])
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_MUTABLE)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, getString(R.string.default_notification_channel_id))
            .setSmallIcon(R.drawable.logo_4teeth)
            .setContentTitle(data["name"])//(getString(R.string.fcm_default_title_message))
            .setContentText(data["text"])
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())
    }
}