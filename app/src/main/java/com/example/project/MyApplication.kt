package com.example.project

import android.app.Application
import com.example.project.databases.AppDatabase
import com.example.project.repositories.ParkingRepository
import com.example.project.repositories.ReservationRepository
import com.example.project.repositories.UserRepository

class MyApplication:Application() {

    private val dataBase by lazy { AppDatabase.getInstance(this) }
    private val userDao by lazy { dataBase.getUserDao() }
    val userRepository by lazy { UserRepository(userDao) }

    private val reservationDao by lazy { dataBase.getReservationDao() }
    val reservationRepository by lazy { ReservationRepository(reservationDao) }

    private val parkingDao by lazy { dataBase.getParkingDao() }
    val parkingRepository by lazy { ParkingRepository(parkingDao) }
}