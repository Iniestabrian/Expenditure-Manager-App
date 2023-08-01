package com.example.trailevy.viewModels

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.trailevy.data.UserLoginRequest
import com.example.trailevy.data.UserLoginResponse
import com.example.trailevy.data.UserSignupRequest
import com.example.trailevy.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log

class LoginViewModel() : ViewModel() {
    private val _loginResult = MutableLiveData<Boolean>()
    val loginResult: LiveData<Boolean>
        get() = _loginResult

    fun login(email:String,password:String){

        val userLoginRequest = UserLoginRequest(email,password)
        val call = RetrofitInstance.apiService.login(userLoginRequest)

        call.enqueue(object :Callback<UserLoginResponse>{
            override fun onResponse(
                call: Call<UserLoginResponse>,
                response: Response<UserLoginResponse>
            ) {
               if (response.isSuccessful){
                   val loginResponse = response.body()!!

                   _loginResult.value =  true
               }else{
                   _loginResult.value= false
                   Log.e("LoginViewModel", "Login request failed with code: ${response.code()}")

               }


            }

            override fun onFailure(call: Call<UserLoginResponse>, t: Throwable) {
                _loginResult.value =false


                Log.e("LoginViewModel", t.message.toString());

            }

        })
    }
}
