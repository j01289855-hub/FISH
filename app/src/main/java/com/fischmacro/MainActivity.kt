package com.fischmacro

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.fischmacro.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val projectionLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            if (data != null) {
                val serviceIntent = Intent(this, FishingMacroService::class.java).apply {
                    putExtra("projection_data", data)
                }
                ContextCompat.startForegroundService(this, serviceIntent)
                binding.statusText.text = getString(R.string.status_running)
            }
        } else {
            binding.statusText.text = "Permiso de captura denegado"
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startButton.setOnClickListener {
            requestScreenCapturePermission()
        }

        binding.stopButton.setOnClickListener {
            stopService(Intent(this, FishingMacroService::class.java))
            binding.statusText.text = getString(R.string.status_idle)
        }

        binding.captureButton.setOnClickListener {
            requestScreenCapturePermission()
        }
    }

    private fun requestScreenCapturePermission() {
        val mediaProjectionManager = getSystemService(Context.MEDIA_PROJECTION_SERVICE)
            as android.media.MediaProjectionManager

        val captureIntent = mediaProjectionManager.createScreenCaptureIntent()
        projectionLauncher.launch(captureIntent)
    }
}
