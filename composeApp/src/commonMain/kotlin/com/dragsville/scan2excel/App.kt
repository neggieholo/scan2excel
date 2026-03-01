package com.dragsville.scan2excel

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import cafe.adriel.voyager.navigator.Navigator
import com.dragsville.scan2excel.ui.screen.LoginViewScreen
import com.dragsville.scan2excel.ui.theme.ScanToExcelTheme

@Composable
@Preview
fun App() {
    ScanToExcelTheme {
        // 1. Wrap the Navigator in a Surface or Box to apply the padding
        Surface(
            modifier =  Modifier
                .fillMaxSize()
                .safeDrawingPadding(),
            color = MaterialTheme.colorScheme.background
        ) {
            Navigator(LoginViewScreen())
        }
    }
}

