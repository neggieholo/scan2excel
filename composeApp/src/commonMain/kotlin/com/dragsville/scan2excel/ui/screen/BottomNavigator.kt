package com.dragsville.scan2excel.ui.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavigationBar(navController: NavHostController) {

    val items = listOf(
        Screen.Home,
        Screen.History,
        Screen.Accounts,
        Screen.Settings
    )

    val customBlue = Color(0xFF2196F3)

    NavigationBar(
        containerColor = customBlue, // This sets the bar background
        contentColor = Color.White    // This sets the default color for items
    ) {
        val currentRoute =
            navController.currentBackStackEntryAsState().value?.destination?.route

        items.forEach { screen ->
            NavigationBarItem(
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(Screen.Home.route) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    when (screen) {
                        Screen.Home -> Icon(Icons.Default.Home, null)
                        Screen.History -> Icon(Icons.Default.List, null)
                        Screen.Accounts -> Icon(Icons.Default.Person, null)
                        Screen.Settings -> Icon(Icons.Default.Settings, null)
                    }
                },
                label = {
                    Text(screen.route.replaceFirstChar { it.uppercase() })
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White,
                    selectedTextColor = Color.White,
                    unselectedIconColor = Color.White.copy(alpha = 0.6f),
                    unselectedTextColor = Color.White.copy(alpha = 0.6f),
                    indicatorColor = Color.White.copy(alpha = 0.2f)
                )
            )
        }
    }
}
