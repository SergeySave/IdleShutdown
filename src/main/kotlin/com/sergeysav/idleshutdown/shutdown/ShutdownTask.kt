package com.sergeysav.idleshutdown.shutdown

import com.sergeysav.idleshutdown.IdleShutdown
import net.minecraft.server.dedicated.MinecraftDedicatedServer
import java.util.TimerTask

/**
 * This class represents a task to shutdown the server.
 * It should be given to a timer to execute at some point in the future.
 *
 * @author sergeys
 *
 * @param server the server which will be stopped when this task is run
 */
class ShutdownTask(private val server: MinecraftDedicatedServer) : TimerTask() {
    override fun run() {
        IdleShutdown.log.info("Server has been idle too long: shutting down")
        server.stop(false)
    }
}