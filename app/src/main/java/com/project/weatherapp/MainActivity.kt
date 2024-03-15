package com.project.weatherapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.project.weatherapp.screen.MainWeatherPage
import com.project.weatherapp.ui.theme.WeatherAppTheme
import com.project.weatherapp.ui.theme.mainBackground
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                Color.TRANSPARENT,
                Color.TRANSPARENT,
            ),
            navigationBarStyle = SystemBarStyle.light(
                Color.TRANSPARENT,
                Color.TRANSPARENT,
            )
        )
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = mainBackground
                ) {
                    var location by remember { mutableStateOf(Pair(0.0, 0.0)) }
                    var permissionResult by remember { mutableStateOf(false) }
                    RequestLocationPermission(
                        onPermissionGranted = {

                            permissionResult = true

                            getCurrentLocation(
                                onGetCurrentLocationSuccess = {
                                    location = it
                                },
                                onGetCurrentLocationFailed = {
                                    permissionResult = true
                                },
                            )
                        },
                        onPermissionDenied = {
                            permissionResult = true
                            toastMaker("Permission Denied")
                        },
                        onPermissionsRevoked = {
                            permissionResult = true
                        }
                    )

                    MainWeatherPage(
                        location = location,
                        permissionResult = permissionResult
                    )

                }
            }
        }
    }

    @OptIn(ExperimentalPermissionsApi::class)
    @Composable
    fun RequestLocationPermission(
        onPermissionGranted: () -> Unit,
        onPermissionDenied: () -> Unit,
        onPermissionsRevoked: () -> Unit
    ) {
        val permissionState = rememberMultiplePermissionsState(
            listOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
            )
        )

        LaunchedEffect(
            key1 = permissionState.permissions.size,
            key2 = permissionState.revokedPermissions.size,
        ) {
            Log.d("Permission", "Permission State: ${permissionState.permissions}")
            val allPermissionsRevoked =
                permissionState.permissions.size == permissionState.revokedPermissions.size

            val permissionsToRequest = permissionState.permissions.filter {
                !it.status.isGranted
            }

            if (permissionsToRequest.isNotEmpty()) permissionState.launchMultiplePermissionRequest()

            if (allPermissionsRevoked) {
                onPermissionsRevoked()
            } else {
                if (permissionState.allPermissionsGranted) {
                    onPermissionGranted()
                } else {
                    onPermissionDenied()
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocation(
        onGetCurrentLocationSuccess: (Pair<Double, Double>) -> Unit,
        onGetCurrentLocationFailed: (Exception) -> Unit,
        priority: Boolean = true
    ) {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        val accuracy = if (priority) Priority.PRIORITY_HIGH_ACCURACY
        else Priority.PRIORITY_BALANCED_POWER_ACCURACY

        if (areLocationPermissionsGranted()) {
            fusedLocationProviderClient.getCurrentLocation(
                accuracy, CancellationTokenSource().token,
            ).addOnSuccessListener { location ->
                location?.let {
                    onGetCurrentLocationSuccess(Pair(it.latitude, it.longitude))
                }
            }.addOnFailureListener { exception ->
                onGetCurrentLocationFailed(exception)
            }
        }
    }

    private fun areLocationPermissionsGranted(): Boolean {
        return (ActivityCompat.checkSelfPermission(
            this, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    this, Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED)
    }

    private fun toastMaker(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}


