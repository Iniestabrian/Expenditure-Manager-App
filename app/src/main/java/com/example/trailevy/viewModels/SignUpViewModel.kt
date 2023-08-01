package com.example.trailevy.viewModels

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.example.trailevy.R
import com.example.trailevy.data.CategoriesRequest
import com.example.trailevy.data.UserSignupRequest
import com.example.trailevy.data.UserSignupResponse
import com.example.trailevy.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpViewModel : ViewModel() {

    private  var signUpLiveData  = MutableLiveData<List<CategoriesRequest>>()
    private val _signupResult = MutableLiveData<Boolean>()
    val signupResult: LiveData<Boolean>
        get() = _signupResult


    fun signUp(firstName: String,secondName:String,email:String,password:String){


        val userSignupRequest = UserSignupRequest(firstName,secondName,email,password)
        val call = RetrofitInstance.apiService.signup(userSignupRequest)


        call.enqueue(object: Callback<UserSignupResponse> {
            override fun onResponse(
                call: Call<UserSignupResponse>,
                response: Response<UserSignupResponse>
            ) {
                if (response.isSuccessful){
                    val signupResponse = response.body()!!

                    _signupResult.value = true
                   /* Toast.makeText(context, "Signup successful!!", Toast.LENGTH_LONG).show()
                    findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)*/

                } else {
                    // Handle error response here
                    _signupResult.value = false

                }
            }

            override fun onFailure(call: Call<UserSignupResponse>, t: Throwable) {
                /*Toast.makeText(context,t.message, Toast.LENGTH_LONG).show()*/

                _signupResult.value = false

            }

        })

    }
}