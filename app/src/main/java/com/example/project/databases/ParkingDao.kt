package com.example.project.databases

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.project.databases.entities.Parking

@Dao
interface ParkingDao {
    @Query("select * from parkings where nom LIKE :name")
    fun getParkingByName(name:String):List<Parking>

    @Query("select * from parkings where id =:id")
    fun getParkingById(id:Int): Parking

    @Query("select * from parkings")
    fun getParkings():List<Parking>

    @Query("select count(*) from parkings")
    fun count(): Int

    @Insert
    fun addParking(vararg parkingE: Parking)
    @Query("delete from parkings")
    fun deleteParkings()
}