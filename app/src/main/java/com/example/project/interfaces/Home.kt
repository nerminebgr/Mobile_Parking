package com.example.project.interfaces

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.project.databases.ParkingDao
import com.example.project.databases.entities.Parking
import com.example.project.R
import com.example.project.URL
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
import java.net.URL
import java.util.Date

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun DisplayHome(navController: NavHostController, reservationModel: ResevationModel, parkingModel: ParkingModel, userModel: UserModel) {

    var user = User(firstName = "test", lastName = "test")

    LaunchedEffect(Unit){
        parkingModel.getAllParkings()
    }



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
            Image(
                painter = painterResource(id = R.drawable.backarrow),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(
                        start = 40.dp,
                        end = 40.dp
                    )
                    .size(30.dp)
            )
            Text(
                text = "Parkings",
                modifier = Modifier
                    .padding(start = 30.dp),
                fontWeight = FontWeight.ExtraBold
            )

        }

        /*Row {
            Text(text = "$parkings")
        }*/
        LazyColumn {
            items(parkingModel.allParkings.value) {
                Column(
                    modifier = Modifier
                        .clip(RoundedCornerShape(15.dp))
                        .padding(10.dp)
                        .background(Color(0xFFE0E0E0))

                ) {
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .padding(10.dp)

                    ) {
                        AsyncImage(model = URL +it.photo,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f)
                                .padding(8.dp)
                                .clip(RoundedCornerShape(20.dp))
                        )

                        Column(
                            modifier = Modifier
                                .weight(2f)
                                .padding(8.dp)
                        ) {
                            Text(
                                text = it.nom, fontWeight = FontWeight.Bold,
                                fontSize = 12.sp, modifier = Modifier.padding(start = 10.dp)
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Row(
                                modifier = Modifier
                                    .padding(10.dp)
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.adresse),
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .padding(
                                            end = 5.dp
                                        )
                                        .size(15.dp)
                                )
                                Text(
                                    text = it.adresse,
                                    fontSize = 11.sp
                                )
                            }

                            Spacer(modifier = Modifier.height(15.dp))
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .padding(end = 20.dp)
                                ) {
                                    Row {
                                        Image(
                                            painter = painterResource(id = R.drawable.car),
                                            contentDescription = null,
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier
                                                .padding(
                                                    end = 5.dp
                                                )
                                                .size(15.dp)
                                        )
                                        val nb = it.places
                                        Text(
                                            text = "$nb Car Spots",
                                            fontSize = 10.sp
                                        )
                                    }

                                }
                                Column(

                                ) {
                                    Text(
                                        text = it.prix,
                                        fontSize = 10.sp
                                    )
                                }
                            }


                        }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 15.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {

                        Column {
                            Button(onClick = {

                                /*CoroutineScope(Dispatchers.IO).launch {
                                    val res = Reservation(

                                        userId = user.id,
                                        parkingId = it.id,
                                        date = Date()
                                    )
                                    reservationModel.addReservation(res)
                                }*/
                            })
                            {
                                Text(text = "Book-Now")
                            }
                        }
                    }


                }
            }

        }

    }


}




@Composable
fun loader(loading : Boolean){
    if(loading){
        CircularProgressIndicator()

    }
}