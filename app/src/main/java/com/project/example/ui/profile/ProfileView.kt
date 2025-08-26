package com.project.example.ui.profile

import android.graphics.Bitmap
import android.net.Uri
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
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Share
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
import com.project.repository.security.SessionManager
import com.project.service.security.AuthenticationService

@Composable
fun ProfileScreen(navController: NavHostController) {
    val context = LocalContext.current
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val bitmapImage = remember { mutableStateOf<Bitmap?>(null) }
    var showDialog by remember { mutableStateOf(false) }
    val prefs = PreferencesManager(context)
    val authService = AuthenticationService(context)

    Scaffold { padding ->
        val scrollState = rememberScrollState()
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .background((if (isSystemInDarkTheme()) Color.Black else BackgroundColor2))
        ) {
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
                    IconButton(onClick = { }) {
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
                                    Icons.Default.KeyboardArrowRight,
                                    contentDescription = null,
                                )
                            }
                        )
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
                            subtitle = "Sign out from your account",
                            trailing = {
                                Icon(
                                    Icons.Default.KeyboardArrowRight,
                                    contentDescription = null
                                )
                            },
                            onClick = {
                                authService.logout()
                                navController.navigate("login") {
                                    popUpTo("home") { inclusive = true }
                                    launchSingleTop = true
                                }
                            }
                        )
                    }
                }

                Spacer(Modifier.height(32.dp))

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