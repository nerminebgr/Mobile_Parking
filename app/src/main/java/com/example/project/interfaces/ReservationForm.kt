package com.example.project.interfaces

import android.app.TimePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text

import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.project.R
import com.example.project.databases.entities.Reservation
import com.example.project.models.ParkingModel
import com.example.project.models.ResevationModel
import com.example.project.models.UserModel
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.util.Date
import java.time.format.DateTimeFormatter
import java.util.Calendar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReservationForm(
    id: Int?,
    navController: NavHostController,
    reservationModel: ResevationModel,
    parkingModel: ParkingModel,
    userModel: UserModel
) {
    var user = userModel.authUser.value

    LaunchedEffect(Unit){
        if(id!=null){
            parkingModel.getParkingDetail(id)
        }

    }
    var parking = parkingModel.currentParking.value


    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    var selectedDateText by remember { mutableStateOf("") }

    // Fetching current year, month and day
    val year = calendar[Calendar.YEAR]
    val month = calendar[Calendar.MONTH]
    val dayOfMonth = calendar[Calendar.DAY_OF_MONTH]

    var selectedStartTimeText by remember { mutableStateOf("") }
    var selectedEndTimeText by remember { mutableStateOf("") }

    // Fetching current hour, and minute
    val hour = calendar[Calendar.HOUR_OF_DAY]
    val minute = calendar[Calendar.MINUTE]

    val datePicker = android.app.DatePickerDialog(
        context,
        { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int ->
            selectedDateText = "${selectedYear}-${String.format("%02d", selectedMonth + 1)}-${String.format("%02d", selectedDayOfMonth)}"
        }, year, month, dayOfMonth
    )

    datePicker.datePicker.minDate = calendar.timeInMillis

    val timePicker1 = TimePickerDialog(
        context,
        { _, selectedHour: Int, selectedMinute: Int ->
            selectedStartTimeText = String.format("%02d:%02d", selectedHour, selectedMinute)
        }, hour, minute, true
    )

    val timePicker2 = TimePickerDialog(
        context,
        { _, selectedHour: Int, selectedMinute: Int ->
            selectedEndTimeText =  String.format("%02d:%02d", selectedHour, selectedMinute)
        }, hour, minute, true
    )


    Column(
        modifier = Modifier
            .padding(20.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
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

            Text(
                text = "Fill your reservation infos",
                modifier = Modifier
                    .padding(start = 40.dp),
                fontWeight = FontWeight.ExtraBold,
                onTextLayout = {}
            )

        }


        DateInputField(selectedDateText) {
            datePicker.show()
        }

        Spacer(modifier = Modifier.height(16.dp))

        TimeInputField("Start Time", selectedStartTimeText) {
            timePicker1.show()
        }

        Spacer(modifier = Modifier.height(16.dp))

        TimeInputField("End Time", selectedEndTimeText) {
            timePicker2.show()
        }

        Spacer(modifier = Modifier.height(32.dp))

        // The place

        Image(
            painter = painterResource(id = R.drawable.parking_place),// Remplacez R.drawable.logo par votre propre logo
            contentDescription = "Logo",
            modifier = Modifier.size(250.dp),
            contentScale = ContentScale.Fit
        )

        Text(
            text = "Price = ${parking?.prix}",
            modifier = Modifier.padding(bottom = 40.dp),
            fontSize = 15.sp,
            color = Color.Black,
            onTextLayout = {}
        )

        // The price 
        Button(
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF703ED1)),
            modifier = Modifier
                .fillMaxWidth(1f)
                .height(50.dp),
            onClick = {
                if (user != null && id != null) {

                    // Parse the selected date text to a LocalDate

                    val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                    val selectedDate = LocalDate.parse(selectedDateText, dateFormatter)

                    // Convert the LocalDate to a Date object
                    val date = Date.from(selectedDate.atStartOfDay(ZoneId.systemDefault()).toInstant())



                    val res = Reservation(
                        conducteurId = user.id,
                        parkingId = id,
                        date_entree = date,
                        heure_entree = selectedStartTimeText,
                        heure_sortie = selectedEndTimeText
                    )
                    reservationModel.addReservation(res,navController)
                }
            }
        ) {
            Text(
                text = "Pay & validate",
                onTextLayout = {}
            )
        }
    }
}

@Composable
fun DateInputField(selectedDateText: String, onClick: () -> Unit) {
    OutlinedTextField(
        value = if (selectedDateText.isNotEmpty()) {
            selectedDateText
        } else {
            "Please pick a date"
        },
        onValueChange = {},
        label = { Text("Reservation Date") },
        modifier = Modifier
            .padding(top = 20.dp)
            .fillMaxWidth()
            .clickable { onClick() },

        enabled = false,
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = "Date Picker"
            )
        }
    )
}

@Composable
fun TimeInputField(label: String, selectedTimeText: String, onClick: () -> Unit) {
    OutlinedTextField(
        value = if (selectedTimeText.isNotEmpty()) {
            selectedTimeText
        } else {
            "Please select the time"
        },
        onValueChange = {},
        label = { Text(label) },
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        enabled = false
    )
}

