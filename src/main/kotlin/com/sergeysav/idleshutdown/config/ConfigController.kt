package com.sergeysav.idleshutdown.config

import com.google.gson.GsonBuilder
import com.sergeysav.idleshutdown.IdleShutdown
import java.io.File
import java.io.FileNotFoundException
import java.io.FileReader
import java.io.FileWriter

/**
 * This singleton is in charge of the config data: both on disk and in RAM.
 *
 * @author sergeys
 */
object ConfigController {

    /**
     * The current config state
     */
    var state = ConfigData()
        private set

    /**
     * The config folder
     */
    private val configFolder = File("config")
    /**
     * The config file
     */
    private val configFile = File(configFolder, "idleshutdown.json")
    /**
     * A gson instance for use in config serialization and deserialization
     */
    private val gson = GsonBuilder().setPrettyPrinting().create()

    /**
     * Load the config file from disk.
     * This will create a new file if the config file is not found.
     *
     * The config data can then be accessed from [state]
     */
    fun load() {
        try {
            FileReader(configFile).use {
                state = gson.fromJson(it, ConfigData::class.java)
            }
        } catch (ex: FileNotFoundException) {
            IdleShutdown.log.info("Config file not found. Creating default.")
            save()
        }
        IdleShutdown.log.trace("Config data loaded")
    }

    /**
     * Save the config data to disk.
     * This will create a new config folder and a new config file if they do not exist.
     *
     * The config data to save will be retrieved from [state]
     */
    private fun save() {
        if (!configFolder.exists()) configFolder.mkdirs()
        if (!configFile.exists()) configFile.createNewFile()
        FileWriter(configFile, false).use {
            gson.toJson(state, it)
        }
    }
}