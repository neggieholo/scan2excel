package com.dragsville.scan2excel.ui.screen

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator

@Composable
fun BottomNavigationBar(
    tabNavigator: TabNavigator,
    tabs: List<Tab>
) {
    val customBlue = Color(0xFF2196F3)

    NavigationBar(
        containerColor = customBlue,
        contentColor = Color.White
    ) {
        tabs.forEach { tab ->
            NavigationBarItem(
                selected = tabNavigator.current == tab,
                onClick = { tabNavigator.current = tab },
                icon = {
                    Icon(
                        painter = tab.options.icon!!,
                        contentDescription = tab.options.title,
                        tint = Color.White
                    )
                },
                label = {
                    Text(tab.options.title, color = Color.White)
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.White.copy(alpha = 0.2f)
                )
            )
        }
    }
}