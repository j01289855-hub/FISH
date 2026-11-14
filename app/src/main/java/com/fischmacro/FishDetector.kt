package com.fischmacro

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.PixelFormat
import android.hardware.display.DisplayManager
import android.hardware.display.VirtualDisplay
import android.media.ImageReader
import android.media.projection.MediaProjection
import android.media.projection.MediaProjectionManager
import android.os.Build
import android.util.Log

class ScreenCaptureManager(
    private val context: Context
) {
    private val projectionManager = context.getSystemService(MediaProjectionManager::class.java)
    private var virtualDisplay: VirtualDisplay? = null
    private var imageReader: ImageReader? = null
    private var mediaProjection: MediaProjection? = null

    fun startCapture(resultCode: Int, data: Intent) {
        mediaProjection = projectionManager?.getMediaProjection(resultCode, data)
        val width = 1080
        val height = 2400
        val density = context.resources.displayMetrics.densityDpi

        imageReader = ImageReader.newInstance(width, height, PixelFormat.RGBA_8888, 2)

        virtualDisplay = mediaProjection?.createVirtualDisplay(
            "FischMacroCapture",
            width,
            height,
            density,
            0,
            imageReader?.surface,
            null,
            null
        )
    }

    fun stopCapture() {
        virtualDisplay?.release()
        imageReader?.close()
        mediaProjection?.stop()
    }

    fun captureFrame(): Bitmap? {
        val image = imageReader?.acquireLatestImage() ?: return null
        val width = image.width
        val height = image.height
        val planes = image.planes
        val buffer = planes[0].buffer
        val pixelStride = planes[0].pixelStride
        val rowStride = planes[0].rowStride
        val rowPadding = rowStride - pixelStride * width
        val bitmap = Bitmap.createBitmap(
            width + rowPadding / pixelStride,
            height,
            Bitmap.Config.ARGB_8888
        )

        buffer.rewind()
        var index = 0
        val pixels = IntArray(width * height)
        for (y in 0 until height) {
            for (x in 0 until width) {
                val pixel = buffer.getInt(index)
                pixels[y * width + x] = pixel
                index += pixelStride
            }
            index += rowPadding
        }
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height)
        image.close()
        return bitmap
    }
}
