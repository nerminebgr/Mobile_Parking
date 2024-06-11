package com.example.project.models

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.project.databases.DataClasses.Credentials
import com.example.project.databases.DataClasses.RegisterRequest
import com.example.project.databases.DataClasses.TokenRequest
import com.example.project.databases.entities.User
import com.example.project.repositories.UserRepository
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class UserModel( private val userRepository: UserRepository): ViewModel() {

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


    fun addToken(user: TokenRequest) {
        loading.value = true
        error.value = false
        viewModelScope.launch {
            withContext(Dispatchers.IO) {

                val token = userRepository.generateToken()
                if (token != null){
                    user.token = token
                    val response = userRepository.update(user)
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

    }

    fun addUser(user: User) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                userRepository.addUser(user)
            }
        }
    }

    suspend fun checkEmail(email: String): Boolean {
        val response = userRepository.checkEmail(mapOf("email" to email))
        return if (response.isSuccessful) {
            Log.v("checkV","success")
            response.body()?.get("exists") ?: false
        } else {
            Log.v("checkV","false")
            false
        }
    }

    fun getUserByEmail(email: String) {
        loading.value = true
        error.value = false
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val response = userRepository.getUserByEmail(email)
                loading.value = false

                if (response.isSuccessful) {
                    Log.v("byEmail","successful")
                    val responseBody = response.body()
                    if (responseBody != null) {
                        // Update the authUser
                        authUser.value = responseBody
                        isLoggedIn.value = true
                        CoroutineScope(Dispatchers.IO).launch {
                            if(userRepository.getUsersByID(responseBody.id)==null) {
                                userRepository.addUser(responseBody)
                            }

                        }
                    }
                } else {
                    Log.v("byEmail","not success")
                    error.value = true
                }
            }
        }
    }


    class Factory(private val userRepository: UserRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return UserModel(userRepository) as T
        }
    }

}