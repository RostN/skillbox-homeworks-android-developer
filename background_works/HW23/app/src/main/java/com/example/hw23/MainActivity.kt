package com.example.hw23

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.hw23.databinding.ActivityMainBinding
import com.google.android.gms.location.*
import kotlinx.coroutines.launch
import java.util.*

class MainActivity : AppCompatActivity() {
    var lat: Float = 0.0F
    var long: Float = 0.0F
    var sunRise: String = ""
    val formatter = SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
    var alarmTime = 0L

    lateinit var binding: ActivityMainBinding
    private lateinit var fusedClient: FusedLocationProviderClient
    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult) {
            super.onLocationResult(p0)
            p0.lastLocation.let { location ->
                lat = location?.latitude?.toFloat() ?: 0.0F
                long = location?.longitude?.toFloat() ?: 0.0F
            }
            binding.coordinates.text = lat.toString() + " " + long.toString()
            lifecycleScope.launch {
                sunRise = retrofit.loadList(lat, long).results.sunrise
                sunRise = "0" + sunRise.subSequence(0, 7) as String
                binding.sunRiseTime.text = sunRise
                when (sunRise) {
                    "XX:XX:XX" -> binding.btn.isEnabled = false
                    else -> binding.btn.isEnabled = true
                }
                calculateTime()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fusedClient = LocationServices.getFusedLocationProviderClient(this)
        binding.btn.isEnabled = false
        binding.btn.setOnClickListener {
            var enterHour = binding.enterTimeHour.text.toString()
            var enterMinutes = binding.enterTimeMinutes.text.toString()
            if (enterMinutes == "") enterMinutes = "0"
            if (enterHour == "") enterHour = "0"
            alarmTime += (enterHour.toInt() * 60 * 60 * 1000) + (enterMinutes.toInt() * 60 * 1000)
            binding.btn.isEnabled = false
            val exitTime = formatter.format(alarmTime)
            val textAlarm = getString(R.string.text_alarm)
            Toast.makeText(this, "$textAlarm $exitTime", Toast.LENGTH_LONG).show()
            createBackgroundAlarm()
        }
    }

    private fun createBackgroundAlarm() {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val alarmType = AlarmManager.RTC_WAKEUP
        val intent = AlarmReceiver.createIntent(this, "Background exact idle alarm")
        val pendingIntent = PendingIntent.getBroadcast(this, 1, intent, FLAG_IMMUTABLE)
        alarmManager.setExactAndAllowWhileIdle(
            alarmType,
            alarmTime,
            pendingIntent
        )
    }

    private fun calculateTime() {
        val day = Date().date
        val month = Date().month + 1
        val year = Date().year + 1900
        val todayRise = "${day}-${month}-${year} ${sunRise}"
        val todayRiseInMillis = formatter.parse(todayRise).time
        val nowDateInMillis = System.currentTimeMillis()
        if (todayRiseInMillis > nowDateInMillis) {
            alarmTime = todayRiseInMillis
        } else {
            alarmTime = todayRiseInMillis + (24 * 60 * 60 * 1000)
        }
    }

    @SuppressLint("MissingPermission")
    private fun startLocation() {
        val request = LocationRequest.create()
            .setInterval(1000)
            .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
            .setNumUpdates(1)
        fusedClient.requestLocationUpdates(
            request,
            locationCallback,
            Looper.getMainLooper()
        )
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

    private val launcher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { map ->
            if (map.values.isNotEmpty() && map.values.all { it }) {
                Toast.makeText(this, "Permissions is granted", Toast.LENGTH_LONG).show()
            }
        }

    companion object {
        private val REQUEST_PERMISSION: Array<String> =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.POST_NOTIFICATIONS,
                        Manifest.permission.SCHEDULE_EXACT_ALARM
                    )
                } else {
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.SCHEDULE_EXACT_ALARM
                    )
                }
            } else {
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            }
    }

    override fun onStart() {
        super.onStart()
        checkPermissions()
    }

    override fun onStop() {
        super.onStop()
        fusedClient.removeLocationUpdates(locationCallback)
    }
}