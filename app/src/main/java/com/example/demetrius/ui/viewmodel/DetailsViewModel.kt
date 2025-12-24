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

class DetailsViewModel(private val repository: PostRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<Post>>(UiState.Loading)
    val uiState: StateFlow<UiState<Post>> = _uiState.asStateFlow()

    fun getPost(id: Int) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val post = repository.getPost(id)
                _uiState.value = UiState.Success(post)
            } catch (e: Exception) {
                _uiState.value = UiState.Error("Error: ${e.message}")
            }
        }
    }
}

class DetailsViewModelFactory(private val repository: PostRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DetailsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
