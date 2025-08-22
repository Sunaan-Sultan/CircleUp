package com.project.example.ui.history

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.project.example.ui.theme.BackgroundColor2

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductView(
    patientId: String,
    onSubmit: (
        title: String,
        description: String,
        tags: List<String>,
        imageUri: Uri?
    ) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var tagsText by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri -> imageUri = uri }

    Scaffold(
        containerColor = if (isSystemInDarkTheme()) Color.Black else BackgroundColor2
    ) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = tagsText,
                onValueChange = { tagsText = it },
                label = { Text("Tags (comma separated)") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = { launcher.launch("image/*") },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Icon(Icons.Default.AddCircle, contentDescription = "Upload")
                Spacer(Modifier.width(8.dp))
                Text("Upload Photo")
            }

            imageUri?.let {
                Image(
                    painter = rememberAsyncImagePainter(it),
                    contentDescription = "Selected",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )
            }

            Spacer(Modifier.weight(1f))

            // Normal submit button at the bottom
            Button(
                onClick = {
                    onSubmit(
                        title.trim(),
                        description.trim(),
                        tagsText
                            .split(',')
                            .map { it.trim() }
                            .filter { it.isNotEmpty() },
                        imageUri
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text("Submit")
            }
        }
    }
}


