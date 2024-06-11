package com.example.project.repositories

import com.example.project.databases.DataClasses.ReservationDetails
import com.example.project.databases.DataClasses.ReservationWithDetails
import com.example.project.databases.Endpoint
import com.example.project.databases.ParkingDao
import com.example.project.databases.ReservationDao
import com.example.project.databases.entities.Parking
import com.example.project.databases.entities.Reservation
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ReservationRepository (private val endpoint: Endpoint, private val reservationDao: ReservationDao) {

    suspend fun getAllUserReservations(userId:Int) = endpoint.getAllUserReservations(userId)

    suspend fun getReservationDetails(id:Int) = endpoint.getReservationDetails(id)
    suspend fun addReservation(reservation: Reservation) = endpoint.addReservation(reservation)

    suspend fun getReservationNotifications()= endpoint.getNotification()


    fun count(userId:Int) = reservationDao.count(userId)

    fun getUserReservations(userId:Int) = reservationDao.getUserReservations(userId)

    fun addLocalReservation(reservation: Reservation) = reservationDao.addReservation(reservation)

    fun getReservationByDate(date: Date) = reservationDao.getReservationByDate(date)

    fun getReservationDetailsById(reservationId: Int): ReservationDetails {
        val reservationWithDetails = reservationDao.getReservationWithDetails(reservationId)

        // Define the input and output date formats
        val inputFormat1 = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val inputFormat2 = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.getDefault())
        val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        // Parse the date_entree from the database and convert it to the desired format
        var dateEntree: String
        try {
            val parsedDate = inputFormat1.parse(reservationWithDetails.reservation.date_entree.toString())
            dateEntree = outputFormat.format(parsedDate)
        } catch (e: ParseException) {
            try {
                val parsedDate = inputFormat2.parse(reservationWithDetails.reservation.date_entree.toString())
                dateEntree = outputFormat.format(parsedDate)
            } catch (e: ParseException) {
                dateEntree = reservationWithDetails.reservation.date_entree.toString()
            }
        }

        return ReservationDetails(
            id = reservationWithDetails.reservation.id,
            date_entree = dateEntree,
            heure_entree = reservationWithDetails.reservation.heure_entree,
            heure_sortie = reservationWithDetails.reservation.heure_sortie,
            code_qr = null, // Assuming code_qr is not available in your data
            createdAt = null, // Assuming createdAt is not available in your data
            updatedAt = null, // Assuming updatedAt is not available in your data
            conducteurId = reservationWithDetails.reservation.conducteurId,
            parkingId = reservationWithDetails.reservation.parkingId,
            conducteur = reservationWithDetails.conducteur,
            parking = reservationWithDetails.parking
        )
    }

}