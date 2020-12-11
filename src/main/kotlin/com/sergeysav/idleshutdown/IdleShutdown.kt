package com.sergeysav.idleshutdown

import com.sergeysav.idleshutdown.config.ConfigController
import com.sergeysav.idleshutdown.shutdown.ShutdownController
import net.fabricmc.api.DedicatedServerModInitializer
import net.fabricmc.api.ModInitializer
import org.apache.logging.log4j.LogManager

/**
 * This is the main entrypoint for IdleShutdown.
 * It is a singleton that gives access to any necessary global data.
 *
 * While the mod is currently configured not to run on the client side,
 * this helps keep server side code from running on the client.
 *
 * @author sergeys
 */
object IdleShutdown : ModInitializer {

    /**
     * IdleShutdown mod logger.
     */
    val log = LogManager.getLogger("IdleShutdown")!!

    override fun onInitialize() {
        ConfigController.load()
        ConfigController.save() // Ensure the config file is up-to-date
        log.info("IdleShutdown Loaded")
    }
}