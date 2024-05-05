package com.example.project.databases

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.project.databases.entities.ParkingE

@Dao
interface ParkingDao {
    @Query("select * from parkings where nom LIKE :name")
    fun getParkingByName(name:String):List<ParkingE>

    @Query("select * from parkings where id =:id")
    fun getParkingById(id:Int): ParkingE

    @Query("select * from parkings")
    fun getParkings():List<ParkingE>

    @Insert
    fun addParking(vararg parkingE: ParkingE)
    @Query("delete from parkings")
    fun deleteParkings()
}