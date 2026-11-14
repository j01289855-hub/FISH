package com.fischmacro

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.projection.MediaProjection
import android.media.projection.MediaProjectionManager
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat

class FishingMacroService : Service() {

    companion object {
        private const val CHANNEL_ID = "fisch_macro_channel"
        private const val NOTIFICATION_ID = 101
    }

    private var projectionManager: MediaProjectionManager? = null
    private var mediaProjection: MediaProjection? = null
    private var screenCaptureManager: ScreenCaptureManager? = null

    override fun onCreate() {
        super.onCreate()
        projectionManager = getSystemService(Context.MEDIA_PROJECTION_SERVICE) as? MediaProjectionManager
        createNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val captureData = intent?.getParcelableExtra<Intent>("projection_data")

        val notification: Notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Fisch Macro")
            .setContentText("Servicio activo")
            .setSmallIcon(android.R.drawable.stat_sys_download)
            .build()

        startForeground(NOTIFICATION_ID, notification)

        captureData?.let {
            mediaProjection = projectionManager?.getMediaProjection(Activity.RESULT_OK, it)
            screenCaptureManager = ScreenCaptureManager(this)
            screenCaptureManager?.startCapture(Activity.RESULT_OK, it)
        }

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        screenCaptureManager?.stopCapture()
        mediaProjection?.stop()
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Fisch Macro",
                NotificationManager.IMPORTANCE_LOW
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }
}
