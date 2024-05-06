package com.stlvrtx.mawatchface.presentation

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.util.Log
import android.view.SurfaceHolder
import androidx.core.graphics.withRotation
import androidx.wear.watchface.ComplicationSlotsManager
import androidx.wear.watchface.Renderer
import androidx.wear.watchface.WatchState
import androidx.wear.watchface.style.CurrentUserStyleRepository
import com.stlvrtx.mawatchface.R
import java.time.Duration
import java.time.ZonedDateTime

private const val FRAME_PERIOD_MS_DEFAULT: Long = 1000 / 8L // 8 fps

class MAWatchCanvasRenderer(
    private val context: Context,
    surfaceHolder: SurfaceHolder,
    watchState: WatchState,
    private val complicationSlotsManager: ComplicationSlotsManager,
    currentUserStyleRepository: CurrentUserStyleRepository,
    canvasType: Int
) : Renderer.CanvasRenderer2<MAWatchCanvasRenderer.MASharedAssets>(
    surfaceHolder,
    currentUserStyleRepository,
    watchState,
    canvasType,
    FRAME_PERIOD_MS_DEFAULT,
    clearWithBackgroundTintBeforeRenderingHighlightLayer = false,
) {
    companion object {
        private const val TAG = "MAWatchCanvasRenderer"
        private const val DATE_RING_COUNT = 31
        private const val CIRCLE_DEGREES = 360.0f
    }

    class MASharedAssets(context: Context) : SharedAssets {
        var dateMarker: Bitmap =
            BitmapFactory.decodeResource(context.resources, R.drawable.date_marker);
        var outerRing: Bitmap =
            BitmapFactory.decodeResource(context.resources, R.drawable.outer_ring);
        var moonPhase: Bitmap =
            BitmapFactory.decodeResource(context.resources, R.drawable.moon_phase);
        var innerRing: Bitmap =
            BitmapFactory.decodeResource(context.resources, R.drawable.inner_ring)
        var hourPip: Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.hour_pip)
        var minutePip: Bitmap =
            BitmapFactory.decodeResource(context.resources, R.drawable.minute_pip)
        var secondPip: Bitmap =
            BitmapFactory.decodeResource(context.resources, R.drawable.second_pip)

        override fun onDestroy() {
        }
    }

    override suspend fun createSharedAssets(): MASharedAssets {
        return MASharedAssets(context)
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy()")
        super.onDestroy()
    }

    // Draws the watch face
    override fun render(
        canvas: Canvas,
        bounds: Rect,
        zonedDateTime: ZonedDateTime,
        sharedAssets: MASharedAssets
    ) {
        canvas.drawColor(Color.BLACK)

        canvas.drawBitmap(sharedAssets.outerRing, null, bounds, null)

        drawDateMarker(canvas, bounds, zonedDateTime, sharedAssets)

        // TODO: implement drawMoonPhase method
        canvas.drawBitmap(sharedAssets.moonPhase, null, bounds, null)

        canvas.drawBitmap(sharedAssets.innerRing, null, bounds, null)

        drawHourPip(canvas, bounds, zonedDateTime, sharedAssets)
        drawMinutePip(canvas, bounds, zonedDateTime, sharedAssets)
        drawSecondPip(canvas, bounds, zonedDateTime, sharedAssets)
    }

    private fun drawDateMarker(
        canvas: Canvas,
        bounds: Rect,
        zonedDateTime: ZonedDateTime,
        sharedAssets: MASharedAssets
    ) {
        var currentDayOfMonth = zonedDateTime.dayOfMonth
        var dateMarkerRotation = currentDayOfMonth * CIRCLE_DEGREES / DATE_RING_COUNT

        canvas.withRotation(dateMarkerRotation, bounds.exactCenterX(), bounds.exactCenterY()) {
            save()
            drawBitmap(sharedAssets.dateMarker, null, bounds, null)
            restore()
        }
    }

    private fun drawHourPip(
        canvas: Canvas,
        bounds: Rect,
        zonedDateTime: ZonedDateTime,
        sharedAssets: MASharedAssets
    ) {
        val twelveHoursDurationInSeconds = Duration.ofHours(12).seconds
        val currentSecondOfDay = zonedDateTime.toLocalTime().toSecondOfDay()
        val hourPipRotation = currentSecondOfDay * CIRCLE_DEGREES / twelveHoursDurationInSeconds

        canvas.withRotation(hourPipRotation, bounds.exactCenterX(), bounds.exactCenterY()) {
            save()
            drawBitmap(sharedAssets.hourPip, null, bounds, null)
            restore()
        }
    }

    private fun drawMinutePip(
        canvas: Canvas,
        bounds: Rect,
        zonedDateTime: ZonedDateTime,
        sharedAssets: MASharedAssets
    ) {
        val hourDurationInSeconds = Duration.ofHours(1).seconds
        val currentMinuteInSeconds = zonedDateTime.toLocalTime().minute * 60
        val currentSecond = zonedDateTime.toLocalTime().second
        val currentSecondInHour = currentMinuteInSeconds + currentSecond
        val minutePipRotation = currentSecondInHour * CIRCLE_DEGREES / hourDurationInSeconds

        canvas.withRotation(minutePipRotation, bounds.exactCenterX(), bounds.exactCenterY()) {
            save()
            drawBitmap(sharedAssets.minutePip, null, bounds, null)
            restore()
        }
    }

    private fun drawSecondPip(
        canvas: Canvas,
        bounds: Rect,
        zonedDateTime: ZonedDateTime,
        sharedAssets: MASharedAssets
    ) {
        val minuteDurationInSeconds = Duration.ofMinutes(1).seconds
        val currentSecond = zonedDateTime.toLocalTime().second
        val currentNanosecond = zonedDateTime.toLocalTime().nano / 1000000000f
        val currentPartialSecond = currentSecond + currentNanosecond
        val secondPipRotation = currentPartialSecond * CIRCLE_DEGREES / minuteDurationInSeconds

        canvas.withRotation(secondPipRotation, bounds.exactCenterX(), bounds.exactCenterY()) {
            save()
            drawBitmap(sharedAssets.secondPip, null, bounds, null)
            restore()
        }
    }

    override fun renderHighlightLayer(
        canvas: Canvas,
        bounds: Rect,
        zonedDateTime: ZonedDateTime,
        sharedAssets: MASharedAssets
    ) {
        for ((_, complication) in complicationSlotsManager.complicationSlots) {
            if (complication.enabled) {
                complication.renderHighlightLayer(canvas, zonedDateTime, renderParameters)
            }
        }
    }
}