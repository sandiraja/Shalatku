package com.example.shalatku

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TasbihScreen(
    count: Int,
    rounds: Int,
    onIncrement: () -> Unit,
    onReset: () -> Unit,
    modifier: Modifier = Modifier) {

    Column(
        modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Tasbih", style = MaterialTheme.typography.headlineMedium)
        Text(text = "Putaran: $rounds")
        Spacer(Modifier.height(16.dp))
        Text("$count", style = MaterialTheme.typography.displayLarge)
        Spacer(Modifier.height(24.dp))
        Button(onClick = onIncrement) { Text("+1") }
        Spacer(Modifier.height(8.dp))
        OutlinedButton(onClick = onReset) { Text("Reset") }
    }


    }
@Preview(showBackground = true)
@Composable
fun TasbihScreenPreview() {
    TasbihScreen(count = 12, rounds = 2, onIncrement = {}, onReset = {})
}
