package com.sergeysav.idleshutdown.config

/**
 * This represents the data stored in the config file.
 *
 * @author sergeys
 *
 * @property delayMilliseconds the delay after the last player leaves before
 * the server will be stopped (in milliseconds). Default value is 7 days.
 */
data class ConfigData(
    val delayMilliseconds: Long = 7 * 24 * 60 * 60 * 1000 // 7 day
) {
    init { // Check that the data falls within bounds
        if (delayMilliseconds < 0) throw ConfigException("delayMilliseconds must not be negative")
    }
}
