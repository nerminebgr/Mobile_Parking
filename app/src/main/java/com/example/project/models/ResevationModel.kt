package com.example.project.models

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.project.databases.DataClasses.Credentials
import com.example.project.databases.DataClasses.ReservationDetails
import com.example.project.databases.entities.Parking
import com.example.project.databases.entities.Reservation
import com.example.project.interfaces.DestinationPath
import com.example.project.repositories.ParkingRepository
import com.example.project.repositories.ReservationRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date
import java.util.*

class ResevationModel (private val reservationRepository: ReservationRepository): ViewModel() {

    private val timer = Timer()
    private val notificationTask = object : TimerTask() {
        override fun run() {
            CoroutineScope(Dispatchers.Main).launch {
                getNotification()
            }
        }
    }

    init {
        // Schedule the task to run every 1 minute
        // timer.scheduleAtFixedRate(notificationTask, 0, 60000)
        timer.scheduleAtFixedRate(notificationTask, 0, 1800000)//1800000= 30 minutes
    }

    var loading = mutableStateOf(false)
    var error = mutableStateOf(false)
    var allUserReservations = mutableStateOf(listOf<Reservation>())
    var currentReservation = mutableStateOf<ReservationDetails?>(null)
    var id=mutableStateOf(1)


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
            //getAllReservationsRemote(userID)
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

    suspend fun getNotification() = withContext(Dispatchers.Main){
        Log.v("getNotification", "is called")
        loading.value = true
        error.value = false
        viewModelScope.launch {
            withContext(Dispatchers.IO) {

                val response = reservationRepository.getReservationNotifications()
                loading.value = false

                if(response.isSuccessful){
                    val responseBody  = response.body()
                    if(responseBody !=null){
                        // well done
                    }
                }
                else {
                    error.value = true
                }
            }
        }

    }
    fun getReservationDetails(id: Int,local:Int) {
        loading.value = true
        error.value = false
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                if(local==0){
                    val response = reservationRepository.getReservationDetails(id)
                    loading.value = false

                    if(response.isSuccessful){
                        val responseBody  = response.body()
                        if(responseBody !=null){

                            currentReservation.value = responseBody

                        }
                    }
                    else {
                        error.value = true
                    }
                }
                else { // local
                    currentReservation.value = reservationRepository.getReservationDetailsById(id)
                }

            }
        }
    }


    fun addReservation(reservation: Reservation, navController: NavHostController) {
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
                            id.value = responseBody.id
                            Log.v("id resercationnnnn = ",id.value.toString())
                            withContext(Dispatchers.Main) {
                                navController.navigate(DestinationPath.ConfirmReservation.getRoute(0,id.value))
                            }
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