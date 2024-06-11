package com.example.project.repositories

import android.util.Log
import com.example.project.databases.DataClasses.Credentials
import com.example.project.databases.DataClasses.RegisterRequest
import com.example.project.databases.DataClasses.TokenRequest
import com.example.project.databases.Endpoint
import com.example.project.databases.UserDao
import com.example.project.databases.entities.User
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.tasks.await

class UserRepository(private val endpoint: Endpoint, private val userDao: UserDao) {

    suspend fun register(user: RegisterRequest) = endpoint.register(user)

    suspend fun login(user: Credentials) = endpoint.login(user)

    suspend fun update(user: TokenRequest) = endpoint.update(user)

    fun addUser(user: User) = userDao.addUser(user)

    fun getUsersByID(id:Int) =  userDao.getUsersByID(id)

    suspend fun checkEmail(email: Map<String, String>) = endpoint.checkEmail(email)
    suspend fun getUserByEmail(email: String) = endpoint.getUserByEmail(mapOf("email" to email))


    suspend fun generateToken(): String? {
        return try {
            val token = FirebaseMessaging.getInstance().token.await()
            Log.v("FCM Token new", token)
            token
        } catch (e: Exception) {
            Log.w("FCM Token", "Failed to get FCM token", e)
            null
        }
    }

}