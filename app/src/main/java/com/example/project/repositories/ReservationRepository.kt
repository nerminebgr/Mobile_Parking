package com.example.project.repositories

import com.example.project.databases.Endpoint
import com.example.project.databases.ParkingDao
import com.example.project.databases.ReservationDao
import com.example.project.databases.entities.Parking
import com.example.project.databases.entities.Reservation
import java.util.Date

class ReservationRepository (private val endpoint: Endpoint, private val reservationDao: ReservationDao) {

    suspend fun getAllUserReservations(userId:Int) = endpoint.getAllUserReservations(userId)

    fun addReservation(reservation: Reservation) = reservationDao.addReservation(reservation)
    fun count() = reservationDao.count()

    fun getUserReservations(userId:Int) = reservationDao.getUserReservations(userId)


    fun getReservationByDate(date: Date) = reservationDao.getReservationByDate(date)


}