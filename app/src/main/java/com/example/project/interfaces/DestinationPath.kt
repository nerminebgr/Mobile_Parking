package com.example.project.interfaces

sealed class DestinationPath(val route : String) {
    object Home: Destination("home")
    object SignIn: Destination("singIn")
    object SignUp: Destination("SingUp")
    object Splash:DestinationPath("splash")

    object Reservations: Destination("reservations/{userId}")

    object ParkingDetails:DestinationPath("parking_details/{parkingId}"){
        fun getRoute (id:Int) = "parking_details/$id"
    }

}