package com.example.project.interfaces

sealed class Destination(val route : String) {
    object Home: Destination("home")
    object SingIn: Destination("singIn")
    object SingUp: Destination("SingUp")
    object Reservations: Destination("reservations/{userId}") {
        fun createRoute(userId:Int) = "reservations/$userId"
    }
}