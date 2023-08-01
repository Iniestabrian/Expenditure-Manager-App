package com.example.trailevy.onboarding.screens

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.trailevy.R
import com.example.trailevy.databinding.FragmentFirstScreenBinding
import com.example.trailevy.databinding.FragmentSecondScreenBinding
import com.example.trailevy.databinding.FragmentThirdScreenBinding

class ThirdScreen : Fragment() {
    private lateinit var binding: FragmentThirdScreenBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentThirdScreenBinding.inflate(inflater,container,false)



        binding.finish.setOnClickListener {
            findNavController().navigate(R.id.action_viewPagerFragment_to_signUpFragment)
            onBoardingFinished()
        }


        return binding.root

    }



    private fun onBoardingFinished(){

        val sharedPref =requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("Finished",true)
        editor.apply()
    }




}