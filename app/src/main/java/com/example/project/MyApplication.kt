package com.example.project

import android.app.Application
import com.example.project.databases.AppDatabase
import com.example.project.databases.Endpoint
import com.example.project.repositories.ParkingRepository
import com.example.project.repositories.ReservationRepository
import com.example.project.repositories.UserRepository
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.project.service.FcmNotificationWorker
import java.util.concurrent.TimeUnit

class MyApplication : Application() {


    override fun onCreate() {
        super.onCreate()

        val periodicWorkRequest = PeriodicWorkRequestBuilder<FcmNotificationWorker>(1, TimeUnit.MINUTES)
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "fcm_notification_worker",
            ExistingPeriodicWorkPolicy.KEEP,
            periodicWorkRequest
        )
    }

    private val dataBase by lazy { AppDatabase.getInstance(this) }
    private val endpoint by lazy { Endpoint.createEndpoint() }
    private val reservationDao by lazy { dataBase.getReservationDao() }
    val reservationRepository by lazy { ReservationRepository(endpoint, reservationDao) }
    private val parkingDao by lazy { dataBase.getParkingDao() }
    val parkingRepository by lazy { ParkingRepository(endpoint, parkingDao) }
    private val userDao by lazy { dataBase.getUserDao() }
    val userRepository by lazy { UserRepository(endpoint, userDao) }
}