package com.example.project.models

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.project.databases.DataClasses.Credentials
import com.example.project.databases.DataClasses.RegisterRequest
import com.example.project.databases.entities.User
import com.example.project.repositories.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class UserModel(private val userRepository: UserRepository): ViewModel() {

    var allUsers = mutableStateOf(listOf<User>())
    var users = mutableStateOf(listOf<User>())
    var authUser = mutableStateOf<User?>(null)
    var message = mutableStateOf("string")
    var loading = mutableStateOf(false)
    var error = mutableStateOf(false)
    var isLoggedIn = mutableStateOf(false)

    fun registerUser(user: RegisterRequest) {
            loading.value = true
            error.value = false
            viewModelScope.launch {
                withContext(Dispatchers.IO) {

                    val response = userRepository.register(user)
                    loading.value = false

                    if(response.isSuccessful){
                        val responseBody  = response.body()
                        if(responseBody !=null){

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

    fun loginUser(user: Credentials) {
        loading.value = true
        error.value = false
        viewModelScope.launch {
            withContext(Dispatchers.IO) {

                val response = userRepository.login(user)
                loading.value = false

                if(response.isSuccessful){
                    val responseBody  = response.body()
                    if(responseBody !=null){

                            authUser.value = responseBody
                            isLoggedIn.value = true
                            CoroutineScope(Dispatchers.IO).launch {
                                if(userRepository.getUsersByID(responseBody.id)==null) {
                                    userRepository.addUser(responseBody)
                                }

                            }


                    }
                }
                else {
                    error.value = true
                }
            }
        }
    }

    fun logout(user : User){
        isLoggedIn.value = false
        authUser.value = null

    }

    fun addUser(user: User) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                userRepository.addUser(user)
            }
        }
    }


    class Factory(private val userRepository: UserRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return UserModel(userRepository) as T
        }
    }

}