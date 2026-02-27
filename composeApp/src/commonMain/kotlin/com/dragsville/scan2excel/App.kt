package com.dragsville.scan2excel

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import cafe.adriel.voyager.navigator.Navigator
import com.dragsville.scan2excel.ui.screen.LoginViewScreen
import com.dragsville.scan2excel.ui.theme.ScanToExcelTheme

@Composable
@Preview
fun App() {
    ScanToExcelTheme {
        Navigator(LoginViewScreen())
    }
}

