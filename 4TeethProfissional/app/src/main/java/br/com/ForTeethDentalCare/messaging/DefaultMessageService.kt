package br.com.ForTeethDentalCare.messaging

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.graphics.toColor
import br.com.ForTeethDentalCare.MainActivity
import br.com.ForTeethDentalCare.R
import br.com.ForTeethDentalCare.dataStore.UserPreferencesRepository
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class DefaultMessageService : FirebaseMessagingService() {
    private lateinit var userPreferencesRepository: UserPreferencesRepository

    /***
     * Evento disparado automaticamente
     * quando o FCM envia uma mensagem. Lembrando que este serviço
     * "DefaultMessageService" está registrado no Manifesto como um serviço.
     */
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        val msgData = remoteMessage.data
        val msg = msgData["text"]
        Log.d("mensagem", msg.toString())
        showNotification(msg!!)
    }

    override fun onNewToken(token: String) {
        Log.d("DefaultMessageService", "Refreshed token: $token")
        sendRegistrationToServer(token)
    }

    /**
     * Atualizar o FCM token caso tenha mudado.
     * Sem implementação para este exemplo.
     */
    private fun sendRegistrationToServer(token: String?) {
        Log.d("DefaultMessageService", "sendRegistrationTokenToServer($token)")
        // Guardar apenas no DataStore. Vamos manipular isso sempre no Login ou criação de conta.
        userPreferencesRepository = UserPreferencesRepository.getInstance(this)
        userPreferencesRepository.updateFcmToken(token!!)

    }

    /***
     * Este método cria uma Intent
     * para a activity Main, vinculada a notificação.
     * ou seja, quando acontecer a notificação, se o usuário clicar,
     * abrirá a activity Main.
     * Trabalhar isso para que dependendo da mensagem,
     * você poderá abrir uma ou outra activity
     * ou enviar um parametro na Intent para tratar qual fragment abrir.(desafio para vc fazer)
     */
    private fun showNotification(messageBody: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, getString(R.string.default_notification_channel_id))
            .setSmallIcon(R.drawable.logo_4teeth)
            .setContentTitle(getString(R.string.fcm_default_title_message))
            .setContentText(messageBody)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())
    }
}