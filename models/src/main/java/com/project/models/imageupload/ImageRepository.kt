package com.project.models.imageupload
import java.io.File

interface ImageRepository {
    suspend fun uploadProfileImage(username: String, imageFile: File): Boolean
}