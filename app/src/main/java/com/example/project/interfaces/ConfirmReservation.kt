package com.example.project.interfaces

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.QRCodeWriter
import java.util.Date
import java.util.Hashtable
import com.example.project.databases.entities.Reservation
import com.example.project.models.ParkingModel
import com.example.project.models.ResevationModel
import com.example.project.models.UserModel

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmReservation( id: Int?,navController: NavHostController, reservationModel: ResevationModel){

    val context = LocalContext.current

    LaunchedEffect(Unit){
        if(id!=null){
            reservationModel.getReservationDetails(id)
        }

    }

    val details = reservationModel.currentReservation.value



    Column (
        modifier = Modifier
            .background(Color(0xFFF6F6F6))
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .padding(bottom = 10.dp),

            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(
                modifier = Modifier
                    .scale(.8f)
                    .border(width = 1.dp, color = Color.LightGray, shape = CircleShape)
                    .background(Color.White, shape = CircleShape),
                onClick = { navController.navigate(Destination.Reservations.route) }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null,
                )
            }

            Text(
                text = "E-Ticket",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
            )
        }


        Row (
            modifier = Modifier
                .padding(8.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(Color.White)
        ) {
            Image(
                painter = BitmapPainter(generateQRCode(id.toString(), 1024).asImageBitmap()),
                contentDescription = "QR Code"
            )
        }

        Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
            ){
                Row(

                ) {
                    Text(
                        text = "Parking: ",
                        color = Color.Gray,
                        fontSize = 20.sp,
                    )
                    Text(
                        modifier = Modifier.height(30.dp),
                        text = "${details?.parking?.nom}",
                        color = Color.Black,
                        fontSize = 20.sp,
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                }
                Row(

                ){

                    Text(
                        text = "Date: ",
                        color = Color.Gray,
                        fontSize = 20.sp,
                    )
                    Text(
                        text = "${details?.date_entree}",
                        color = Color.Black,
                        fontSize = 20.sp,
                    )

                    Spacer(modifier = Modifier.height(20.dp))
                }
                Row(

                ){

                    Text(
                        text = "Entrée: ",
                        color = Color.Gray,
                        fontSize = 20.sp,
                    )
                    Text(
                        text = "${details?.heure_entree}",
                        color = Color.Black,
                        fontSize = 20.sp,
                    )

                    Spacer(modifier = Modifier.height(20.dp))
                }
                Row(

                ){

                    Text(
                        text = "Sortie: ",
                        color = Color.Gray,
                        fontSize = 20.sp,
                    )
                    Text(
                        text = "${details?.heure_sortie}",
                        color = Color.Black,
                        fontSize = 20.sp,
                    )
                }
                Row(

                ){
                    Text(
                        text = "Conducteur: ",
                        color = Color.Gray,
                        fontSize = 20.sp,
                    )
                    Text(
                        text = "${details?.conducteur?.firstname} ${details?.conducteur?.lastname}",
                        color = Color.Black,
                        fontSize = 20.sp,
                    )

                    Spacer(modifier = Modifier.height(20.dp))}
                Row(

                ){


                    Text(
                        text = "Prix: ",
                        color = Color.Gray,
                        fontSize = 20.sp,
                    )
                    Text(
                        text = "${(details?.parking?.prix)}",
                        color = Color.Black,
                        fontSize = 20.sp,
                    )
                }


        }



    }
}



fun generateQRCode(content: String, size: Int): Bitmap {
    val hints = Hashtable<EncodeHintType, Any>()
    hints[EncodeHintType.CHARACTER_SET] = "UTF-8"
    val writer = QRCodeWriter()
    val bitMatrix: BitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, size, size, hints)
    val width = bitMatrix.width
    val height = bitMatrix.height
    val pixels = IntArray(width * height)

    for (y in 0 until height) {
        for (x in 0 until width) {
            pixels[y * width + x] = if (bitMatrix[x, y]) android.graphics.Color.BLACK else android.graphics.Color.WHITE
        }
    }

    val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
    bmp.setPixels(pixels, 0, width, 0, 0, width, height)
    return bmp
}