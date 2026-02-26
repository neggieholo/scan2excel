package com.dragsville.scan2excel

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dragsville.scan2excel.ui.screen.LoginScreen
import com.dragsville.scan2excel.ui.screen.ScanApp

@Composable
@Preview
fun App() {
    MaterialTheme {
        RootNavigation()
    }
}

@Composable
fun RootNavigation() {
    val rootNavController = rememberNavController()

    NavHost(navController = rootNavController, startDestination = "login") {
        composable("login") {
            LoginScreen(onLoginSuccess = {
                rootNavController.navigate("main") {
                    popUpTo("login") { inclusive = true }
                }
            })
        }
        composable("main") {
            ScanApp()
        }
    }
}
