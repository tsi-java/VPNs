package com.example.vpn.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vpn.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Profile",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = PastelTextPrimary
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    scrolledContainerColor = PastelBackground
                )
            )
        },
        containerColor = PastelBackground
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(PastelGradientStart, PastelGradientEnd)
                    )
                )
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Аватар
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(PastelPrimary, PastelPrimaryDark)
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Avatar",
                        tint = Color.White,
                        modifier = Modifier.size(48.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "User Name",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = PastelTextPrimary
                )

                Text(
                    text = "user@example.com",
                    style = MaterialTheme.typography.bodyMedium,
                    color = PastelTextSecondary
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Subscription Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = PastelSurface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Current Plan",
                            style = MaterialTheme.typography.titleSmall,
                            color = PastelTextSecondary
                        )
                        Text(
                            text = "Premium",
                            style = MaterialTheme.typography.displaySmall,
                            fontWeight = FontWeight.Bold,
                            color = PastelPrimary
                        )
                        Text(
                            text = "Active until Dec 31, 2025",
                            style = MaterialTheme.typography.bodySmall,
                            color = PastelTextSecondary
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { /* TODO */ },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = PastelPrimary
                            ),
                            shape = RoundedCornerShape(32.dp)
                        ) {
                            Text("Upgrade Plan", color = Color.White)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Stats Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = PastelSurface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        ProfileStat("Total Data", "45.2 GB")
                        ProfileStat("Days Active", "127")
                        ProfileStat("Devices", "3")
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Logout Button
                OutlinedButton(
                    onClick = { /* TODO */ },
                    shape = RoundedCornerShape(32.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = PastelError
                    )
                ) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "Logout",
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Logout")
                }
            }
        }
    }
}

@Composable
fun ProfileStat(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = PastelTextPrimary
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = PastelTextSecondary
        )
    }
}