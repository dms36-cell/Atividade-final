package com.example.demetrius.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.demetrius.data.model.Post
import com.example.demetrius.data.repository.PostRepository
import com.example.demetrius.ui.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException

class PostViewModel(private val repository: PostRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Post>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Post>>> = _uiState.asStateFlow()

    init {
        fetchPosts()
    }

    fun fetchPosts() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val posts = repository.getPosts()
                _uiState.value = UiState.Success(posts)
            } catch (e: IOException) {
                _uiState.value = UiState.Error("Network Error: ${e.message}")
            } catch (e: Exception) {
                _uiState.value = UiState.Error("Error: ${e.message}")
            }
        }
    }
}

class PostViewModelFactory(private val repository: PostRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PostViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PostViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
