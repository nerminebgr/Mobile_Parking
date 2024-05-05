package com.example.project.interfaces

sealed class DestinationPath(val route : String) {
    object Home: Destination("home")
    object Authentication: Destination("login")
    object Reservations: Destination("reservations/{userId}")
}