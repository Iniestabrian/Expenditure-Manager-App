package com.example.trailevy

import android.os.Bundle
import android.text.TextUtils
import android.text.method.PasswordTransformationMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.trailevy.databinding.FragmentLoginBinding
import com.example.trailevy.viewModels.LoginViewModel

class LoginFragment : Fragment() {

    private lateinit var  binding :FragmentLoginBinding
    private lateinit var  loginMvvm :LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Inflate the layout for this fragment

        binding = FragmentLoginBinding.inflate(inflater,container,false)

        loginMvvm = ViewModelProvider(this)[LoginViewModel::class.java]

        observeLoginResultLiveData()
        return binding.root
    }


    @Override
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        signupOnclick()
        toggleShowPassword()
        loginOnclick()



    }


    private  fun signupOnclick(){

        binding.tvSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }
    }


    private  fun loginOnclick(){

        binding.btnLogin.setOnClickListener {
            val email = binding.edEmailAddress.text.toString()
            val password = binding.edPassword.text.toString()

            if (TextUtils.isEmpty(email)|| TextUtils.isEmpty(password))
            {
                Toast.makeText(context, "Please fill all the fields", Toast.LENGTH_LONG).show()

            }
            else{
                userLogin()

            }
        }
    }



    private fun toggleShowPassword(){


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
    }


    private fun userLogin(){
        val email = binding.tvEmail.text.toString()
        val password = binding.tvPassword.text.toString()


        loginMvvm.login(email,password)
    }


    private fun observeLoginResultLiveData(){
        loginMvvm.loginResult.observe(viewLifecycleOwner) { isSignupSuccessful ->
            if (isSignupSuccessful) {
                Toast.makeText(context, "Login successful!", Toast.LENGTH_LONG).show()
                findNavController().navigate(R.id.action_loginFragment_to_dashboardActivity)
            } else {
                Toast.makeText(context, "Login failed. Please try again.", Toast.LENGTH_LONG).show()
            }
        }
    }


}