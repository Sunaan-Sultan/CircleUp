package com.project.example.ui.favourites

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.models.posts.FavoritePost
import com.project.models.posts.FavoritesService
import com.project.service.favourites.FavoritesServiceImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class FavoritesUiState(
    val favorites: List<FavoritePost> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class FavoritesViewModel(
    private val context: Context,
    private val favoritesService: FavoritesService = FavoritesServiceImpl(context)
) : ViewModel() {

    private val _uiState = MutableStateFlow(FavoritesUiState())
    val uiState: StateFlow<FavoritesUiState> = _uiState.asStateFlow()

    fun loadFavorites() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                error = null
            )

            try {
                val favorites = favoritesService.getFavorites()
                _uiState.value = _uiState.value.copy(
                    favorites = favorites,
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Failed to load favorites"
                )
            }
        }
    }

    fun removeFromFavorites(postId: Int) {
        viewModelScope.launch {
            try {
                favoritesService.removeFromFavorites(postId)
                val updatedFavorites = _uiState.value.favorites.filter { it.id != postId }
                _uiState.value = _uiState.value.copy(
                    favorites = updatedFavorites
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message ?: "Failed to remove from favorites"
                )
            }
        }
    }

    fun retry() {
        loadFavorites()
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}