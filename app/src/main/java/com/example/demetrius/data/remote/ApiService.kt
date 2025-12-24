package com.example.demetrius.data.remote

import com.example.demetrius.data.model.Post
import retrofit2.http.GET

interface ApiService {
    @GET("posts")
    suspend fun getPosts(): List<Post>

    @GET("posts/{id}")
    suspend fun getPost(@retrofit2.http.Path("id") id: Int): Post
}
