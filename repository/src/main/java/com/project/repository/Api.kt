package com.project.repository
import com.project.models.imageupload.ImageUploadResponse
import com.project.models.posts.Post
import com.project.models.security.LoginRequest
import com.project.models.security.LoginResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @POST("login")
    suspend fun loginUser(
        @Body loginRequest: LoginRequest
    ): Response<LoginResponse>

    @Multipart
    @POST("v1/upload")
    suspend fun uploadProfileImage(
        @Header("Authorization") token: String,
        @Part("username") username: RequestBody,
        @Part image: MultipartBody.Part
    ): Response<ImageUploadResponse>

    @GET("posts")
    suspend fun getPosts(
        @Query("_page") page: Int,
        @Query("_limit") limit: Int
    ): List<Post>

    @GET("posts/{id}")
    suspend fun getPost(@Path("id") id: Int): Post

    @GET("posts")
    suspend fun getAllPosts(): List<Post>
}


