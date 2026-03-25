package com.example.vpn.ui.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.vpn.ui.theme.*
import androidx.compose.foundation.clickable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen() {
    var autoConnect by remember { mutableStateOf(false) }
    var notifications by remember { mutableStateOf(true) }
    var selectedProtocol by remember { mutableStateOf("VLESS + Reality") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Settings",
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
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Connection Section
                SettingsSection(title = "Connection") {
                    SettingsSwitchItem(
                        icon = Icons.Default.Done,
                        title = "Auto-connect",
                        subtitle = "Connect automatically on app start",
                        checked = autoConnect,
                        onCheckedChange = { autoConnect = it }
                    )
                    SettingsSwitchItem(
                        icon = Icons.Default.Notifications,
                        title = "Notifications",
                        subtitle = "Show connection alerts",
                        checked = notifications,
                        onCheckedChange = { notifications = it }
                    )
                }

                // Protocol Section
                SettingsSection(title = "VPN Protocol") {
                    SettingsDropdownItem(
                        icon = Icons.Default.Lock,
                        title = "Protocol",
                        value = selectedProtocol,
                        options = listOf("VLESS + Reality", "Shadowsocks", "Hysteria2", "WireGuard"),
                        onValueSelected = { selectedProtocol = it }
                    )
                }

                // Appearance Section
                SettingsSection(title = "Appearance") {
                    SettingsSelectItem(
                        icon = Icons.Default.CheckCircle,
                        title = "Theme",
                        value = "System default"
                    )
                }

                // About Section
                SettingsSection(title = "About") {
                    SettingsInfoItem(
                        icon = Icons.Default.Info,
                        title = "Version",
                        value = "1.0.0"
                    )
                    SettingsInfoItem(
                        icon = Icons.Default.FavoriteBorder,
                        title = "Privacy Policy",
                        value = ""
                    )
                    SettingsInfoItem(
                        icon = Icons.Default.Favorite,
                        title = "Rate Us",
                        value = ""
                    )
                }
            }
        }
    }
}

@Composable
fun SettingsSection(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = PastelSurface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                color = PastelTextSecondary
            )
            Divider(color = PastelDivider)
            content()
        }
    }
}

@Composable
fun SettingsSwitchItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = PastelPrimary,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
                color = PastelTextPrimary
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = PastelTextSecondary
            )
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = PastelPrimary,
                checkedTrackColor = PastelPrimary.copy(alpha = 0.5f)
            )
        )
    }
}

@Composable
fun SettingsDropdownItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    value: String,
    options: List<String>,
    onValueSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = true },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = PastelPrimary,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
                color = PastelTextPrimary
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodySmall,
                color = PastelTextSecondary
            )
        }
        Icon(
            Icons.Default.KeyboardArrowDown,
            contentDescription = "Select",
            tint = PastelTextSecondary
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onValueSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun SettingsSelectItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = PastelPrimary,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
                color = PastelTextPrimary
            )
        }
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = PastelTextSecondary
        )
        Spacer(modifier = Modifier.width(8.dp))
        Icon(
            Icons.Default.KeyboardArrowRight,
            contentDescription = null,
            tint = PastelTextSecondary
        )
    }
}

@Composable
fun SettingsInfoItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = PastelPrimary,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium,
            color = PastelTextPrimary,
            modifier = Modifier.weight(1f)
        )
        if (value.isNotEmpty()) {
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium,
                color = PastelTextSecondary
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
        Icon(
            Icons.Default.KeyboardArrowRight,
            contentDescription = null,
            tint = PastelTextSecondary,
            modifier = Modifier.size(20.dp)
        )
    }
}