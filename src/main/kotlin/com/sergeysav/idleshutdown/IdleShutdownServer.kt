package com.sergeysav.idleshutdown

import com.sergeysav.idleshutdown.command.IdleShutdownCommand
import com.sergeysav.idleshutdown.shutdown.ShutdownController
import net.fabricmc.api.DedicatedServerModInitializer
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback

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

        CommandRegistrationCallback.EVENT.register(CommandRegistrationCallback { dispatcher, _ ->
            // second parameter is true when running on a dedicated server, always true
            IdleShutdownCommand.register(dispatcher, shutdownController)
        })

        IdleShutdown.log.info("IdleShutdown Server Loaded")
    }
}