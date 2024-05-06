package com.stlvrtx.mawatchface.presentation.data.watchface

// Defaults for the watch face. All private values aren't editable by the user, so they don't need
// to be exposed as settings defaults.
const val DRAW_HOUR_PIPS_DEFAULT = true

data class WatchFaceData (
    val drawHourPips: Boolean = DRAW_HOUR_PIPS_DEFAULT,
)
