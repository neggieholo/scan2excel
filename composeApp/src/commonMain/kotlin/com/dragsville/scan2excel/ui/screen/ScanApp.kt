package com.dragsville.scan2excel.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuite
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldLayout
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.dragsville.scan2excel.ui.ScanViewModelProvider
import com.dragsville.scan2excel.ui.viewmodel.ScanViewModel
import org.jetbrains.compose.resources.painterResource
import scan2excel.composeapp.generated.resources.Res
import scan2excel.composeapp.generated.resources.app_logo

class MainViewScreen : Screen {
    @Composable
    override fun Content() {
        ScanApp()
    }
}

@Composable
fun ScanApp(
    scanViewModel: ScanViewModel = viewModel(factory = ScanViewModelProvider.Factory)
) {
    val homeTab = HomeTab(scanViewModel)
    val historyTab = HistoryTab(scanViewModel)
    val accountsTab = AccountsTab
    val settingsTab = WorkspaceTab
    val tabs = listOf(homeTab, historyTab, accountsTab, settingsTab)

    TabNavigator(homeTab) { tabNavigator ->
        val customBlue = Color(0xFF2196F3)
        val adaptiveInfo = currentWindowAdaptiveInfo()
        val currentLayoutType = NavigationSuiteScaffoldDefaults.calculateFromAdaptiveInfo(adaptiveInfo)

        NavigationSuiteScaffoldLayout(
            navigationSuite = {
                // Pre-calculate item colors in the @Composable navigationSuite block
                val suiteItemColors = NavigationSuiteDefaults.itemColors(
                    navigationBarItemColors = NavigationBarItemDefaults.colors(
                        indicatorColor = Color.Transparent, // Hide the M3 pill
                        selectedIconColor = Color.Black,
                        unselectedIconColor = Color.White
                    ),
                    navigationRailItemColors = NavigationRailItemDefaults.colors(
                        indicatorColor = Color.Transparent,
                        selectedIconColor = Color.Black,
                        unselectedIconColor = Color.White
                    )
                )

                if (currentLayoutType == NavigationSuiteType.NavigationRail) {
                    val shinyGradient = Brush.horizontalGradient(
                        0.0f to Color(0xFF0D47A1), // Very deep blue (Dark edge)
                        0.45f to Color(0xFF64B5F6), // Shiny light blue
                        0.5f to Color(0xFFE3F2FD), // Sharp White glint (The shine)
                        0.55f to Color(0xFF64B5F6), // Back to light blue
                        1.0f to Color(0xFF1565C0)  // Deep blue (Content edge)
                    )

                    NavigationRail(
                        containerColor = Color.Transparent,
                        contentColor = Color.Black,
                        modifier = Modifier
                            .width(120.dp)
                            .shadow(elevation = 15.dp) // CMP Shadow
                            .background(shinyGradient)
                            // 2. Subtle Right Border for depth
                            .drawBehind {
                                // Subtle white "specular" highlight on the very left edge
                                drawLine(
                                    color = Color.White.copy(alpha = 0.5f),
                                    start = Offset(0f, 0f),
                                    end = Offset(0f, size.height),
                                    strokeWidth = 1.5.dp.toPx()
                                )
                                // Dark "occlusion" line on the right edge to separate from content
                                drawLine(
                                    color = Color.Black.copy(alpha = 0.2f),
                                    start = Offset(size.width, 0f),
                                    end = Offset(size.width, size.height),
                                    strokeWidth = 2.dp.toPx()
                                )
                            },
                        header = {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(
                                    painter = painterResource(Res.drawable.app_logo),
                                    contentDescription = null,
                                    modifier = Modifier.size(100.dp),
                                    tint = Color.Unspecified
                                )
                            }
                        }
                    ) {
                        Column(
                            modifier = Modifier.fillMaxHeight().padding(vertical = 40.dp),
                            verticalArrangement = Arrangement.spacedBy(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            tabs.forEach { tab ->
                                val isSelected = tabNavigator.current == tab

                                NavigationRailItem(
                                    selected = isSelected,
                                    onClick = { tabNavigator.current = tab },
                                    icon = {
                                        // THE ICON BUBBLE
                                        Surface(
                                            color = if (isSelected) Color.White else Color.Black,
                                            shape = CircleShape,
                                            modifier = Modifier.size(48.dp).shadow(4.dp, CircleShape)
                                        ) {
                                            Box(contentAlignment = Alignment.Center) {
                                                Icon(
                                                    tab.options.icon!!,
                                                    null,
                                                    modifier = Modifier.size(24.dp),
                                                    tint = if (isSelected) Color.Black else Color.White
                                                )
                                            }
                                        }
                                    },
                                    label = {
                                        Text(
                                            tab.options.title,
                                            style = MaterialTheme.typography.labelLarge.copy(
                                                fontWeight = FontWeight.ExtraBold
                                            ),
                                            // ALWAYS BLACK TEXT
                                            color = Color.Black
                                        )
                                    },
                                    alwaysShowLabel = true,
                                    colors = NavigationRailItemDefaults.colors(
                                        selectedIconColor = Color.Black,
                                        unselectedIconColor = Color.White,
                                        selectedTextColor = Color.Black,
                                        unselectedTextColor = Color.Black,
                                        indicatorColor = Color.Transparent // We used our own Surface instead
                                    )
                                )
                            }
                        }
                    }
                } else {
                    // 1. Define the Shiny Gradient for the Suite (horizontal for bottom bar, vertical for rail)
                    val suiteGradient = Brush.verticalGradient(
                        0.0f to Color(0xFF0D47A1), // Very deep blue (Dark edge)
                        0.45f to Color(0xFF64B5F6), // Shiny light blue
                        0.5f to Color(0xFFE3F2FD), // Sharp White glint (The shine)
                        0.55f to Color(0xFF64B5F6), // Back to light blue
                        1.0f to Color(0xFF1565C0)  // Deep blue (Content edge)
                    )

                    NavigationSuite(
                        layoutType = currentLayoutType,
                        colors = NavigationSuiteDefaults.colors(
                            navigationBarContainerColor = Color.Transparent,
                            navigationRailContainerColor = Color.Transparent
                        ),
                        modifier = Modifier.background(suiteGradient),
                        content = {
                            tabs.forEach { tab ->
                                val isSelected = tabNavigator.current == tab

                                item(
                                    selected = isSelected,
                                    onClick = { tabNavigator.current = tab },
                                    alwaysShowLabel = false,
                                    icon = {
                                        Surface(
                                            color = if (isSelected) Color.White else Color.Black,
                                            shape = CircleShape,
                                            modifier = Modifier.size(42.dp).shadow(2.dp, CircleShape)
                                        ) {
                                            Box(contentAlignment = Alignment.Center) {
                                                Icon(
                                                    tab.options.icon!!,
                                                    null,
                                                    modifier = Modifier.size(20.dp),
                                                    tint = if (isSelected) Color.Black else Color.White
                                                )
                                            }
                                        }
                                    },
                                    label = null,
                                    colors = suiteItemColors
                                )
                            }
                        }
                    )
                }
            },
            layoutType = currentLayoutType
        ) {
            Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                CurrentTab()
            }
        }
    }
}
