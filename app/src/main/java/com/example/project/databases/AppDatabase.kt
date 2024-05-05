package com.example.project.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.project.databases.entities.ParkingE
import com.example.project.databases.entities.Reservation
import com.example.project.databases.entities.User

@Database(entities = [Reservation::class, User::class, ParkingE::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun getUserDao(): UserDao
    abstract fun getReservationDao(): ReservationDao
    abstract fun getParkingDao(): ParkingDao

    companion object {
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            synchronized(this){
                var instance= INSTANCE
                if (instance == null) {
                    instance=
                        Room.databaseBuilder(context, AppDatabase::class.java,name="app_db").build()
                    INSTANCE =instance
                }
                return instance
            }
        }
    }
}

