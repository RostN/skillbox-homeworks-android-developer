package com.example.hw17

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.hw17.databinding.ActivityMapsBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    private lateinit var fusedClient: FusedLocationProviderClient
    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult) {
            super.onLocationResult(p0)
        }
    }
    private val launcher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { map ->
            if (map.values.isNotEmpty() && map.values.all { it }) {
                Toast.makeText(this, "Permissions is granted", Toast.LENGTH_LONG).show()
            }
        }

    @SuppressLint("MissingPermission")
    private fun startLocation() {

        val request = LocationRequest.create()
            .setInterval(1000)
            .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
        fusedClient.requestLocationUpdates(
            request,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fusedClient = LocationServices.getFusedLocationProviderClient(this)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        checkPermissions()
        mMap.isMyLocationEnabled = true
        with(googleMap.uiSettings) {
            isMyLocationButtonEnabled = true
            this.isZoomControlsEnabled = true
        }
        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)

        //Достопремечательности Красноярского края

        val showPlace0 = LatLng(56.010563, 92.852572)

        val showPlace1 = LatLng(55.83492042, 91.0087944)
        mMap.addMarker(MarkerOptions().position(showPlace1).title("Церковь богоявления"))
        val showPlace2 = LatLng(58.4529124, 92.16935795)
        mMap.addMarker(MarkerOptions().position(showPlace2).title("Захарьевская надвратная церковь"))
        val showPlace3 = LatLng(56.011208, 92.881758)
        mMap.addMarker(MarkerOptions().position(showPlace3).title("Усадьба Гадалова"))
        val showPlace4 = LatLng(55.8034349, 95.2868754)
        mMap.addMarker(MarkerOptions().position(showPlace4).title("Церковь Ильи Пророка"))
        val showPlace5 = LatLng(55.913602, 92.735229)
        mMap.addMarker(MarkerOptions().position(showPlace5).title("Красноярский заповедник Столбы"))

        mMap.moveCamera(CameraUpdateFactory.newLatLng(showPlace0))
    }

    override fun onStart() {
        super.onStart()
        checkPermissions()
    }

    override fun onStop() {
        super.onStop()
        fusedClient.removeLocationUpdates(locationCallback)
    }

    private fun checkPermissions() {
        if (REQUEST_PERMISSION.all { permission ->
                ContextCompat
                    .checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
            }
        ) {
            startLocation()
        } else {
            launcher.launch(REQUEST_PERMISSION)
        }
    }

    companion object {
        private val REQUEST_PERMISSION: Array<String> = arrayOf(
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        )
    }
}