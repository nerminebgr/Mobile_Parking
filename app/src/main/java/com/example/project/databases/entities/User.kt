package com.example.project.databases.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName ="users" )
data class User (
    @PrimaryKey(autoGenerate = true)
    val id:Int=0,
    var firstName:String,
    var lastName:String
)

