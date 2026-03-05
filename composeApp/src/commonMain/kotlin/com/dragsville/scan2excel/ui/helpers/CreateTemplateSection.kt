package com.dragsville.scan2excel.ui.helpers

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dragsville.scan2excel.ui.viewmodel.ScanViewModel

@Composable
fun CreateTemplateSection(viewModel: ScanViewModel) {
    val fields by viewModel.templateFields.collectAsState()
    val name by viewModel.templateName.collectAsState()

    Surface (
        modifier = Modifier
            .fillMaxSize() // Make it take over the screen
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        color = colorScheme.surface, // This blocks out the HomeScreen underneath
        tonalElevation = 8.dp,
        shadowElevation = 8.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            TextField(
                value = name,
                onValueChange = { viewModel.updateTemplateName(it) },
                label = { Text("Template Name (e.g. Name, D.O.B)") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            fields.forEachIndexed { index, fieldText ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    TextField(
                        value = fieldText,
                        onValueChange = { viewModel.updateField(index, it) },
                        label = { Text("Field ${index + 1}") },
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(onClick = { viewModel.removeField(index) }) {
                        Icon(Icons.Default.Delete, contentDescription = "Remove")
                    }
                }
            }

            Button(
                onClick = { viewModel.addFieldInput() },
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = null)
                Text("Add Field")
            }

            Button(
                onClick = { viewModel.saveNewTemplate() },
                modifier = Modifier.fillMaxWidth(),
                enabled = name.isNotBlank()
            ) {
                Text("Save Template")
            }
        }
    }
}
