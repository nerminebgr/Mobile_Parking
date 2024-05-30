package com.example.project.service

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.RemoteMessage

class FcmNotificationWorker(context: Context, params: WorkerParameters) : Worker(context, params) {
    override fun doWork(): Result {
        val messageBody = "This is a notification sent every 30 minutes using FCM."

        // Create a data payload object
        val data = mapOf("message" to messageBody)

        // Send the notification using FCM
        FirebaseMessaging.getInstance().send(
            RemoteMessage.Builder("816939922928@fcm.googleapis.com")
                .setData(data)
                .build()
        )

        return Result.success()
    }
}