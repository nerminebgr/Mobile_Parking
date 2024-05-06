package com.example.project.databases.DataClasses

data class Parking(
    var id: Int=0,
    val nom: String,
    val commune: String,
    val photo: String,
    val description: String,
    val adresse: String,
    val prix: String,
    val dispo: Boolean = false,  // Default value set to false
    val places: Int = 0,  // Default value set to 0
    val longitude: Double,
    val latitude: Double
)
