package com.project.models
import com.project.models.imageupload.ImageUploadResponse
import com.project.models.security.LoginRequest
import com.project.models.security.LoginResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

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

}


