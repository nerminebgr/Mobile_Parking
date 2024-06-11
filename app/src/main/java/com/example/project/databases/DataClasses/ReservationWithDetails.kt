package com.example.project.databases.DataClasses

import androidx.room.Embedded
import androidx.room.Relation
import com.example.project.databases.entities.Reservation
import com.example.project.databases.entities.User
import com.example.project.databases.entities.Parking
data class ReservationWithDetails(
    @Embedded val reservation: Reservation,
    @Relation(
        parentColumn = "conducteurId",
        entityColumn = "id"
    )
    val conducteur: User,
    @Relation(
        parentColumn = "parkingId",
        entityColumn = "id"
    )
    val parking: Parking
)
