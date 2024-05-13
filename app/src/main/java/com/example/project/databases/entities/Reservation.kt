package com.example.project.databases.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "reservations"
    , foreignKeys = [
        ForeignKey(entity= User::class,
            parentColumns=["id"],childColumns = ["conducteurId"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE ),
        ForeignKey(entity= Parking::class,
            parentColumns=["id"],childColumns = ["parkingId"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE ),
    ])
data class Reservation (
    @PrimaryKey(autoGenerate = true)
    val id:Int=0,
    var conducteurId:Int,
    var parkingId:Int,
    var date_entree: Date,
)



