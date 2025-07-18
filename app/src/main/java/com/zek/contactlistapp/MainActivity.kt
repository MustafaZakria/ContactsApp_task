package com.zek.contactlistapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import com.zek.contactlistapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
//    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        setupNavigation()
    }
//
//    private fun setupNavigation() {
//        // Get NavHostFragment
//        val navHostFragment = supportFragmentManager
//            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
//
//        // Initialize NavController
//        navController = navHostFragment.navController
//
//        // Setup ActionBar with NavController
////        setupActionBarWithNavController()
//
//        // Optional: Add bottom navigation if needed
//        // binding.bottomNav.setupWithNavController(navController)
//    }

}


