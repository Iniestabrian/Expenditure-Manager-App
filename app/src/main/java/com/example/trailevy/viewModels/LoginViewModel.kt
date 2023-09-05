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
import com.example.trailevy.repository.LoginRepository
import com.example.trailevy.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log

class LoginViewModel() : ViewModel() {

    private val loginRepository = LoginRepository()

    private val _loginResult = MutableLiveData<Boolean>()
    val loginResult: LiveData<Boolean>
        get() = _loginResult





    fun login(email: String, password: String) {
        loginRepository.loginUser(email, password) { isSuccess ->
            _loginResult.value = isSuccess
        }
    }
}

