package com.example.project.databases

import com.example.project.URL
import com.example.project.databases.entities.Parking
import com.example.project.databases.entities.Reservation
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface Endpoint {

    @GET("parkings/all/")
    suspend fun getAllParkings(): Response<List<Parking>>

    @GET("reservations/{userId}/")
    suspend fun getAllUserReservations(@Path("userId") userId:Int): Response<List<Reservation>>

    @POST("addReservation")
    suspend fun addReservation(@Body reservation: Reservation): Response<String>

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
