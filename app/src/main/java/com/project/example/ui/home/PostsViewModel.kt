package com.project.example.ui.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.models.posts.Post
import com.project.models.posts.PostService
import com.project.service.posts.PostServiceImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class PostsUiState(
    val allPosts: List<Post> = emptyList(),
    val displayedPosts: List<Post> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val hasMoreData: Boolean = true,
    val currentPage: Int = 1,
    val searchQuery: String = "",
    val uiSearchQuery: String = "",
    val isOffline: Boolean = false,
    val hasCachedData: Boolean = false
)

class PostsViewModel(
    private val context: Context? = null,
    private val postService: PostService = PostServiceImpl(context)
) : ViewModel() {

    private val _uiState = MutableStateFlow(PostsUiState())
    val uiState: StateFlow<PostsUiState> = _uiState.asStateFlow()

    private val pageSize = 100
    private var searchJob: kotlinx.coroutines.Job? = null

    init {
        viewModelScope.launch {
            checkCacheAndOfflineStatus()
        }
    }

    private suspend fun checkCacheAndOfflineStatus() {
        val hasCachedData = postService.hasCachedData()
        val isOffline = context?.let {
            !com.project.repository.NetworkUtil.isNetworkAvailable(it)
        } ?: false

        _uiState.value = _uiState.value.copy(
            hasCachedData = hasCachedData,
            isOffline = isOffline
        )
    }

    fun loadPosts() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                error = null,
                currentPage = 1
            )

            checkCacheAndOfflineStatus()

            try {
                val posts = postService.getPostsWithFavorites(page = 1, limit = pageSize)
                _uiState.value = _uiState.value.copy(
                    allPosts = posts,
                    displayedPosts = if (_uiState.value.searchQuery.isEmpty()) posts else filterPosts(posts, _uiState.value.searchQuery),
                    isLoading = false,
                    hasMoreData = posts.size == pageSize && !_uiState.value.isOffline,
                    currentPage = 1
                )

                postService.syncCacheWithFavorites()
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = if (_uiState.value.isOffline && !_uiState.value.hasCachedData) {
                        "No internet connection and no cached data available. Please connect to the internet to load posts."
                    } else {
                        e.message ?: "Unknown error occurred"
                    }
                )
            }
        }
    }

    fun loadMorePosts() {
        val currentState = _uiState.value
        if (currentState.isLoading || !currentState.hasMoreData || currentState.searchQuery.isNotEmpty() || currentState.isOffline) return

        viewModelScope.launch {
            _uiState.value = currentState.copy(isLoading = true)

            try {
                val nextPage = currentState.currentPage + 1
                val newPosts = postService.getPostsWithFavorites(page = nextPage, limit = pageSize)
                val updatedAllPosts = currentState.allPosts + newPosts

                _uiState.value = _uiState.value.copy(
                    allPosts = updatedAllPosts,
                    displayedPosts = updatedAllPosts,
                    isLoading = false,
                    hasMoreData = newPosts.size == pageSize,
                    currentPage = nextPage
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Error loading more posts"
                )
            }
        }
    }

    fun toggleFavorite(post: Post) {
        viewModelScope.launch {
            try {
                val newFavoriteStatus = postService.toggleFavorite(post)
                val updatedPost = post.copy(isFavorite = newFavoriteStatus)

                val updatedAllPosts = _uiState.value.allPosts.map {
                    if (it.id == post.id) updatedPost else it
                }
                val updatedDisplayedPosts = _uiState.value.displayedPosts.map {
                    if (it.id == post.id) updatedPost else it
                }

                _uiState.value = _uiState.value.copy(
                    allPosts = updatedAllPosts,
                    displayedPosts = updatedDisplayedPosts
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "Failed to update favorite: ${e.message}"
                )
            }
        }
    }

    fun updateUISearchQuery(query: String) {
        _uiState.value = _uiState.value.copy(uiSearchQuery = query)
    }

    fun searchPosts(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                searchQuery = query.trim(),
                uiSearchQuery = query
            )

            if (query.trim().isEmpty()) {
                _uiState.value = _uiState.value.copy(
                    displayedPosts = _uiState.value.allPosts
                )
                return@launch
            }

            try {
                val serviceWithSearch = postService as? PostServiceImpl
                val searchResults = if (serviceWithSearch != null) {
                    serviceWithSearch.searchPosts(query.trim())
                } else {
                    filterPosts(_uiState.value.allPosts, query.trim())
                }

                _uiState.value = _uiState.value.copy(
                    displayedPosts = searchResults
                )
            } catch (e: Exception) {
                val filteredPosts = filterPosts(_uiState.value.allPosts, query.trim())
                _uiState.value = _uiState.value.copy(
                    displayedPosts = filteredPosts
                )
            }
        }
    }

    private fun filterPosts(posts: List<Post>, query: String): List<Post> {
        if (query.isEmpty()) return posts

        return posts.filter { post ->
            post.title.contains(query, ignoreCase = true) ||
                    post.body.contains(query, ignoreCase = true)
        }
    }

    fun clearSearch() {
        searchJob?.cancel()
        _uiState.value = _uiState.value.copy(
            searchQuery = "",
            uiSearchQuery = "",
            displayedPosts = _uiState.value.allPosts
        )
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }

    fun refreshData() {
        viewModelScope.launch {
            checkCacheAndOfflineStatus()
            loadPosts()
        }
    }
}