package com.dragsville.scan2excel.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow

class ProfileScreen : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val colorScheme = colorScheme

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Profile",
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontWeight = FontWeight.ExtraBold,
                                letterSpacing = 1.5.sp,
                                shadow = Shadow(
                                    color = Color.Black.copy(alpha = 0.2f),
                                    offset = Offset(2f, 2f),
                                    blurRadius = 4f
                                )
                            ),
                            color = colorScheme.primary
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { navigator.pop() }) {
                            Icon(Icons.Default.ArrowBackIosNew, contentDescription = "Back")
                        }
                    }
                )
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Profile Placeholder
                Surface(
                    modifier = Modifier.size(100.dp),
                    shape = MaterialTheme.shapes.extraLarge,
                    color = colorScheme.primary.copy(alpha = 0.1f)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text("JD", style = MaterialTheme.typography.headlineLarge, color = colorScheme.primary)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                Text("John Doe", style = MaterialTheme.typography.headlineSmall)
                Text("john.doe@example.com", color = Color.Gray)

                Spacer(modifier = Modifier.height(32.dp))

                // Logout Button
                Button(
                    onClick = {
                        // This pops everything and puts LoginViewScreen at the start
                        navigator.parent?.replaceAll(LoginViewScreen())
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red.copy(alpha = 0.8f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.ExitToApp, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Log Out")
                }
            }
        }
    }
}