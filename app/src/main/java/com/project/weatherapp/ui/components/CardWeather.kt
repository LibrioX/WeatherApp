package com.project.weatherapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.project.weatherapp.util.EnumCity
import com.project.weatherapp.util.toFormattedDateString

@Composable
fun CardWeather(
    cityId: Int,
    temperature: Double,
    weather: String,
    weatherDescription: String,
    humidity: Int,
    windSpeed: Double,
    pressure: Int,
    icon: String,
    dateTime: Int
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(
                RoundedCornerShape(16.dp)
            )
            .fillMaxWidth()
            .background(Color.White)
            .padding(12.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Column{
                Text(
                    text = enumValues<EnumCity>(
                    ).first { it.id == cityId }.name,
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = dateTime.toFormattedDateString(),
                    style = MaterialTheme.typography.labelMedium
                )
            }


            AsyncImage(
                modifier = Modifier
                    .width(80.dp)
                    .height(40.dp)
                    .offset(x = 16.dp)
                ,

                contentScale = ContentScale.Crop,
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://openweathermap.org/img/wn/${icon}@4x.png").crossfade(true)
                    .build(),
                contentDescription = "Weather Image"
            )

        }
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "${temperature}°C",
                style = MaterialTheme.typography.displayMedium
            )
            Text(
                text = weather,
                style = MaterialTheme.typography.labelLarge
            )
            Text(
                text = weatherDescription,
                style = MaterialTheme.typography.labelMedium
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
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