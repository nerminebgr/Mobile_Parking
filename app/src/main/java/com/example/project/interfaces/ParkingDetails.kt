package com.example.project.interfaces

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.project.R
import com.example.project.URL
import com.example.project.databases.entities.User
import com.example.project.models.ParkingModel
import com.example.project.models.UserModel


@Composable
fun ParkingDetails(id:Int?, navController: NavHostController,parkingModel: ParkingModel, userModel: UserModel)
{
    var user = userModel.authUser.value


    LaunchedEffect(Unit){
        if(id!=null){
            parkingModel.getParkingDetail(id)
        }

    }
    var parking = parkingModel.currentParking.value
    if(parking!=null){

        Column (
        ){
            Column (
                modifier = Modifier
                    .fillMaxHeight(.9f)
                    .verticalScroll(rememberScrollState())
            ){
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                )
                {

                    AsyncImage(model = URL +parking.image,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .aspectRatio(1f)
                            .padding(8.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .fillMaxWidth()
                            .height(350.dp)
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp, horizontal = 10.dp)
                            .padding(bottom = 10.dp),

                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {

                        IconButton(
                            modifier = Modifier
                                .scale(.8f)
                                .border(width = 1.dp, color = Color.LightGray, shape = CircleShape)
                                .background(Color.White, shape = CircleShape),
                            onClick = { navController.popBackStack() }
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = null,
                            )
                        }

                        IconButton(
                            modifier = Modifier
                                .scale(.8f)
                                .border(width = 1.dp, color = Color.LightGray, shape = CircleShape)
                                .background(Color.White, shape = CircleShape),
                            onClick = {  }
                        ) {
                            Icon(
                                imageVector = Icons.Default.FavoriteBorder,
                                contentDescription = null,
                            )
                        }
                    }
                }


                Column(
                    modifier = Modifier
                        .weight(2f)
                        .fillMaxHeight()
                        .padding(20.dp),
                ) {

                    Row (
                        modifier= Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ){
                        Box(
                            modifier = Modifier
                                .background(Color(0xFFF6F6F6))
                                .padding(horizontal = 5.dp)
                                .clip(RoundedCornerShape(10.dp)),
                        ) {
                            Text(
                                text = "car parking",
                                fontSize = 20.sp,
                                color = Color(0xFF7136ff),
                            )
                        }


                    }


                    Spacer(modifier = Modifier.height(10.dp))


                    Text(
                        text = parking.nom,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                    )


                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = parking.adresse,
                        fontSize = 15.sp,
                        color = Color.Gray,
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Row (
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ){


                        Row (
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            Icon(
                                modifier = Modifier
                                    .size(40.dp)
                                    .padding(end = 2.dp),
                                painter = painterResource(id = R.drawable.clock),
                                contentDescription = null, // Set to null if the icon is purely decorative
                                tint = Color(0xFF7136ff),
                            )

                            Text(
                                text = "à 5 minutes",
                                fontSize = 15.sp,
                                color = Color.Black,
                            )
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            Icon(
                                modifier = Modifier
                                    .size(40.dp)
                                    .padding(end = 2.dp),
                                painter = painterResource(id = R.drawable.car),
                                contentDescription = null, // Set to null if the icon is purely decorative
                                tint = Color(0xFF7136ff),
                            )

                            Text(
                                text = "${parking.places} blocks disponibles",
                                fontSize = 15.sp,
                                color = Color.Black,
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(15.dp))

                    Column {
                        Text(
                            text = "Description",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = parking.description,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Gray,
                        )
                    }



                }

            }




            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RectangleShape)
                    .border(
                        width = 1.dp,
                        color = Color.LightGray,
                        shape = RoundedCornerShape(20.dp, 20.dp, 0.dp, 0.dp)
                    )
                    .height(80.dp)
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceAround

            ) {
                Column {
                    Text(
                        text = "Prix:",
                        fontSize = 15.sp,
                        color = Color.Gray,
                    )

                    Row(verticalAlignment = Alignment.Bottom) {

                        Text(
                            text = "${parking.prix}DA",
                            fontSize = 20.sp,
                            color = Color(0xFF7136ff),
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(
                            text = "/hr",
                            fontSize = 15.sp,
                            color = Color.Gray,
                        )
                    }
                }

                Button(
                    modifier = Modifier
                        .fillMaxHeight()
                        .aspectRatio(3.5f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF7136ff)
                    ),
                    onClick = { /*TODO*/ }
                ) {
                    Text(text = "Book now", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                }


            }

        }

    }


}


