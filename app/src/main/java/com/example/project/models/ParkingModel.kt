package com.example.project.models

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.project.databases.entities.ParkingE
import com.example.project.repositories.ParkingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ParkingModel(private val parkingRepository: ParkingRepository): ViewModel() {

    var allParkings = mutableStateOf(listOf<ParkingE>())

    fun getAllParkings() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                allParkings.value  = parkingRepository.getParkings()
            }
        }
    }


    fun addParking(parkingE: ParkingE) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                parkingRepository.addParking(parkingE)
            }
        }
    }

    fun deleteParkings() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                parkingRepository.deleteParkings()
                allParkings.value  = listOf<ParkingE>()
            }
        }
    }


    class Factory(private val parkingRepository: ParkingRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ParkingModel(parkingRepository) as T
        }
    }

}