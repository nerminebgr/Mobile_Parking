package com.example.project.databases.DataClasses

import com.google.gson.annotations.SerializedName

data class NotificationResponse(
    @SerializedName("message")
    val message: String
)
