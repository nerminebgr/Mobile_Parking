package com.example.project.models

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.project.databases.entities.ParkingE
import com.example.project.databases.entities.Reservation
import com.example.project.repositories.ParkingRepository
import com.example.project.repositories.ReservationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date

class ResevationModel (private val reservationRepository: ReservationRepository): ViewModel() {

    var allUserReservations = mutableStateOf(listOf<Reservation>())

    fun getAllUserReservations(userID:Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                allUserReservations.value  = reservationRepository.getUserReservations(userID)
            }
        }
    }


    fun addReservation(reservation: Reservation) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                reservationRepository.addReservation(reservation)
            }
        }
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