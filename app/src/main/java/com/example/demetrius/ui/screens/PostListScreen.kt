package com.example.demetrius.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.demetrius.data.model.Post
import com.example.demetrius.data.remote.RetrofitClient
import com.example.demetrius.data.repository.PostRepository
import com.example.demetrius.ui.state.UiState
import com.example.demetrius.ui.viewmodel.PostViewModel
import com.example.demetrius.ui.viewmodel.PostViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostListScreen(
    navController: NavController,
    viewModel: PostViewModel = viewModel(
        factory = PostViewModelFactory(PostRepository(RetrofitClient.apiService))
    )
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Posts") },
                actions = {
                    IconButton(onClick = { navController.navigate("settings") }) {
                        Icon(Icons.Filled.Settings, contentDescription = "Settings")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (val state = uiState) {
                is UiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                is UiState.Success -> {
                    PostList(posts = state.data, onPostClick = { post ->
                        navController.navigate("details/${post.id}")
                    })
                }
                is UiState.Error -> {
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = state.message, color = MaterialTheme.colorScheme.error)
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = { viewModel.fetchPosts() }) {
                            Text("Retry")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PostList(posts: List<Post>, onPostClick: (Post) -> Unit) {
    LazyColumn {
        items(posts) { post ->
            PostItem(post = post, onClick = { onPostClick(post) })
        }
    }
}

@Composable
fun PostItem(post: Post, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = post.title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = post.body,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
