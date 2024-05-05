package com.example.project

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.example.project.databases.AppDatabase

import org.junit.Before

lateinit var mDataBase: AppDatabase
@Before
fun initDB() {
    val appContext = InstrumentationRegistry.getInstrumentation().targetContext
    mDataBase = Room.inMemoryDatabaseBuilder(appContext, AppDatabase::class.java).build()
}