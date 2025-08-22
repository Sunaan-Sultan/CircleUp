package com.project.example.ui.profile

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.project.example.PreferencesManager
import com.project.example.ui.theme.BackgroundColor2
import com.project.example.ui.theme.PrimaryColor
import com.project.example.ui.theme.White
import com.project.repository.SessionManager
import com.project.service.imageupload.ImageServiceImpl
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

@Composable
fun ProfileScreen(navController: NavHostController) {
    val scope = rememberCoroutineScope()
//    val user = SessionManager.loginData
//        ?: return
    val context = LocalContext.current
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val bitmapImage = remember { mutableStateOf<Bitmap?>(null) }
    var showDialog by remember { mutableStateOf(false) }
    var profileImagePath by remember { mutableStateOf<String?>(null) }
    val prefs = PreferencesManager(context)

    // Load saved image
    LaunchedEffect(Unit) {
        profileImagePath = loadProfileImage(context)
        profileImagePath?.let {
            bitmapImage.value = BitmapFactory.decodeFile(it)
        }
    }

    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                getBitmapFromUri(context, it)?.also { bmp ->
                    // 1) save locally
                    saveProfileImage(context, bmp)
                    bitmapImage.value = bmp

                    // 2) upload to server
                    scope.launch {
                        // Construct the File you just wrote
                        val imageFile = File(context.filesDir, "profile_image.jpg")
                        val username = SessionManager.currentUser?.username.orEmpty()
                        val success = ImageServiceImpl(context)
                            .uploadProfileImage(username, imageFile)

                        if (success) {
                            Toast.makeText(context, "Upload successful", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, "Upload failed", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

    val cameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { bmp ->
            bmp?.also {
                // 1) save locally
                saveProfileImage(context, it)
                bitmapImage.value = it

                // 2) upload
                scope.launch {
                    val imageFile = File(context.filesDir, "profile_image.jpg")
                    val username = SessionManager.currentUser?.username.orEmpty()
                    val success = ImageServiceImpl(context)
                        .uploadProfileImage(username, imageFile)

                    if (success) Toast.makeText(context, "Upload successful", Toast.LENGTH_SHORT).show()
                    else Toast.makeText(context, "Upload failed", Toast.LENGTH_SHORT).show()
                }
            }
        }

    val camPermission =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) cameraLauncher.launch(null)
        }
    val storagePermission =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) {
//            granted ->
//            if (granted)
            galleryLauncher.launch("image/*")
        }

    Scaffold { padding ->
        val scrollState = rememberScrollState()
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .background((if (isSystemInDarkTheme()) Color.Black else BackgroundColor2))
        ) {
            // === Header Card ===
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                colors = CardDefaults.cardColors(containerColor = PrimaryColor),
                shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Row(
                    Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        Modifier
                            .size(72.dp)
                            .clip(CircleShape)
                            .background(Color.Gray)
                            .clickable { showDialog = true },
                        contentAlignment = Alignment.Center
                    ) {
                        if (bitmapImage.value != null || imageUri != null) {
                            if (bitmapImage.value != null) {
                                Image(
                                    bitmap = bitmapImage.value!!.asImageBitmap(),
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                            } else {
                                AsyncImage(
                                    model = imageUri,
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                        } else {
                            Icon(
                                Icons.Default.Person,
                                contentDescription = null,
                                modifier = Modifier.size(36.dp),
                                tint = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f)
                            )
                        }
                    }
                    Spacer(Modifier.width(16.dp))
                    Column(
                        Modifier.weight(1f),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Guest",
                            style = MaterialTheme.typography.headlineSmall.copy(
                                color = Color.White,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                        Text(
                            text = "",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = Color.White.copy(alpha = 0.8f)
                            )
                        )
                    }
                    IconButton(onClick = { navController.navigate("bio") }) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit",
                            tint = Color.White
                        )
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            Column(
                Modifier
                    .verticalScroll(scrollState)
                    .fillMaxSize()
                    .padding(padding)
                    .background((if (isSystemInDarkTheme()) Color.Black else BackgroundColor2))
            ) {
                // === Main Options Card ===
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    colors = CardDefaults.cardColors(containerColor = White),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column {
                        ProfileItem(
                            icon = Icons.Default.Person,
                            title = "My Account",
                            subtitle = "Make changes to your account",
                            trailing = {
                                Icon(
                                    Icons.Default.Warning,
                                    contentDescription = null,
                                    tint = Color(0xFFF44336)
                                )
                            }
                        ) {
                            navController.navigate("profile-info")
                        }
                        Divider()
                        ProfileItem(
                            icon = Icons.Default.Person,
                            title = "Saved Beneficiary",
                            subtitle = "Manage your saved account"
                        )
                        Divider()
                        ProfileItem(
                            icon = Icons.Default.Lock,
                            title = "Face ID / Touch ID",
                            subtitle = "Manage your device security",
                            trailing = {
                                Switch(checked = false, onCheckedChange = { /*TODO*/ })
                            }
                        ) {

                        }
                        Divider()
                        ProfileItem(
                            icon = Icons.Default.CheckCircle,
                            title = "Two-Factor Authentication",
                            subtitle = "Further secure your account for safety",
                            trailing = {
                                Icon(
                                    Icons.Default.KeyboardArrowRight,
                                    contentDescription = null
                                )
                            }
                        )
                        Divider()
                        ProfileItem(
                            icon = Icons.Default.ExitToApp,
                            title = "Log out",
                            subtitle = "Further secure your account for safety",
                            trailing = {
                                Icon(
                                    Icons.Default.KeyboardArrowRight,
                                    contentDescription = null
                                )
                            },
                            onClick = {
                                // 1) clear saved credentials
                                prefs.removeKey("userRole")
                                prefs.removeUsername("user")
                                prefs.removePassword("pass")
                                // 2) clear in-memory session
                                SessionManager.loginData = null
                                // 3) navigate back to login
                                navController.navigate("login") {
                                    popUpTo("home") { inclusive = true }
                                    launchSingleTop = true
                                }
                            }
                        )
                    }
                }

                Spacer(Modifier.height(32.dp))

                // === More Card ===
                Text(
                    text = "More",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(start = 24.dp, bottom = 8.dp)
                )

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    colors = CardDefaults.cardColors(containerColor = White),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column {
                        ProfileItem(
                            icon = Icons.Default.Notifications,
                            title = "Notification"
                        )
                        Divider()
                        ProfileItem(
                            icon = Icons.Default.MoreVert,
                            title = "Help & Support"
                        )
                        Divider()
                        ProfileItem(
                            icon = Icons.Default.Share,
                            title = "About App"
                        )
                    }
                }
                Spacer(Modifier.height(16.dp))
            }
        }

        // === Upload Dialog ===
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text(text = "Upload Profile Picture") },
                text = { Text(text = "Choose an option to upload your profile picture.") },
                confirmButton = {
                    TextButton(onClick = {
                        camPermission.launch(android.Manifest.permission.CAMERA)
                        showDialog = false
                    }) {
                        Text(text = "Open Camera")
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        storagePermission.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                        showDialog = false
                    }) {
                        Text(text = "Open Gallery")
                    }
                }
            )
        }
    }
}

@Composable
private fun ProfileItem(
    icon: ImageVector,
    title: String,
    subtitle: String? = null,
    trailing: (@Composable () -> Unit)? = null,
    onClick: () -> Unit = {}
) {
    Row(
        Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, modifier = Modifier.size(24.dp))
        Spacer(Modifier.width(16.dp))
        Column(Modifier.weight(1f)) {
            Text(text = title, style = MaterialTheme.typography.bodyLarge)
            subtitle?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                )
            }
        }
        trailing?.invoke()
    }
}


fun getBitmapFromUri(context: Context, uri: Uri): Bitmap? {
    return try {
        // Open an input stream for the Uri and decode it into a Bitmap
        val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
        BitmapFactory.decodeStream(inputStream)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun saveProfileImage(context: Context, bitmap: Bitmap) {
    try {
        val file = File(context.filesDir, "profile_image.jpg")
        val outputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        outputStream.flush()
        outputStream.close()
    } catch (e: IOException) {
        Log.e("ProfileScreen", "Error saving profile image", e)
    }
}

fun loadProfileImage(context: Context): String? {
    val file = File(context.filesDir, "profile_image.jpg")
    return if (file.exists()) file.absolutePath else null
}