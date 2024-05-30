package com.example.project.service

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.project.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


@SuppressLint("MissingFirebaseInstanceTokenRefresh")
class MyFcmService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        val messageBody = remoteMessage.notification?.body
        if (!messageBody.isNullOrEmpty()) {
            Log.v("remoteMessage.notification", messageBody)
        } else {
            Log.v("remoteMessage.notification", "Notification body is null or empty")
        }

        // Créez le canal de notification si nécessaire
        NotificationUtils.createNotificationChannel(applicationContext)

        val builder = NotificationCompat.Builder(applicationContext, NotificationUtils.CHANNEL_ID)
            .setSmallIcon(R.drawable.notification)
            .setContentTitle("A Coming Reservation")
            .setContentText(messageBody)
            .setStyle(NotificationCompat.BigTextStyle().bigText(messageBody))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(0, builder.build())
    }
}
