package com.example.demetrius.data.repository

import com.example.demetrius.data.model.Post
import com.example.demetrius.data.remote.ApiService

class PostRepository(private val apiService: ApiService) {
    suspend fun getPosts(): List<Post> {
        return apiService.getPosts()
    }

    suspend fun getPost(id: Int): Post {
        return apiService.getPost(id)
    }
}
