package com.project.weatherapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.project.weatherapp.R
import com.project.weatherapp.ui.components.MiniIcon
import com.project.weatherapp.ui.theme.gray
import com.project.weatherapp.util.toFormattedDateString

@Composable
fun MainCardWeather(
    temperature: Double,
    weather: String,
    weatherDescription: String,
    humidity: Int,
    windSpeed: Double,
    pressure: Int,
    icon: String,
    dateTime: Int,
    location: String
) {
    Column(
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
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = Icons.Outlined.LocationOn,
                    contentDescription = "location",
                    tint = gray
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = location,
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            Text(
                text = "${dateTime.toFormattedDateString()}",
                style = MaterialTheme.typography.labelMedium
            )
        }
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                modifier = Modifier
                    .width(140.dp)
                    .height(70.dp),
                contentScale = ContentScale.Crop,
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://openweathermap.org/img/wn/${icon}@4x.png").crossfade(true)
                    .build(),
                contentDescription = "Weather Image"
            )
            Text(
                text = "${temperature}°C",
                style = MaterialTheme.typography.displayLarge
            )
            Text(
                text = weather,
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = weatherDescription,
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        )
        {
            MiniIcon(
                imageVector = ImageVector.vectorResource(id = R.drawable.entypo_air),
                title = "${pressure}hpa",
            )
            MiniIcon(
                imageVector = ImageVector.vectorResource(id = R.drawable.water_outline),
                title = "${humidity}%",
            )
            MiniIcon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ph_wind),
                title = "${windSpeed}km/h",
            )
        }
    }

}