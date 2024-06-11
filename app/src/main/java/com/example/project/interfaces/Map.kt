package com.example.auth.pages

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.IntentSender
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.health.connect.datatypes.ExerciseRoute
import android.location.Location
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.example.project.R
import com.example.project.interfaces.DestinationPath
import com.example.project.models.ParkingModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.Priority
import com.google.android.gms.location.SettingsClient
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LocationScreen(
    parkingModel: ParkingModel,
    navController: NavHostController
) {
    val context = LocalContext.current
    val locationPermissionState = rememberPermissionState(android.Manifest.permission.ACCESS_FINE_LOCATION)
    var currentLatLng by remember { mutableStateOf(LatLng(0.0, 0.0)) }
    var locationEnabled by remember { mutableStateOf(false) }

    val resolutionForResult = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult(),
        onResult = { result ->
            if (result.resultCode == android.app.Activity.RESULT_OK) {
                locationEnabled = true
            }
        }
    )

    LaunchedEffect(locationPermissionState.status, locationEnabled) {
        if (locationPermissionState.status.isGranted) {
            checkLocationSettings(context, onSuccess = {
                locationEnabled = true
                getLocation(context) { latLng ->
                    currentLatLng = latLng
                }
            }, onFailure = { exception ->
                if (exception is ResolvableApiException) {
                    try {
                        resolutionForResult.launch(
                            IntentSenderRequest.Builder(exception.resolution).build()
                        )
                    } catch (sendEx: IntentSender.SendIntentException) {
                        Log.e("Location", "Error getting location settings resolution: ${sendEx.message}")
                    }
                }
            })
        } else {
            locationPermissionState.launchPermissionRequest()
        }
    }

    when {
        locationPermissionState.status.isGranted && locationEnabled && currentLatLng != LatLng(0.0, 0.0) -> {
            LocationMap(currentLatLng, parkingModel, navController)
        }
        locationPermissionState.status.shouldShowRationale -> {
            PermissionDeniedUI(onRequestPermission = { locationPermissionState.launchPermissionRequest() })
        }
        else -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Waiting for location permission and services to be enabled.",
                    color =Color(0xFF703ED1),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@SuppressLint("MissingPermission")
private fun getLocation(
    context: Context,
    onLocationReceived: (LatLng) -> Unit
) {
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
        .addOnSuccessListener { location: Location? ->
            if (location != null) {
                val currentLatLng = LatLng(location.latitude, location.longitude)
                Log.d("Location", "Latitude: ${location.latitude}, Longitude: ${location.longitude}")
                onLocationReceived(currentLatLng)
            } else {
                Log.d("Location", "Location is null")
            }
        }
        .addOnFailureListener { exception ->
            Log.d("Location", "Location request failed: ${exception.message}")
        }
}

private fun checkLocationSettings(
    context: Context,
    onSuccess: () -> Unit,
    onFailure: (Exception) -> Unit
) {
    val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000).build()

    val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)

    val client: SettingsClient = LocationServices.getSettingsClient(context)
    val task = client.checkLocationSettings(builder.build())

    task.addOnSuccessListener { onSuccess() }
    task.addOnFailureListener { exception -> onFailure(exception) }
}

@Composable
fun LocationMap(
    currentLatLng: LatLng,
    parkingModel: ParkingModel,
    navController: NavHostController
) {
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(currentLatLng, 10f)
    }

    var uiSettings by remember {
        mutableStateOf(MapUiSettings(zoomControlsEnabled = true))
    }
    var properties by remember {
        mutableStateOf(MapProperties(mapType = MapType.TERRAIN))
    }

    LaunchedEffect(Unit) {
        parkingModel.getAllParkings()
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        properties = properties,
        uiSettings = uiSettings
    ) {
        Marker(
            state = MarkerState(position = currentLatLng),
            //   icon = bitmapDescriptorFromVector(LocalContext.current, R.drawable.car, 80, 80),
            title = "Current Location",
            snippet = "This is your current location"
        )

        parkingModel.allParkings.value.forEach { parking ->
            Marker(
                state = MarkerState(position = LatLng(parking.latitude, parking.longitude)),
                title = parking.nom,
                snippet = parking.adresse,
                icon = bitmapDescriptorFromVector(LocalContext.current, R.drawable.img_5, 80, 80),
                onClick = {
                    navController.navigate(DestinationPath.ParkingDetails.getRoute(parking.id))
                    return@Marker true
                }
            )
        }
    }
}

@Composable
fun PermissionDeniedUI(
    onRequestPermission: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Location permission is needed to show your current location.")
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onRequestPermission) {
                Text("Grant Permission")
            }
        }
    }
}
fun bitmapDescriptorFromVector(context: Context, vectorResId: Int, desiredWidth: Int, desiredHeight: Int): BitmapDescriptor {
    val vectorDrawable = ContextCompat.getDrawable(context, vectorResId)
    vectorDrawable?.setBounds(0, 0, vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight)
    val bitmap = Bitmap.createBitmap(
        vectorDrawable?.intrinsicWidth ?: 0,
        vectorDrawable?.intrinsicHeight ?: 0,
        Bitmap.Config.ARGB_8888
    )
    val canvas = android.graphics.Canvas(bitmap)
    vectorDrawable?.draw(canvas)

    val resizedBitmap = Bitmap.createScaledBitmap(bitmap, desiredWidth, desiredHeight, false)

    return BitmapDescriptorFactory.fromBitmap(resizedBitmap)
}