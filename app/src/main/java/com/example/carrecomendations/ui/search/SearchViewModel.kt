package com.example.carrecomendations.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.carrecomendations.ui.home.Car
import com.example.carrecomendations.ui.home.CarRepository

class SearchViewModel : ViewModel() {

    private val _searchResults = MutableLiveData<List<Car>>()
    val searchResults: LiveData<List<Car>> = _searchResults

    val carTypes = listOf("Semua", "Hatchback", "Convertible", "Pickup", "Supercar")

    fun performSearch(query: String, type: String, minPrice: Int = 0, maxPrice: Int = 0): List<Car> {
        val allCars = CarRepository.getAllCars()

        println("Total cars in repository: ${allCars.size}")

        val filteredList = allCars.filter { car ->
            val nameMatch = if (query.isEmpty()) {
                true
            } else {
                car.name.contains(query, ignoreCase = true)
            }

            val typeMatch = if (type == "Semua") {
                true
            } else {
                car.category.equals(type, ignoreCase = true)
            }

            val priceMatch = if (minPrice <= 0 && maxPrice <= 0) {
                true
            } else {
                val carPriceString = car.price
                    .replace("Rp ", "")
                    .replace(".", "")
                    .replace(" ", "")
                    .replace(",", "")

                val carPrice = try {
                    carPriceString.toLong() / 1000000 // Convert to millions
                } catch (e: NumberFormatException) {
                    println("Error parsing price for ${car.name}: ${car.price}")
                    0L
                }

                val minMatch = if (minPrice > 0) carPrice >= minPrice else true
                val maxMatch = if (maxPrice > 0) carPrice <= maxPrice else true

                println("Car: ${car.name}, Price: ${car.price}, Parsed: $carPrice, MinMatch: $minMatch, MaxMatch: $maxMatch")

                minMatch && maxMatch
            }

            val result = nameMatch && typeMatch && priceMatch
            println("Car: ${car.name}, NameMatch: $nameMatch, TypeMatch: $typeMatch, PriceMatch: $priceMatch, Final: $result")

            result
        }

        println("Filtered results: ${filteredList.size}")
        _searchResults.value = filteredList
        return filteredList
    }

    fun loadAllCars() {
        _searchResults.value = CarRepository.getAllCars()
    }
}
