package com.example.project.databases.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName ="parkings" )
data class ParkingE(
    @PrimaryKey(autoGenerate = true)
    var id: Int=0,
    var nom:String,
    var commune:String,
    var adresse:String,
    var prix:String,
    var dispo:String,
    var distance:String,
    var places:Int,
    var img:Int
)
