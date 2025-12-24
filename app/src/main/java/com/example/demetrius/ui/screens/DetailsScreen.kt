package com.example.demetrius.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.demetrius.data.remote.RetrofitClient
import com.example.demetrius.data.repository.PostRepository
import com.example.demetrius.ui.state.UiState
import com.example.demetrius.ui.viewmodel.DetailsViewModel
import com.example.demetrius.ui.viewmodel.DetailsViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    navController: NavController,
    postId: Int,
    viewModel: DetailsViewModel = viewModel(
        factory = DetailsViewModelFactory(PostRepository(RetrofitClient.apiService))
    )
) {
    LaunchedEffect(postId) {
        viewModel.getPost(postId)
    }

    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Post Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            when (val state = uiState) {
                is UiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                is UiState.Success -> {
                    Column {
                        Text(text = state.data.title, style = MaterialTheme.typography.headlineMedium)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(text = state.data.body, style = MaterialTheme.typography.bodyLarge)
                    }
                }
                is UiState.Error -> {
                    Text(
                        text = state.message,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}
