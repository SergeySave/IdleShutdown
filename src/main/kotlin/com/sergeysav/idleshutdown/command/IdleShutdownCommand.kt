package com.sergeysav.idleshutdown.command

import com.mojang.brigadier.Command
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.context.CommandContext
import com.sergeysav.idleshutdown.config.ConfigController
import com.sergeysav.idleshutdown.shutdown.ShutdownController
import net.minecraft.server.command.CommandManager.literal
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.text.LiteralText
import java.time.Duration

object IdleShutdownCommand {

    private fun CommandContext<ServerCommandSource>.execute(shutdownController: ShutdownController): Int {
        val task = shutdownController.task
        source.sendFeedback(LiteralText(if (task != null) {
            val deltaTime = Duration.ofMillis((task.scheduledExecutionTime() - System.currentTimeMillis()))

            ConfigController.state.commandResponses.scheduledShutdown.format(buildString {
                val days = deltaTime.toDays()
                val hours = deltaTime.toHours() - deltaTime.toDays() * 24
                val minutes = deltaTime.toMinutes() - deltaTime.toHours() * 60
                val seconds = deltaTime.seconds - deltaTime.toMinutes() * 60
                if (days > 0) {
                    append("$days Days ")
                }
                if (hours > 0) {
                    append("$hours Hours ")
                }
                if (minutes > 0) {
                    append("$minutes Minutes ")
                }
                append("$seconds Seconds")
            })
        } else {
            ConfigController.state.commandResponses.noShutdown
        }), false)

        return Command.SINGLE_SUCCESS
    }

    fun register(dispatcher: CommandDispatcher<ServerCommandSource>, shutdownController: ShutdownController) {
        ConfigController.state.commandAliases.forEach { alias ->
            dispatcher.register(
                literal(alias).requires {
                    it.hasPermissionLevel(4)
                }.executes { it.execute(shutdownController) }
            )
        }
    }
}