package com.example.project.interfaces

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.project.databases.ParkingDao
import com.example.project.databases.entities.Parking
import com.example.project.R
import com.example.project.databases.entities.Reservation
import com.example.project.databases.ReservationDao
import com.example.project.databases.entities.User
import com.example.project.databases.UserDao
import com.example.project.models.ParkingModel
import com.example.project.models.ResevationModel
import com.example.project.models.UserModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun DisplayReservations(navController: NavHostController, reservationModel: ResevationModel, parkingModel: ParkingModel, userModel: UserModel) {

    var user = userModel.authUser.value

    val loading = remember {
        mutableStateOf(true)
    }

    var parking = Parking(
            nom="t",
            commune = "t",
            adresse = "r",
            prix = "r",
                dispo = "r",
                distance = "r",
                places = 100,
                img = R.drawable.p1
        )


    LaunchedEffect(Unit) {
        if (user != null) reservationModel.getAllUserReservations(user.id)
        loading.value = false
    }



    if (loading.value) {
        loader(loading = loading.value)
    } else {

    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(
                    start = 10.dp,
                    top = 30.dp,
                    end = 10.dp,
                    bottom = 30.dp
                )
        ) {
            Image(painter = painterResource(id = R.drawable.backarrow) ,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(
                        start = 40.dp,
                        end = 40.dp
                    )
                    .size(30.dp)
            )
            Text(text = "My Reservations",
                modifier = Modifier
                    .padding(start = 30.dp),
                fontWeight = FontWeight.ExtraBold
            )
            Button(
                onClick = {
                    if (user!=null) userModel.logout(user)
                    navController.navigate(DestinationPath.Home.route)
                }
            ) {
                Text(text = "Logout")
            }


        }
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(
                    20.dp
                )
                .fillMaxWidth()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally // Centers content within each column
            ) {
                Text(text = "Ongoing")
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally // Centers content within each column
            ) {
                Text(text = "Completed")
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally // Centers content within each column
            ) {
                Text(text = "Cancelled")
            }
        }

        LazyColumn {
            items(reservationModel.allUserReservations.value) {

                Column (
                    modifier = Modifier
                        .clip(RoundedCornerShape(15.dp))
                        .padding(10.dp)
                        .background(Color(0xFFE0E0E0))

                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    ){

                        Text(text = "ParkingID = ${it.parkingId}")
                    }
                    Spacer(modifier = Modifier.height(15.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    ){

                        Text(text = "Date = ${it.date_entree}")
                    }
                    Spacer(modifier = Modifier.height(15.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    ){
                        Text(text = "id = ${it.id}")
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 15.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Column {
                            Button(onClick = { /*TODO*/ }) {
                                Text(text = "Get Qr-code")
                            }
                        }


                    }




                }
            }

        }

    }




}}