package com.project.example.ui.home

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.project.models.posts.Post
import com.project.repository.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DisplayPostsView(
    context: Context? = null,
    viewModel: PostsViewModel = viewModel { PostsViewModel(context) }
) {
    val uiState by viewModel.uiState.collectAsState()
    val listState = rememberLazyListState()
    val searchQuery = uiState.uiSearchQuery

    uiState.error?.let { error ->
        LaunchedEffect(error) {
            viewModel.clearError()
        }
    }

    LaunchedEffect(uiState.searchQuery) {
        if (uiState.searchQuery.isNotEmpty()) {
            listState.animateScrollToItem(0)
        }
    }

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
            .padding(8.dp)
    ) {
        TopAppBar(
            title = { Text("Posts") }
        )

        SearchBar(
            query = searchQuery,
            onQueryChange = { newQuery ->
                viewModel.updateUISearchQuery(newQuery)
                viewModel.searchPosts(newQuery)
            },
            onClearSearch = {
                viewModel.clearSearch()
            }
        )


        Spacer(modifier = Modifier.height(16.dp))

        if (uiState.isOffline) {
            OfflineIndicator(
                hasCachedData = uiState.hasCachedData,
                onRefresh = { viewModel.refreshData() }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        when {
            uiState.isLoading && uiState.displayedPosts.isEmpty() -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator()
                        if (uiState.isOffline) {
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "Loading cached posts...",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
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
                if (uiState.searchQuery.isNotEmpty()) {
                    Text(
                        text = "${uiState.displayedPosts.size} results found${if (uiState.isOffline) " (cached)" else ""}",
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
                            searchQuery = uiState.searchQuery,
                            onFavoriteClick = { viewModel.toggleFavorite(post) }
                        )
                    }

                    if (uiState.isLoading && uiState.searchQuery.isEmpty() && !uiState.isOffline) {
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

                    if (uiState.isOffline && uiState.displayedPosts.isNotEmpty()) {
                        item {
                            OfflineBadge()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun OfflineIndicator(
    hasCachedData: Boolean,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.cloudoffline),
                    contentDescription = "Offline",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = "You're offline",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = if (hasCachedData) {
                            "Showing cached posts"
                        } else {
                            "No cached data available"
                        },
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            TextButton(
                onClick = onRefresh
            ) {
                Text("Refresh")
            }
        }
    }
}

@Composable
fun OfflineBadge(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.cloudoffline),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Cached content",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
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
    onFavoriteClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    HighlightedText(
                        text = post.title,
                        searchQuery = searchQuery,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                }

                IconButton(
                    onClick = onFavoriteClick,
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Icon(
                        imageVector = if (post.isFavorite) {
                            Icons.Filled.Favorite
                        } else {
                            Icons.Filled.FavoriteBorder
                        },
                        contentDescription = if (post.isFavorite) {
                            "Remove from favorites"
                        } else {
                            "Add to favorites"
                        },
                        tint = if (post.isFavorite) {
                            MaterialTheme.colorScheme.error
                        } else {
                            MaterialTheme.colorScheme.onSurfaceVariant
                        }
                    )
                }
            }

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
    style: androidx.compose.ui.text.TextStyle = MaterialTheme.typography.bodyMedium,
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

                if (index > startIndex) {
                    append(text.substring(startIndex, index))
                }

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