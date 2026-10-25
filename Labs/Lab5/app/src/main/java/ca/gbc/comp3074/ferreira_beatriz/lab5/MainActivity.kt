package ca.gbc.comp3074.ferreira_beatriz.lab5

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import ca.gbc.comp3074.ferreira_beatriz.lab5.ui.theme.Lab5Theme
import java.util.Locale

class MainActivity : ComponentActivity() {
    private lateinit var locationManager: LocationManager
    private var locationText by mutableStateOf("Tap the button to get your GPS location.")
    private var locationFound by mutableStateOf(false)

    private val locationListener = LocationListener { location ->
        locationText = formatLocation(location)
        locationFound = true
    }

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions(),
    ) { permissions ->
        if (permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true) {
            getGpsLocation()
        } else {
            locationText = "Location permission was denied. Please allow precise location to use GPS."
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        setContent {
            Lab5Theme {
                LocationScreen(
                    locationText = locationText,
                    locationFound = locationFound,
                    onGetLocation = ::checkPermissionAndGetLocation,
                    onBack = ::returnToStart,
                )
            }
        }
    }

    override fun onStop() {
        super.onStop()
        locationManager.removeUpdates(locationListener)
    }

    private fun checkPermissionAndGetLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getGpsLocation()
        } else {
            permissionLauncher.launch(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
            )
        }
    }

    private fun getGpsLocation() {
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            locationText = "GPS is turned off. Turn it on in device settings and try again."
            return
        }

        locationText = "Getting GPS location…"
        locationFound = false
        val lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        if (lastLocation != null) {
            locationText = formatLocation(lastLocation)
            locationFound = true
        }

        locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            1_000L,
            1f,
            locationListener,
            Looper.getMainLooper(),
        )
    }

    private fun returnToStart() {
        locationManager.removeUpdates(locationListener)
        locationFound = false
        locationText = "Tap the button to get your GPS location."
    }

    private fun formatLocation(location: Location): String = String.format(
        Locale.US,
        "Latitude: %.6f\nLongitude: %.6f",
        location.latitude,
        location.longitude,
    )
}

@Composable
fun LocationScreen(
    locationText: String,
    locationFound: Boolean,
    onGetLocation: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text("COMP3074 - Lab 5", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(R.drawable.pin),
                contentDescription = "Location pin",
                modifier = Modifier.size(38.dp),
            )
            Spacer(Modifier.width(8.dp))
            Text("GPS Location", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        }
        Spacer(Modifier.height(24.dp))
        if (locationFound) {
            Card(modifier = Modifier.widthIn(max = 360.dp)) {
                Text(locationText, modifier = Modifier.padding(24.dp), style = MaterialTheme.typography.bodyLarge)
            }
        } else {
            Text(locationText, style = MaterialTheme.typography.bodyLarge)
        }
        Spacer(Modifier.height(24.dp))
        if (locationFound) {
            Button(
                onClick = onBack,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00A878)),
            ) {
                Text("Back")
            }
        } else {
            Button(
                onClick = onGetLocation,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00A878)),
            ) {
                Text("Get Current Location")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LocationScreenPreview() {
    Lab5Theme {
        LocationScreen(
            locationText = "Latitude: 43.653226\nLongitude: -79.383184",
            locationFound = true,
            onGetLocation = {},
            onBack = {},
        )
    }
}
