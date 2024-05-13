package com.example.project.repositories

import com.example.project.databases.DataClasses.Credentials
import com.example.project.databases.DataClasses.RegisterRequest
import com.example.project.databases.Endpoint
import com.example.project.databases.UserDao
import com.example.project.databases.entities.User

class UserRepository(private val endpoint: Endpoint, private val userDao: UserDao) {

    suspend fun register(user: RegisterRequest) = endpoint.register(user)

    suspend fun login(user: Credentials) = endpoint.login(user)

    fun addUser(user: User) = userDao.addUser(user)

    fun getUsersByID(id:Int) =  userDao.getUsersByID(id)

}