package com.example.project.databases

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.project.databases.entities.User

@Dao
interface UserDao {
    @Query("select * from users where firstName LIKE :firstName")
    fun getUsersByFirstName(firstName:String):List<User>

    @Query("select * from users where id = :id")
    fun getUsersByID(id:Int): User?

    @Insert
    fun addUser(user: User)
    @Delete
    fun deleteUser(user: User)
    @Query("SELECT * FROM users WHERE email = :email")
    fun getUserByEmail(email: String): User?

}