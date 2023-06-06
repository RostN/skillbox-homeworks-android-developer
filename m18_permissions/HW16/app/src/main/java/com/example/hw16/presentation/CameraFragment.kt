package com.example.hw16.presentation

import android.content.ContentValues
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.hw16.data.App
import com.example.hw16.databinding.FragmentCameraBinding
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executor

private const val FILE_NAME_FORMAT = "yyyy-MM-dd-HH-mm-ss"

class CameraFragment : Fragment() {

    private var imageCapture: ImageCapture? = null
    private lateinit var executor: Executor
    private val name = SimpleDateFormat(FILE_NAME_FORMAT, Locale.US)
        .format(System.currentTimeMillis())
    val viewModel: MainViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val attractionsDao = (activity?.application as App).db.attractionsDao()
                return MainViewModel(attractionsDao) as T
            }
        }
    }

    companion object {
        fun newInstance() = CameraFragment()

        //Список разрешений
        private val REQUEST_PERMISSIONS: Array<String> = buildList {
            add(android.Manifest.permission.CAMERA)
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }.toTypedArray()
    }

    //Лаунчер
    private val launcher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions())
        { map ->
            if (map.values.all { it }) {
                startCamera()
                Toast.makeText(context, "Permissions is granted", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(context, "Permissions is not granted", Toast.LENGTH_SHORT).show()
            }
        }

    private lateinit var binding: FragmentCameraBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCameraBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        executor = ContextCompat.getMainExecutor(requireContext())
        checkPermissions()
        binding.takePhotoButton.setOnClickListener {
            takePhoto()
        }
    }

    //Сделать снимок
    private fun takePhoto() {
        val imageCapture = imageCapture ?: return
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
        }
        val outputOptions = context?.let {
            ImageCapture.OutputFileOptions.Builder(
                it.contentResolver,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues
            ).build()
        }
        if (outputOptions != null) {
            imageCapture.takePicture(
                outputOptions,
                executor,
                object : ImageCapture.OnImageSavedCallback {
                    override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                        Toast.makeText(
                            context,
                            "Saved on ${outputFileResults.savedUri}",
                            Toast.LENGTH_LONG
                        ).show()
                        val uri = (outputFileResults.savedUri).toString()
                        //Добавление в базу
                        viewModel.onAddBtn(name, uri)
                        //Закрытие камеры
                        activity?.finish()
                    }

                    override fun onError(exception: ImageCaptureException) {
                        Toast.makeText(context, "Failed ${exception.message}", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            )
        }
    }

    //Запуск камеры
    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder().build()
            preview.setSurfaceProvider(binding.viewFinder.surfaceProvider)
            imageCapture = ImageCapture.Builder().build()
            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(
                this,
                CameraSelector.DEFAULT_BACK_CAMERA,
                preview,
                imageCapture
            )
        }, executor)
    }

    //Проверка разрешений
    private fun checkPermissions() {
        val isAllGranted = REQUEST_PERMISSIONS.all { permission ->
            ContextCompat.checkSelfPermission(
                requireContext(),
                permission
            ) == PackageManager.PERMISSION_GRANTED
        }
        if (isAllGranted) {
            startCamera()
        } else {
            launcher.launch(REQUEST_PERMISSIONS)
        }
    }
}