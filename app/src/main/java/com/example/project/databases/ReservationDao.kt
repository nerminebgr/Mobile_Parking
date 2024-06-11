package com.example.project.databases

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.project.databases.DataClasses.ReservationDetails
import com.example.project.databases.DataClasses.ReservationWithDetails
import com.example.project.databases.entities.Reservation
import java.util.Date

@Dao
interface ReservationDao {
    @Query("select * from reservations where conducteurId= :userId")
    fun getUserReservations(userId:Int):List<Reservation>

    @Query("select * from reservations ")
    fun getAllReservations():List<Reservation>

    @Query("select count(*) from reservations where conducteurId= :userId")
    fun count(userId:Int): Int



    @Query("SELECT * FROM reservations WHERE id = :id")
    fun getReservationById(id: Int): Reservation

    @Query("delete from reservations")
    fun deleteReservations()

    @Query("select * from reservations where date_entree = :date")
    fun getReservationByDate(date:Date):List<Reservation>
    @Insert
    fun addReservation(vararg reservation: Reservation)
    @Update
    fun updateParking(reservation: Reservation)

    @Transaction
    @Query("SELECT * FROM reservations WHERE id = :reservationId")
    fun getReservationWithDetails(reservationId: Int): ReservationWithDetails


}