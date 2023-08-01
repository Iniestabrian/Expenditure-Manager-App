package com.example.trailevy.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.trailevy.R
import com.example.trailevy.databinding.FragmentViewPagerBinding
import com.example.trailevy.onboarding.screens.FirstScreen
import com.example.trailevy.onboarding.screens.SecondScreen
import com.example.trailevy.onboarding.screens.ThirdScreen

class ViewPagerFragment : Fragment() {

        private lateinit var binding: FragmentViewPagerBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentViewPagerBinding.inflate(inflater,container,false)

        val fragmentList = arrayListOf<Fragment>(

            FirstScreen(),
        SecondScreen(),
            ThirdScreen()

        )


        val adapter  = ViewPagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )


        binding.viewpager.adapter = adapter


        return binding.root



    }

    }
