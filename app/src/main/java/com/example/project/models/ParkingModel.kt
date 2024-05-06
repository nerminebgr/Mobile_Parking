package com.example.project.models

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.project.databases.entities.Parking

import com.example.project.repositories.ParkingRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ParkingModel(private val parkingRepository: ParkingRepository): ViewModel() {

    var allParkings = mutableStateOf(listOf<Parking>())
    var loading = mutableStateOf(false)
    var error = mutableStateOf(false)

    fun getAllParkings(){
        CoroutineScope(Dispatchers.IO).launch {
            val count = parkingRepository.count()
            if (count == 0) {
                getAllParkingsRemote()
            } else {
                getAllParkingsLocal()
            }
        }
    }
    fun getAllParkingsLocal(){
        CoroutineScope(Dispatchers.IO).launch {
            allParkings.value = parkingRepository.getParkings()
        }
    }

    fun getAllParkingsRemote() {
        if(allParkings.value.isEmpty()) {
            loading.value = true
            error.value = false
            viewModelScope.launch {
                withContext(Dispatchers.IO) {

                    val response = parkingRepository.getAllParkings()
                    loading.value = false

                    if(response.isSuccessful){

                        val list = response.body()
                        if(list!=null){

                            list.forEach { parking ->
                                CoroutineScope(Dispatchers.IO).launch {
                                // Perform your desired operation for each item
                                    parkingRepository.addParking(parking)
                                }
                            }
                            allParkings.value = list
                        }

                    }
                    else {
                        error.value = true
                    }
                }
            }
        }
    }


    /*fun addParking(parkingE: ParkingE) {
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
    }*/


    class Factory(private val parkingRepository: ParkingRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ParkingModel(parkingRepository) as T
        }
    }

}