package com.sergeysav.idleshutdown.config

/**
 * This represents the data stored in the config file.
 *
 * @author sergeys
 *
 * @property delayMilliseconds the delay after the last player leaves before
 * the server will be stopped (in milliseconds). Default value is 7 days.
 * @property commandResponses the text to used as command responses
 * @property commandAliases aliases to register for the main command
 */
data class ConfigData(
    val delayMilliseconds: Long = 7 * 24 * 60 * 60 * 1000, // 7 day
    val commandResponses: CommandFormatData = CommandFormatData(),
    val commandAliases: List<String> = listOf("idleshutdown", "ishut", "is")
) {
    fun validate() { // Check that the data falls within bounds
        if (delayMilliseconds < 0) throw ConfigException("delayMilliseconds must not be negative")
        commandResponses.validate()
    }
}

/**
 * This represents the command format data stored in the config file.
 *
 * @author sergeys
 *
 * @property scheduledShutdown the response to use when a shutdown is scheduled
 * @property noShutdown the response to use when a shutdown is not scheduled
 */
data class CommandFormatData(
    val scheduledShutdown: String = "Shutdown Scheduled in %s",
    val noShutdown: String = "No Shutdown Scheduled"
) {
    fun validate() {
        if (!scheduledShutdown.contains("%s")) throw ConfigException("commandResponses.scheduledShutdown must contain exactly one %s")
    }
}
