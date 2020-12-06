package com.sergeysav.idleshutdown

import com.sergeysav.idleshutdown.shutdown.ShutdownController
import net.fabricmc.api.DedicatedServerModInitializer

/**
 * This is the server side entrypoint for IdleShutdown.
 * It is a singleton that gives access to any necessary global data.
 *
 * @author sergeys
 */
object IdleShutdownServer : DedicatedServerModInitializer {

    /**
     * The shutdown controller
     */
    lateinit var shutdownController: ShutdownController
        private set

    override fun onInitializeServer() {
        shutdownController = ShutdownController()
        IdleShutdown.log.info("IdleShutdown Server Loaded")
    }
}