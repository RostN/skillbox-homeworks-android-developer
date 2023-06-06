package com.example.hw18

import android.Manifest
import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import com.example.hw18.databinding.FragmentFirstBinding
import com.google.firebase.messaging.FirebaseMessaging
import kotlin.random.Random

class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        FirebaseMessaging.getInstance().token.addOnCompleteListener {
//            Log.d("Registration token", it.result)
//        }

        binding.buttonFirst.setOnClickListener {
//            throw RuntimeException("Test Crash")
            createNotification()
        }

    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun createNotification() {
        val intent = Intent(requireContext(), MainActivity::class.java)
        val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.getActivities(
                requireContext(),
                0,
                arrayOf(intent),
                PendingIntent.FLAG_IMMUTABLE
            )
        } else {
            PendingIntent.getActivities(
                requireContext(),
                0,
                arrayOf(intent),
                PendingIntent.FLAG_UPDATE_CURRENT
            )

        }
        val notification = NotificationCompat.Builder(requireContext(), App.NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.notification_icon)
            .setContentTitle("Notification title")
            .setContentText("Notification text")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        NotificationManagerCompat.from(requireContext()).notify(NOTIFICATION_ID, notification)
    }

    companion object {
        var NOTIFICATION_ID = Random.nextInt()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}