package com.example.project.repositories

import com.example.project.databases.ParkingDao
import com.example.project.databases.ReservationDao
import com.example.project.databases.entities.ParkingE
import com.example.project.databases.entities.Reservation
import java.util.Date

class ReservationRepository (private val reservationDao: ReservationDao) {

    fun addReservation(reservation: Reservation) = reservationDao.addReservation(reservation)

    fun getUserReservations(userId:Int) = reservationDao.getUserReservations(userId)

    fun getReservationByDate(date: Date) = reservationDao.getReservationByDate(date)


}