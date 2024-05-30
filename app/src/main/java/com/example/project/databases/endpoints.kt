package com.example.project.databases

import com.example.project.URL
import com.example.project.databases.DataClasses.Credentials
import com.example.project.databases.DataClasses.NotificationResponse
import com.example.project.databases.DataClasses.RegisterRequest
import com.example.project.databases.DataClasses.ReservationDetails
import com.example.project.databases.DataClasses.TokenRequest
import com.example.project.databases.entities.Parking
import com.example.project.databases.entities.Reservation
import com.example.project.databases.entities.User
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface Endpoint {

    @GET("parkings/")
    suspend fun getAllParkings(): Response<List<Parking>>

    @GET("parkings/{parkingId}/")
    suspend fun getParkingDetail(@Path("parkingId") parkingId:Int): Response<Parking>

    @GET("reservations/{userId}/")
    suspend fun getAllUserReservations(@Path("userId") userId:Int): Response<List<Reservation>>

    @GET("reservations/reservation/{id}/")
    suspend fun getReservationDetails(@Path("id") id:Int): Response<ReservationDetails>

    @POST("reservations/addReservation")
    suspend fun addReservation(@Body reservation: Reservation): Response<Reservation>

    @POST("auth/register/")
    suspend fun register(@Body user: RegisterRequest): Response<User>

    @POST("auth/login/")
    suspend fun login(@Body credentials: Credentials): Response<User>

    @POST("/notif/update-token/")
    suspend fun update(@Body user: TokenRequest): Response<User>

    @GET("/notif/send-notifications/")
    suspend fun getNotification(): Response<NotificationResponse>

    companion object {
        var endpoint: Endpoint? = null
        fun createEndpoint(): Endpoint {
            if(endpoint ==null) {

                endpoint = Retrofit.Builder().baseUrl(URL). addConverterFactory(GsonConverterFactory.create()).build(). create(
                    Endpoint::class.java)

            }
            return endpoint!!
        }
    }
}
