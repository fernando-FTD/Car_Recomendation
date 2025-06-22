package com.example.carrecomendations.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class HomeViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _greetingText = MutableLiveData<String>()
    val greetingText: LiveData<String> = _greetingText

    private val _cars = MutableLiveData<List<Car>>()
    val cars: LiveData<List<Car>> = _cars

    init {
        loadGreeting()
        loadCars()
    }

    private fun loadGreeting() {
        val currentUser = auth.currentUser
        val userName = currentUser?.displayName.takeIf { !it.isNullOrEmpty() } ?: "Pengguna"
        _greetingText.value = "HI! $userName"
    }

    private fun loadCars() {
        // Mengambil data mobil dari repository
        _cars.value = CarRepository.getAllCars()
    }

    // Fungsi untuk refresh data
    fun refreshData() {
        loadGreeting()
        loadCars()
    }
}
