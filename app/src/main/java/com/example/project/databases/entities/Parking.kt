package com.example.project.databases.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName ="parkings" )
data class Parking(
    @PrimaryKey(autoGenerate = true)
    var id: Int=0,
    val nom: String,
    val commune: String,
    val image: String,
    val description: String,
    val adresse: String,
    val prix: String,
    val dispo: Boolean = false,  // Default value set to false
    val places: Int = 0,  // Default value set to 0
    val longitude: Double,
    val latitude: Double
)
