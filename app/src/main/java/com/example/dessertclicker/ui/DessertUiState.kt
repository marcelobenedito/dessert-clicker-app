package com.example.dessertclicker.ui

import androidx.annotation.DrawableRes
import com.example.dessertclicker.R
import com.example.dessertclicker.model.Dessert

data class DessertUiState(
    val revenue: Int = 0,
    val dessertsSold: Int = 0,
    val currentDessertIndex: Int = 0,
    val currentDessertPrice: Int = 0,
    @DrawableRes val currentDessertImageId: Int = R.drawable.cupcake,
    val desserts: List<Dessert> = emptyList()
)
