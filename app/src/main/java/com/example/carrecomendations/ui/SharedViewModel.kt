package com.example.carrecomendations.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.carrecomendations.ui.home.Car
import com.example.carrecomendations.ui.home.CarRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration

class SharedViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var favoritesListener: ListenerRegistration? = null

    private val _favoriteCars = MutableLiveData<Set<Car>>(emptySet())
    val favoriteCars: LiveData<Set<Car>> = _favoriteCars

    init {
        listenToAuthState()
    }

    private fun listenToAuthState() {
        auth.addAuthStateListener { firebaseAuth ->
            if (firebaseAuth.currentUser != null) {
                attachFavoritesListener(firebaseAuth.currentUser!!.uid)
            } else {
                detachFavoritesListener()
                _favoriteCars.value = emptySet()
            }
        }
    }

    private fun attachFavoritesListener(userId: String) {
        // Hentikan listener lama jika ada
        detachFavoritesListener()

        val userFavoritesRef = db.collection("users").document(userId).collection("favorites")

        favoritesListener = userFavoritesRef.addSnapshotListener { snapshot, error ->
            if (error != null) {
                Log.e("SharedViewModel", "Error listening for favorites", error)
                return@addSnapshotListener
            }

            if (snapshot != null) {
                // Ambil semua ID mobil yang difavoritkan
                val favoriteCarIds = snapshot.documents.mapNotNull { it.id.toIntOrNull() }

                // Ubah ID menjadi objek Car lengkap dari Repository
                val favoriteCarsSet = favoriteCarIds.mapNotNull { CarRepository.findCarById(it) }.toSet()
                _favoriteCars.value = favoriteCarsSet
            }
        }
    }

    private fun detachFavoritesListener() {
        favoritesListener?.remove()
        favoritesListener = null
    }

    fun toggleFavorite(car: Car) {
        val userId = auth.currentUser?.uid ?: return // Jangan lakukan apa-apa jika user tidak login

        val favoriteRef = db.collection("users").document(userId).collection("favorites").document(car.id.toString())

        if (_favoriteCars.value?.any { it.id == car.id } == true) {
            // Jika sudah favorit, hapus dari Firestore
            favoriteRef.delete()
        } else {
            // Jika belum, tambahkan ke Firestore (cukup buat dokumen kosong)
            favoriteRef.set(mapOf("favoritedAt" to System.currentTimeMillis()))
        }
    }

    override fun onCleared() {
        super.onCleared()
        // Pastikan listener dilepas saat ViewModel dihancurkan
        detachFavoritesListener()
    }
}
