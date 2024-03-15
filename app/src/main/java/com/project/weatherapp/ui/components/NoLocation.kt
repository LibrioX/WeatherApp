package com.project.weatherapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun NoLocation() {

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .statusBarsPadding()
            .padding(16.dp)
            .clip(
                RoundedCornerShape(16.dp)
            )
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp)
            .height(300.dp),
    ) {
        Text(
            text = "No location found for live location. Please enable location services.",
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )
    }


}