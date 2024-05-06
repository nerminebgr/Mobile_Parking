package com.example.project.interfaces


import android.content.Context
import android.content.SharedPreferences
import java.io.BufferedReader
import java.io.InputStreamReader

object authmanager {private const val PREF_NAME = "auth_pref"
    private const val KEY_EMAIL = "email"
    private const val KEY_PASSWORD = "password"
    private const val KEY_NAME = "name"


    // Initialise les SharedPreferences
    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }
    // Vérifie si l'utilisateur est connecté
    fun isLoggedIn(context: Context): Boolean {
        val sharedPreferences = getSharedPreferences(context)
        return sharedPreferences.contains(KEY_EMAIL) && sharedPreferences.contains(KEY_PASSWORD)
    }
    // Enregistre les informations
    fun saveCredentials(context: Context, email: String, password: String) {
        val sharedPreferences = getSharedPreferences(context)
        val editor = sharedPreferences.edit()
        editor.putString(KEY_EMAIL, email)
        editor.putString(KEY_PASSWORD, password)
        editor.apply()
    }

    fun checkCredentials(email: String, password: String): Boolean {
        val savedEmail = "nour"
        val savedPassword = "0123"
        return email == savedEmail && password == savedPassword

    }

    fun clearCredentials(context: Context) {
        val sharedPreferences = getSharedPreferences(context)
        val editor = sharedPreferences.edit()
        editor.remove(KEY_EMAIL)
        editor.remove(KEY_PASSWORD)
        editor.apply()
    }
    fun createUser(context: Context, name: String, email: String, password: String) {
        val sharedPreferences = getSharedPreferences(context)
        val editor = sharedPreferences.edit()
        editor.putString(KEY_EMAIL, email)
        editor.putString(KEY_PASSWORD, password)
        editor.putString(KEY_NAME, name)
        editor.apply()
    }
}