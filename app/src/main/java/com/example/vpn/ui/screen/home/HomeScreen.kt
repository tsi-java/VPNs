package com.example.vpn.ui.screens.home

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vpn.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    var isConnected by remember { mutableStateOf(false) }
    var selectedServer by remember { mutableStateOf("Netherlands") }
    var selectedFlag by remember { mutableStateOf("🇳🇱") }

    val connectionStatus = if (isConnected) "Connected" else "Disconnected"
    val statusColor = if (isConnected) PastelSuccess else PastelError
    val buttonGradient = if (isConnected)
        Brush.horizontalGradient(listOf(PastelError, PastelError.copy(alpha = 0.8f)))
    else
        Brush.horizontalGradient(listOf(PastelPrimary, PastelPrimaryDark))

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "VPN Shield",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = PastelTextPrimary
                    )
                },
                actions = {
                    IconButton(onClick = { /* TODO */ }) {
                        Icon(
                            Icons.Default.Settings,
                            contentDescription = "Settings",
                            tint = PastelTextSecondary
                        )
                    }
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
                // Анимированная кнопка подключения
                AnimatedConnectButton(
                    isConnected = isConnected,
                    statusColor = statusColor,
                    buttonGradient = buttonGradient,
                    onClick = { isConnected = !isConnected }
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Статус
                Text(
                    text = connectionStatus,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = statusColor,
                    letterSpacing = 0.5.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = if (isConnected) "Your connection is secure" else "Tap to connect",
                    style = MaterialTheme.typography.bodyMedium,
                    color = PastelTextSecondary
                )

                Spacer(modifier = Modifier.height(48.dp))

                // Карточка выбора сервера
                ServerSelectionCard(
                    selectedServer = selectedServer,
                    selectedFlag = selectedFlag,
                    onServerClick = {
                        // TODO: диалог выбора сервера
                    }
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Статистика
                StatisticsCard()

                Spacer(modifier = Modifier.height(32.dp))

                // Преимущества
                FeaturesRow()
            }
        }
    }
}

@Composable
fun AnimatedConnectButton(
    isConnected: Boolean,
    statusColor: Color,
    buttonGradient: Brush,
    onClick: () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition()
    val pulse by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(
        modifier = Modifier
            .size(180.dp)
            .shadow(
                elevation = if (isConnected) 20.dp else 12.dp,
                shape = CircleShape,
                clip = false,
                spotColor = statusColor.copy(alpha = 0.3f)
            )
            .scale(if (isConnected) pulse else 1f)
    ) {
        FloatingActionButton(
            onClick = onClick,
            shape = CircleShape,
            containerColor = Color.Transparent,
            elevation = FloatingActionButtonDefaults.elevation(0.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = buttonGradient,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (isConnected) Icons.Default.Lock else Icons.Default.Favorite,
                    contentDescription = if (isConnected) "Disconnect" else "Connect",
                    tint = Color.White,
                    modifier = Modifier.size(64.dp)
                )
            }
        }
    }
}

@Composable
fun ServerSelectionCard(
    selectedServer: String,
    selectedFlag: String,
    onServerClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onServerClick() },
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = PastelSurface),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = selectedFlag,
                    fontSize = 32.sp
                )
                Column {
                    Text(
                        text = selectedServer,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = PastelTextPrimary
                    )
                    Text(
                        text = "Best latency • 23 ms",
                        style = MaterialTheme.typography.bodySmall,
                        color = PastelTextSecondary
                    )
                }
            }
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "Change server",
                tint = PastelTextSecondary
            )
        }
    }
}

@Composable
fun StatisticsCard() {
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
                text = "Today's Usage",
                style = MaterialTheme.typography.titleSmall,
                color = PastelTextSecondary
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                StatItem(
                    icon = Icons.Default.Add,
                    value = "128 MB",
                    label = "Download"
                )
                StatItem(
                    icon = Icons.Default.ThumbUp,
                    value = "45 MB",
                    label = "Upload"
                )
                StatItem(
                    icon = Icons.Default.Build,
                    value = "1h 23m",
                    label = "Session"
                )
            }
        }
    }
}

@Composable
fun StatItem(icon: ImageVector, value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = PastelPrimary,
            modifier = Modifier.size(28.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
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

@Composable
fun FeaturesRow() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        FeatureChip(
            icon = Icons.Default.Lock,
            text = "Encrypted"
        )
        FeatureChip(
            icon = Icons.Default.PlayArrow,
            text = "Fast"
        )
        FeatureChip(
            icon = Icons.Default.ShoppingCart,
            text = "Unlimited"
        )
    }
}

@Composable
fun FeatureChip(icon: ImageVector, text: String) {
    Surface(
        shape = RoundedCornerShape(32.dp),
        color = PastelSurface,
        shadowElevation = 2.dp
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = text,
                tint = PastelPrimary,
                modifier = Modifier.size(18.dp)
            )
            Text(
                text = text,
                style = MaterialTheme.typography.labelMedium,
                color = PastelTextPrimary
            )
        }
    }
}