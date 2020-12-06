package com.sergeysav.idleshutdown.shutdown

import com.sergeysav.idleshutdown.config.ConfigController
import net.minecraft.server.dedicated.MinecraftDedicatedServer
import java.util.Timer

/**
 * This class is in charge of scheduling and executing shutdown tasks depending on the server's player counts
 *
 * @author sergeys
 */
class ShutdownController {

    /**
     * The timer which is in charge of executing the shutdown tasks in the future
     */
    private var timer = Timer("IdleShutdown", true)

    /**
     * The current task. It is kept track of in order to be cancelled and removed from the timer before the next task
     * is created (if needed)
     */
    private var task: ShutdownTask? = null

    /**
     * The dedicated server on which this mod is currently running.
     *
     * Note: as the environment for the mod is 'server', the mod will never run on the client.
     * On the server side, the Dedicated Server is effectively a singleton, as it will only be created once.
     * So this instance allows the mod to shut the server down when the time comes.
     */
    private var server: MinecraftDedicatedServer? = null

    /**
     * Initialize the mod given the server right after it starts up.
     * This will also start the shutdown timer sequence.
     *
     * @param server the server instance which just started up
     */
    fun notifyServerStart(server: MinecraftDedicatedServer) {
        this.server = server
        notifyPlayerCountChange(0)
    }

    /**
     * Notify the mod that the player count just changed.
     * This will cause the mod to either cancel the current timer (if a player joined and the server was empty),
     * start a timer (if the last player left), or do nothing (if the server had players and still does).
     *
     * @param playerCount the number of people currently in the server
     */
    fun notifyPlayerCountChange(playerCount: Int) {
        task?.cancel()
        if (task != null) {
            timer.purge()
        }
        task = null

        if (playerCount == 0) {
            val dedicatedServer = this.server ?: return
            // This isn't the most efficient, but this method should not be called
            // that often
            synchronized(this) {
                val newTask = ShutdownTask(dedicatedServer)
                task = newTask
                timer.schedule(newTask, ConfigController.state.delayMilliseconds)
            }
        }
    }
}