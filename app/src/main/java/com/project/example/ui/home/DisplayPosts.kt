package com.project.example.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.project.models.posts.Post

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DisplayPosts(
    viewModel: PostsViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val listState = rememberLazyListState()
    var searchQuery by remember { mutableStateOf("") }

    // Reset scroll position when search results change
    LaunchedEffect(uiState.searchQuery) {
        if (uiState.searchQuery.isNotEmpty()) {
            listState.animateScrollToItem(0)
        }
    }

    // Trigger pagination when reaching near end (only for non-search mode)
    LaunchedEffect(listState, uiState.searchQuery) {
        if (uiState.searchQuery.isEmpty()) {
            snapshotFlow {
                listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
            }.collect { lastVisibleIndex ->
                val totalItems = listState.layoutInfo.totalItemsCount
                if (lastVisibleIndex != null &&
                    lastVisibleIndex >= totalItems - 5 &&
                    !uiState.isLoading &&
                    uiState.hasMoreData) {
                    viewModel.loadMorePosts()
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.loadPosts()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TopAppBar(
            title = { Text("Posts") }
        )

        // Search Bar
        SearchBar(
            query = searchQuery,
            onQueryChange = { newQuery ->
                searchQuery = newQuery
                viewModel.searchPosts(newQuery)
            },
            onClearSearch = {
                searchQuery = ""
                viewModel.clearSearch()
            }
        )


        Spacer(modifier = Modifier.height(16.dp))

        when {
            uiState.isLoading && uiState.displayedPosts.isEmpty() -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            uiState.error != null && uiState.displayedPosts.isEmpty() -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Error: ${uiState.error}",
                        color = MaterialTheme.colorScheme.error
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { viewModel.loadPosts() }
                    ) {
                        Text("Retry")
                    }
                }
            }

            uiState.searchQuery.isNotEmpty() && uiState.displayedPosts.isEmpty() && !uiState.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "No posts found",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            text = "Try a different search term",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            else -> {
                // Show search results count
                if (uiState.searchQuery.isNotEmpty()) {
                    Text(
                        text = "${uiState.displayedPosts.size} results found",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                LazyColumn(
                    state = listState,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(uiState.displayedPosts) { post ->
                        PostItem(
                            post = post,
                            searchQuery = uiState.searchQuery
                        )
                    }

                    // Only show loading indicator for pagination (not search)
                    if (uiState.isLoading && uiState.searchQuery.isEmpty()) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onClearSearch: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier.fillMaxWidth(),
        placeholder = { Text("Search posts by title or body...") },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search"
            )
        },
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(onClick = onClearSearch) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "Clear search"
                    )
                }
            }
        },
        singleLine = true
    )
}

@Composable
fun PostItem(
    post: Post,
    searchQuery: String = "",
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            HighlightedText(
                text = post.title,
                searchQuery = searchQuery,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            HighlightedText(
                text = post.body,
                searchQuery = searchQuery,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun HighlightedText(
    text: String,
    searchQuery: String,
    style: TextStyle = MaterialTheme.typography.bodyMedium,
    fontWeight: FontWeight? = null,
    modifier: Modifier = Modifier
) {
    if (searchQuery.isEmpty() || !text.contains(searchQuery, ignoreCase = true)) {
        Text(
            text = text,
            style = style,
            fontWeight = fontWeight,
            modifier = modifier
        )
    } else {
        val annotatedString = buildAnnotatedString {
            val lowerText = text.lowercase()
            val lowerQuery = searchQuery.lowercase()
            var startIndex = 0

            while (startIndex < text.length) {
                val index = lowerText.indexOf(lowerQuery, startIndex)
                if (index == -1) {
                    append(text.substring(startIndex))
                    break
                }

                // Add text before match
                if (index > startIndex) {
                    append(text.substring(startIndex, index))
                }

                // Add highlighted match
                withStyle(
                    style = SpanStyle(
                        background = MaterialTheme.colorScheme.primaryContainer,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                ) {
                    append(text.substring(index, index + searchQuery.length))
                }

                startIndex = index + searchQuery.length
            }
        }

        Text(
            text = annotatedString,
            style = style,
            fontWeight = fontWeight,
            modifier = modifier
        )
    }
}