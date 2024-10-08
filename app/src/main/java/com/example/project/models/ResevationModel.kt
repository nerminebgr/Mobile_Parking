package com.example.project.models

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.project.databases.entities.Parking
import com.example.project.databases.entities.Reservation
import com.example.project.repositories.ParkingRepository
import com.example.project.repositories.ReservationRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date

class ResevationModel (private val reservationRepository: ReservationRepository): ViewModel() {

    var loading = mutableStateOf(false)
    var error = mutableStateOf(false)
    var allUserReservations = mutableStateOf(listOf<Reservation>())


    /*fun getAllUserReservations(userID:Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                allUserReservations.value  = reservationRepository.getUserReservations(userID)
            }
        }
    }*/


    fun getAllUserReservations(userID:Int){
        CoroutineScope(Dispatchers.IO).launch {
            allUserReservations.value = listOf<Reservation>()
            val count = reservationRepository.count(userID)
            if (count == 0) {
                getAllReservationsRemote(userID)
            } else {
                getAllReservationsLocal(userID)
            }
        }
    }
    fun getAllReservationsLocal(userID:Int){
        CoroutineScope(Dispatchers.IO).launch {
            allUserReservations.value = reservationRepository.getUserReservations(userID)
        }
    }

    fun getAllReservationsRemote(userID:Int) {
        if(allUserReservations.value.isEmpty()) {
            loading.value = true
            error.value = false
            viewModelScope.launch {
                withContext(Dispatchers.IO) {

                    val response = reservationRepository.getAllUserReservations(userID)
                    loading.value = false

                    if(response.isSuccessful){

                        val list = response.body()
                        if(list!=null){
                            allUserReservations.value = list
                        }

                    }
                    else {
                        error.value = true
                    }
                }
            }
        }
    }

    fun addReservation(reservation: Reservation) {
        loading.value = true
        error.value = false
        viewModelScope.launch {
            withContext(Dispatchers.IO) {

                val response = reservationRepository.addReservation(reservation)
                loading.value = false

                if(response.isSuccessful){
                    val responseBody  = response.body()
                    if(responseBody !=null){
                        CoroutineScope(Dispatchers.IO).launch {
                            addLocalReservation(reservation)
                        }
                        //val responseMessage: String? = responseBody["message"]
                        //if(responseMessage!=null) message.value = responseMessage
                    }
                }
                else {
                    error.value = true
                }
            }
        }
    }

    fun addLocalReservation(reservation: Reservation){
        reservationRepository.addLocalReservation(reservation)

    }

    fun getReservationByDate(date: Date) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                reservationRepository.getReservationByDate(date)
            }
        }
    }



    class Factory(private val reservationRepository: ReservationRepository ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ResevationModel(reservationRepository) as T
        }
    }

}