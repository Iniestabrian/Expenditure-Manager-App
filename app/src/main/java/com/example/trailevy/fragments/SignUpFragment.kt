package com.example.trailevy.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.method.PasswordTransformationMethod
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.trailevy.R
import com.example.trailevy.data.UserSignupRequest
import com.example.trailevy.data.UserSignupResponse
import com.example.trailevy.databinding.FragmentSignUpBinding
import com.example.trailevy.retrofit.ApiService
import com.example.trailevy.retrofit.RetrofitInstance.apiService
import com.example.trailevy.viewModels.SignUpViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpFragment : Fragment() {

        private lateinit var binding:FragmentSignUpBinding
        private lateinit var signUpMvvm :SignUpViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentSignUpBinding.inflate(inflater,container,false)


        signUpMvvm = ViewModelProvider(this)[SignUpViewModel::class.java]

        return binding.root
    }


    @Override
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        registerOnclick()
        loginOnclick()
        toggleShowPassword()
    }




    private fun registerOnclick() {
        binding.btnRegister.setOnClickListener {
            val firstName = binding.edFirstName.text.toString()
            val secondName = binding.edtSecondName.text.toString()
            val email = binding.edEmail.text.toString()
            val password = binding.edPassword.text.toString()
            val confirmPassword = binding.edConfirmPassword.text.toString()

            if (TextUtils.isEmpty(firstName) || TextUtils.isEmpty(secondName) || TextUtils.isEmpty(email) ||
                TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)
            ) {
                Toast.makeText(context, "Please fill all the fields", Toast.LENGTH_LONG).show()
            } else if (password != confirmPassword) {
                Toast.makeText(context, "Passwords must match", Toast.LENGTH_LONG).show()
            } else {
                // All checks pass, proceed with the signup process
                userSignup()

            }
        }
    }




    private  fun loginOnclick(){

        binding.tvLogin.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
        }
    }









    fun toggleShowPassword(){


      binding.showPasswordButton.setOnClickListener {
            val showPassword =binding.edPassword.transformationMethod == null
            if (showPassword) {
                // Show password as asterisks
              binding.edPassword.transformationMethod = PasswordTransformationMethod.getInstance()
            } else {
                // Show password in plain text
              binding.edPassword.transformationMethod = null
            }
        }



        binding.showConfirmPasswordButton.setOnClickListener {
            val showPassword =binding.edConfirmPassword.transformationMethod == null
            if (showPassword) {
                // Show password as asterisks
              binding.edConfirmPassword.transformationMethod = PasswordTransformationMethod.getInstance()
            } else {
                // Show password in plain text
              binding.edConfirmPassword.transformationMethod = null
            }
        }


    }



    //signUp Logic

    private fun userSignup() {
        val firstName = binding.edFirstName.text.toString()
        val secondName = binding.edtSecondName.text.toString()
        val email =binding.edEmail.text.toString()
        val password = binding.edPassword.text.toString()

        signUpMvvm.signupResult.observe(viewLifecycleOwner) { isSignupSuccessful ->
            if (isSignupSuccessful) {
                Toast.makeText(context, "Signup successful!", Toast.LENGTH_LONG).show()
                findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
            } else {
                Toast.makeText(context, "Signup failed. Please try again.", Toast.LENGTH_LONG).show()
            }
        }

        signUpMvvm.signUp(firstName, secondName, email, password)
    }








}