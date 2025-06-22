package com.example.carrecomendations.ui.home

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Car(
    val id: Int,
    val name: String,
    val price: String,
    val description: String,
    val imageResId: Int,
    val category: String
) : Parcelable
