package com.example.trailevy.repository

import android.util.Log
import com.example.trailevy.data.UserLoginRequest
import com.example.trailevy.data.UserLoginResponse
import com.example.trailevy.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginRepository {

    private val apiService = RetrofitInstance.apiService

    fun loginUser(email: String, password: String, onResult: (Boolean) -> Unit) {
        val userLoginRequest = UserLoginRequest(email, password)
        val call = apiService.login(userLoginRequest)

        call.enqueue(object : Callback<UserLoginResponse> {
            override fun onResponse(
                call: Call<UserLoginResponse>,
                response: Response<UserLoginResponse>
            ) {
                if (response.isSuccessful) {
                    // Perform any data manipulation if needed and notify the result
                    onResult(true)
                } else {
                    onResult(false)
                    Log.e("UserRepository", "Login request failed with code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<UserLoginResponse>, t: Throwable) {
                onResult(false)
                Log.e("UserRepository", t.message.toString())
            }
        })
    }
}