package com.example.carrecomendations

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.carrecomendations.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_search,
                R.id.navigation_favorites,
                R.id.navigation_profile
            )
        )

        supportActionBar?.hide()

        setupActionBarWithNavController(navController, appBarConfiguration)

        // Setup bottom navigation dengan custom behavior
        navView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    // Selalu pop back ke home fragment dan clear back stack
                    navController.popBackStack(R.id.navigation_home, false)
                    true
                }
                R.id.navigation_search -> {
                    // Pop back ke search fragment jika tidak di sana
                    if (navController.currentDestination?.id != R.id.navigation_search) {
                        navController.popBackStack(R.id.navigation_search, false)
                        if (navController.currentDestination?.id != R.id.navigation_search) {
                            navController.navigate(R.id.navigation_search)
                        }
                    }
                    true
                }
                R.id.navigation_favorites -> {
                    // Pop back ke favorites fragment jika tidak di sana
                    if (navController.currentDestination?.id != R.id.navigation_favorites) {
                        navController.popBackStack(R.id.navigation_favorites, false)
                        if (navController.currentDestination?.id != R.id.navigation_favorites) {
                            navController.navigate(R.id.navigation_favorites)
                        }
                    }
                    true
                }
                R.id.navigation_profile -> {
                    // Pop back ke profile fragment jika tidak di sana
                    if (navController.currentDestination?.id != R.id.navigation_profile) {
                        navController.popBackStack(R.id.navigation_profile, false)
                        if (navController.currentDestination?.id != R.id.navigation_profile) {
                            navController.navigate(R.id.navigation_profile)
                        }
                    }
                    true
                }
                else -> false
            }
        }

        // Update selected item based on current destination
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.navigation_home -> navView.selectedItemId = R.id.navigation_home
                R.id.navigation_search -> navView.selectedItemId = R.id.navigation_search
                R.id.navigation_favorites -> navView.selectedItemId = R.id.navigation_favorites
                R.id.navigation_profile -> navView.selectedItemId = R.id.navigation_profile
                // Untuk fragment lain (seperti detail), tidak mengubah selected item
            }
        }
    }
}
