package com.example.project.models

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.project.databases.entities.User
import com.example.project.repositories.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class UserModel(private val userRepository: UserRepository): ViewModel() {

    var allUsers = mutableStateOf(listOf<User>())
    var users = mutableStateOf(listOf<User>())


    fun getUsersByFirstName(firstName:String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                users.value = userRepository.getUsersByFirstName(firstName)
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


    class Factory(private val userRepository: UserRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return UserModel(userRepository) as T
        }
    }

}