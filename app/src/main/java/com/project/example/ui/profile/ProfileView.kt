package com.project.example.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.project.example.ui.theme.BackgroundColor2
import com.project.repository.SessionManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileView(navController: NavHostController) {
    val context = LocalContext.current
    val profileUri = remember { loadProfileImage(context) }
    val user = SessionManager.loginData
        ?: return

    val accountDetails = listOf(
        "Username"     to user.username,
        "First name"   to user.firstname,
        "Last name"    to user.lastname,
        "Govt. ID"     to user.gid,
        "Date Of Birth" to user.dob,
        "Address"      to (user.address.ifBlank { "—" }),
        "Mobile"       to user.mobile,
        "Email"        to user.email
    )

    Scaffold(
        containerColor = if (isSystemInDarkTheme()) Color.Black else BackgroundColor2,
    ) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(16.dp))

            // Avatar
            Box(
                Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(50))
                    .background(Color(0xFF3B3BFF)),
                contentAlignment = Alignment.Center
            ) {
                if (profileUri != null) {
                    AsyncImage(
                        model = profileUri,
                        contentDescription = "Profile picture",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Icon(
                        Icons.Default.Person,
                        contentDescription = "Default avatar",
                        modifier = Modifier.size(48.dp),
                        tint = Color.White.copy(alpha = 0.6f)
                    )
                }
            }

            Spacer(Modifier.height(12.dp))

            Text(
                "${user.firstname} ${user.lastname}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                user.email,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(Modifier.height(24.dp))

            // === DETAILS CARD ===
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = BackgroundColor2
                ),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column {
                    accountDetails.forEachIndexed { index, pair ->
                        DetailRow(label = pair.first, value = pair.second)
                        if (index < accountDetails.lastIndex) {
                            Divider()
                        }
                    }
                }
            }

//            Spacer(Modifier.height(32.dp))
//
//            Button(
//                onClick = { /* TODO: save / update */ },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(48.dp),
//                shape = RoundedCornerShape(12.dp),
//                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3B3BFF))
//            ) {
//                Text(
//                    "Update Profile",
//                    style = MaterialTheme.typography.titleMedium.copy(color = Color.White)
//                )
//            }
        }
    }
}

@Composable
private fun DetailRow(label: String, value: String) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
