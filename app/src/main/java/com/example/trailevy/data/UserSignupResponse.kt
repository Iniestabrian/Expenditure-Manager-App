package com.example.trailevy.data

 data class UserSignupResponse(

    val userId: String,
    val firstName:String,
    val secondName:String,
    val email:String,
    val password:String,
    val otp:String?,
    val otpExpiresAt:String?,
    val  createdAt:String


 )

