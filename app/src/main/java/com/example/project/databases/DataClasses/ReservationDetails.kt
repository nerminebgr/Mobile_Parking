package com.example.project.databases.DataClasses

import com.example.project.databases.entities.User
import com.example.project.databases.entities.Parking

data class ReservationDetails(
    val id: Int,
    val date_entree: String,
    val heure_entree: String,
    val heure_sortie: String,
    val code_qr: String?,
    val createdAt: String?,
    val updatedAt: String?,
    val conducteurId: Int,
    val parkingId: Int,
    val conducteur: User,
    val parking: Parking
)