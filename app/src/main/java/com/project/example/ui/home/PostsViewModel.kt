package com.project.example.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.models.posts.Post
import com.project.models.posts.PostService
import com.project.service.posts.PostServiceImpl
import kotlinx.coroutines.Job
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
    val searchQuery: String = ""
)

class PostsViewModel(
    private val postService: PostService = PostServiceImpl()
) : ViewModel() {

    private val _uiState = MutableStateFlow(PostsUiState())
    val uiState: StateFlow<PostsUiState> = _uiState.asStateFlow()

    private val pageSize = 10
    private var searchJob: Job? = null

    fun loadPosts() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                error = null,
                currentPage = 1
            )

            try {
                val posts = postService.getPosts(page = 1, limit = pageSize)
                _uiState.value = _uiState.value.copy(
                    allPosts = posts,
                    displayedPosts = if (_uiState.value.searchQuery.isEmpty()) posts else filterPosts(posts, _uiState.value.searchQuery),
                    isLoading = false,
                    hasMoreData = posts.size == pageSize,
                    currentPage = 1
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Unknown error occurred"
                )
            }
        }
    }

    fun loadMorePosts() {
        val currentState = _uiState.value
        if (currentState.isLoading || !currentState.hasMoreData || currentState.searchQuery.isNotEmpty()) return

        viewModelScope.launch {
            _uiState.value = currentState.copy(isLoading = true)

            try {
                val nextPage = currentState.currentPage + 1
                val newPosts = postService.getPosts(page = nextPage, limit = pageSize)
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

    fun searchPosts(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                searchQuery = query.trim()
            )

            if (query.trim().isEmpty()) {
                _uiState.value = _uiState.value.copy(
                    displayedPosts = _uiState.value.allPosts
                )
                return@launch
            }

            // If we don't have enough posts loaded, load all posts for comprehensive search
            if (_uiState.value.allPosts.size < 100 && _uiState.value.hasMoreData) {
                loadAllPostsForSearch(query.trim())
            } else {
                val filteredPosts = filterPosts(_uiState.value.allPosts, query.trim())
                _uiState.value = _uiState.value.copy(
                    displayedPosts = filteredPosts
                )
            }
        }
    }

    private suspend fun loadAllPostsForSearch(query: String) {
        _uiState.value = _uiState.value.copy(isLoading = true)

        try {
            val allPosts = postService.getAllPosts()
            val filteredPosts = filterPosts(allPosts, query)

            _uiState.value = _uiState.value.copy(
                allPosts = allPosts,
                displayedPosts = filteredPosts,
                isLoading = false,
                hasMoreData = false
            )
        } catch (e: Exception) {
            _uiState.value = _uiState.value.copy(
                isLoading = false,
                error = e.message ?: "Error searching posts"
            )
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
            displayedPosts = _uiState.value.allPosts
        )
    }

    fun retry() {
        if (_uiState.value.searchQuery.isNotEmpty()) {
            searchPosts(_uiState.value.searchQuery)
        } else {
            loadPosts()
        }
    }
}