package com.example.project.interfaces

sealed class Destination(val route : String) {
    object Home: Destination("home")
    object Authentication: Destination("login")
    object Reservations: Destination("reservations/{userId}") {
        fun createRoute(userId:Int) = "reservations/$userId"
    }
}