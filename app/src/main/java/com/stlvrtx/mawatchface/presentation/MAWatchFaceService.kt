package com.stlvrtx.mawatchface.presentation

import android.util.Log
import android.view.SurfaceHolder
import androidx.wear.watchface.CanvasType
import androidx.wear.watchface.ComplicationSlotsManager
import androidx.wear.watchface.WatchFace
import androidx.wear.watchface.WatchFaceService
import androidx.wear.watchface.WatchFaceType
import androidx.wear.watchface.WatchState
import androidx.wear.watchface.style.CurrentUserStyleRepository
//import androidx.wear.watchface.style.UserStyleSchema
import com.stlvrtx.mawatchface.presentation.utils.createComplicationSlotManager
import androidx.wear.watchface.style.UserStyleSchema

class MAWatchFaceService : WatchFaceService() {

    /**
     * The specification of settings the watch face supports.
     * This is similar to a database schema.
     */
    override fun createUserStyleSchema(): UserStyleSchema =
        UserStyleSchema(listOf())

    /**
     * The complication slot configuration for the watchface.
     */
    override fun createComplicationSlotsManager(
        currentUserStyleRepository: CurrentUserStyleRepository
    ): ComplicationSlotsManager = createComplicationSlotManager(
        context = applicationContext,
        currentUserStyleRepository = currentUserStyleRepository
    )

    /**
     * The watch face itself, which includes the renderer for drawing.
     */
    override suspend fun createWatchFace(
        surfaceHolder: SurfaceHolder,
        watchState: WatchState,
        complicationSlotsManager: ComplicationSlotsManager,
        currentUserStyleRepository: CurrentUserStyleRepository
    ): WatchFace {
        Log.d(TAG, "createWatchFace()")

        // Creates class that renders the watch face.
        val renderer = MAWatchCanvasRenderer(
            context = applicationContext,
            surfaceHolder = surfaceHolder,
            watchState = watchState,
            complicationSlotsManager = complicationSlotsManager,
            currentUserStyleRepository = currentUserStyleRepository,
            canvasType = CanvasType.HARDWARE
        )

        // Creates the watch face.
        return WatchFace(
            watchFaceType = WatchFaceType.ANALOG,
            renderer = renderer
        )
    }

    companion object {
        const val TAG = "MAWatchFaceService"
    }
}