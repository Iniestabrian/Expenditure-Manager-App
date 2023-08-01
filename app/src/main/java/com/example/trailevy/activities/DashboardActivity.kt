package com.example.trailevy.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.trailevy.R
import com.example.trailevy.databinding.ActivityDashboardBinding

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)

        val view = binding.root
        setContentView(view)


        val bottomNavigation = binding.btmNav
        val navController = Navigation.findNavController(this, R.id.dashboarddFragmentHost)


        NavigationUI.setupWithNavController(bottomNavigation,navController)




    }


    }




