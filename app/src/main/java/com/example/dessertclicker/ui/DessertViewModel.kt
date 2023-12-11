package com.example.dessertclicker.ui

import androidx.lifecycle.ViewModel
import com.example.dessertclicker.data.Datasource
import com.example.dessertclicker.model.Dessert
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DessertViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(DessertUiState())
    val uiState: StateFlow<DessertUiState> = _uiState.asStateFlow()

    init {
        loadDesserts()
    }

    /**
     * Determine which dessert to show.
     */
    private fun determineDessertToShow(
        desserts: List<Dessert>,
        dessertsSold: Int
    ): Dessert {
        var dessertToShow = desserts.first()
        for (dessert in desserts) {
            if (dessertsSold >= dessert.startProductionAmount) {
                dessertToShow = dessert
            } else {
                // The list of desserts is sorted by startProductionAmount. As you sell more desserts,
                // you'll start producing more expensive desserts as determined by startProductionAmount
                // We know to break as soon as we see a dessert who's "startProductionAmount" is greater
                // than the amount sold.
                break
            }
        }

        return dessertToShow
    }

    fun updateRevenue() {
        val dessertsSold = _uiState.value.dessertsSold.inc()
        val dessertUiState = _uiState.value
        val dessertToShow = determineDessertToShow(dessertUiState.desserts, dessertUiState.dessertsSold)

        _uiState.update { currentState ->
            currentState.copy(
                revenue = currentState.revenue.plus(currentState.currentDessertPrice),
                dessertsSold = dessertsSold,
                currentDessertImageId = dessertToShow.imageId,
                currentDessertPrice = dessertToShow.price
            )
        }
    }

    private fun loadDesserts() {
        val desserts: List<Dessert> = Datasource.dessertList
        _uiState.update { currentState ->
            currentState.copy(
                desserts = desserts,
                currentDessertPrice = desserts[currentState.currentDessertIndex].price,
                currentDessertIndex = desserts[currentState.currentDessertIndex].imageId
            )
        }
    }
}