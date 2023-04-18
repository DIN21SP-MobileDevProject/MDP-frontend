package com.example.mdp_frontend.viewmodel

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mdp_frontend.domain.model.Listing
import com.example.mdp_frontend.domain.model.Response
import com.example.mdp_frontend.domain.repository.AddListingResponse
import com.example.mdp_frontend.domain.repository.CategoriesResponse
import com.example.mdp_frontend.domain.use_case.category.CategoriesUseCases
import com.example.mdp_frontend.domain.use_case.listing.ListingUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateListingFormViewModel @Inject constructor(
    private val listingUseCases: ListingUseCases,
    private val categoriesUseCases: CategoriesUseCases,
): ViewModel() {
    private val _uiState = MutableStateFlow(Listing())
    val uiState: StateFlow<Listing> = _uiState.asStateFlow()
    var addListingResponse by mutableStateOf<AddListingResponse>(Response.Success(false))
        private set

    var categoriesResponse by mutableStateOf<CategoriesResponse>(Response.Loading)
        private set

    init {
        getCategories()
    }

    private fun getCategories() = viewModelScope.launch {
        categoriesUseCases.getCategories().collect {response ->
            categoriesResponse = response
        }
    }

    fun updateTitle(title: String) {
        _uiState.update { currentState ->
            currentState.copy(title = title)
        }
    }

    fun updateDescription(description: String) {
        _uiState.update { currentState ->
            currentState.copy(description = description)
        }
    }

    fun updatePrice(price: String) {
        _uiState.update { currentState ->
            currentState.copy(priceStr = price, price = price.toDoubleOrNull() ?: 0.0)
        }
    }

    fun updateCoordinates(lat: Float, long: Float) {
        _uiState.update { currentState ->
            currentState.copy(
                latitude = lat,
                longitude = long,
            )
        }
    }

    fun updateImageUri(newUri: Uri?) {
        _uiState.update { currentState ->
            currentState.copy(
                pictureUri = newUri.toString(),
            )
        }
    }

    fun updateCategory(newCategory: String?) {
        _uiState.update { currentState ->
            currentState.copy(
                category = newCategory,
            )
        }
    }

    fun addListing() = viewModelScope.launch {
        addListingResponse = Response.Loading
        addListingResponse = listingUseCases.addListing(uiState.value)
    }

    fun reset() {
        _uiState.update { currentState ->
            Listing()
        }
    }
}