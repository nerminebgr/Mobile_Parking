package com.example.project

import SplashScreen
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

import com.example.project.databases.AppDatabase
import com.example.project.databases.ParkingDao
import com.example.project.databases.ReservationDao
import com.example.project.databases.UserDao
import com.example.project.databases.entities.Parking
import com.example.project.databases.entities.User
import com.example.project.interfaces.DestinationPath
import com.example.project.interfaces.DisplayHome
import com.example.project.interfaces.DisplayReservations
import com.example.project.interfaces.DisplaySignIn
import com.example.project.interfaces.DisplaySignUP
import com.example.project.interfaces.auth
import com.example.project.interfaces.getData
import com.example.project.models.ParkingModel
import com.example.project.models.ResevationModel
import com.example.project.models.UserModel
import com.example.project.ui.theme.ProjectTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val reservationModel: ResevationModel by viewModels {
        ResevationModel.Factory((application as MyApplication).reservationRepository)
    }

    private val parkingModel: ParkingModel by viewModels {
        ParkingModel.Factory((application as MyApplication).parkingRepository)
    }

    private val userModel: UserModel by viewModels {
        UserModel.Factory((application as MyApplication).userRepository)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProjectTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //auth()
                    //DisplayParkingsList(getData())
                    val context = LocalContext.current
                    //val db = AppDatabase.getInstance(context)
                    //val reservationDao = db.getReservationDao()
                    //val userDao = db.getUserDao()
                    //val parkingDao=db.getParkingDao()

                    //addData(parkingDao,userDao)

                    BottomNavScreen(rememberNavController(), reservationModel,parkingModel,userModel)

                }
            }
        }
    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BottomNavScreen(navController: NavHostController, reservationModel: ResevationModel,parkingModel: ParkingModel,userModel: UserModel) {
    val currentRoute = currentRoute(navController)
    val context = LocalContext.current
    val isLoggedIn = true
    Scaffold(
        bottomBar = {
            BottomAppBar {
                NavigationBar {
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                        label = { Text("Home") },
                        selected = currentRoute == DestinationPath.Home.route,
                        onClick = { navController.navigate(DestinationPath.Home.route) }
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.AccountCircle, contentDescription = "Login") },
                        label = { Text("Login") },
                        selected = currentRoute == DestinationPath.SignIn.route,
                        onClick = { navController.navigate(DestinationPath.SignIn.route) }
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.List, contentDescription = "Reservations") },
                        label = { Text("Reservations") },
                        selected = currentRoute == DestinationPath.Reservations.route,
                        onClick = { navController.navigate(DestinationPath.Reservations.route) }
                    )

                }
            }
        },

        ) {
            NavHost(navController = navController, startDestination = DestinationPath.Splash.route) {
                composable(DestinationPath.Home.route) { DisplayHome(navController = navController,reservationModel,parkingModel, userModel) }
                composable(DestinationPath.SignIn.route) { DisplaySignIn(navController = navController) }
                composable(DestinationPath.SignUp.route) { DisplaySignUP(navController) }
                composable(DestinationPath.Splash.route) { SplashScreen(navController)}
                composable(DestinationPath.Reservations.route) {
                    if (isLoggedIn) {
                        DisplayReservations(navController = navController,reservationModel,parkingModel, userModel)
                    }
                    else {
                        DisplaySignIn(navController)
                    }
                }
            }
        }

}


@Composable
fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}

/*
@SuppressLint("CoroutineCreationDuringComposition")
fun addData(parkingModel: ParkingModel, userModel: UserModel){
    val parkinglist:List<Parking> = getData()
    CoroutineScope(Dispatchers.IO).launch {
        parkingModel.deleteParkings()

        val user = User(firstName="firstName",lastName="lastName")
        userModel.addUser(user)

        for(i in 0 .. 4){
            var parking = ParkingE(
                nom=parkinglist[i].nom,
                commune=parkinglist[i].commune,
                adresse=parkinglist[i].adresse,
                prix=parkinglist[i].prix,
                dispo=parkinglist[i].dispo,
                distance=parkinglist[i].distance,
                places=parkinglist[i].places,
                img=parkinglist[i].img
            )
            parkingModel.addParking(parking)
        }

    }
}*/
