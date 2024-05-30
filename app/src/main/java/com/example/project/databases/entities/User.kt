package com.example.project.databases.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName ="users" )
data class User (
    @PrimaryKey(autoGenerate = true)
    val id:Int=0,
    var email:String,
    var password:String,
    var firstname:String,
    var lastname:String,
    var token: String? = null,
)

