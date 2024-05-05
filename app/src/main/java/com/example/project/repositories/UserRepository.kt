package com.example.project.repositories

import com.example.project.databases.UserDao
import com.example.project.databases.entities.User

class UserRepository(private val userDao: UserDao) {


    fun addUser(user: User) = userDao.addUser(user)

    fun getUsersByFirstName(firstname:String) =  userDao.getUsersByFirstName(firstname)

}