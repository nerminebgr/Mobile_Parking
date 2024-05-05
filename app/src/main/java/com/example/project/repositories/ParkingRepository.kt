package com.example.project.repositories

import com.example.project.databases.ParkingDao
import com.example.project.databases.entities.ParkingE


class ParkingRepository(private val parkingDao: ParkingDao) {

    fun addParking(parkingE: ParkingE) = parkingDao.addParking(parkingE)

    fun getParkings() = parkingDao.getParkings()

    fun deleteParkings() = parkingDao.deleteParkings()


}