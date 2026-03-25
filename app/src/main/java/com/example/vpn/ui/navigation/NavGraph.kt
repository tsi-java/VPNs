package com.example.vpn.ui.navigation

import androidx.compose.animation.*
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.vpn.ui.screens.home.HomeScreen
import com.example.vpn.ui.screens.profile.ProfileScreen
import com.example.vpn.ui.screens.settings.SettingsScreen
import com.example.vpn.ui.theme.PastelBackground
import com.example.vpn.ui.theme.PastelPrimary
import com.example.vpn.ui.theme.PastelTextSecondary
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.ui.unit.dp

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Home : Screen("home", "Home", Icons.Default.Home)
    object Profile : Screen("profile", "Profile", Icons.Default.Person)
    object Settings : Screen("settings", "Settings", Icons.Default.Settings)
}

@Composable
fun NavGraph(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = PastelBackground,
                tonalElevation = 0.dp
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                listOf(Screen.Home, Screen.Profile, Screen.Settings).forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = screen.title) },
                        label = { Text(screen.title) },
                        selected = currentRoute == screen.route,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = PastelPrimary,
                            selectedTextColor = PastelPrimary,
                            unselectedIconColor = PastelTextSecondary,
                            unselectedTextColor = PastelTextSecondary
                        )
                    )
                }
            }
        },
        containerColor = PastelBackground
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = modifier.padding(paddingValues)
        ) {
            composable(Screen.Home.route) {
                HomeScreen()
            }
            composable(Screen.Profile.route) {
                ProfileScreen()
            }
            composable(Screen.Settings.route) {
                SettingsScreen()
            }
        }
    }
}