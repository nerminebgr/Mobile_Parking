package com.example.project.repositories

import com.example.project.databases.Endpoint
import com.example.project.databases.ParkingDao
import com.example.project.databases.entities.Parking

/*class ParkingRepository(private val parkingDao: ParkingDao) {

    fun addParking(parkingE: ParkingE) = parkingDao.addParking(parkingE)

    fun getParkings() = parkingDao.getParkings()

    fun deleteParkings() = parkingDao.deleteParkings()

}*/

class ParkingRepository(private val endpoint: Endpoint,private val parkingDao: ParkingDao) {

    suspend fun getAllParkings() = endpoint.getAllParkings()

    suspend fun getParkingDetail(parkingId: Int) = endpoint.getParkingDetail(parkingId)

    fun addParking(parkingE: Parking) = parkingDao.addParking(parkingE)

    fun getParkings() = parkingDao.getParkings()

    fun count() = parkingDao.count()


}