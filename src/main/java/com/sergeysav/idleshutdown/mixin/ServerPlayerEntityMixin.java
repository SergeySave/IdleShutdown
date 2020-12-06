package com.sergeysav.idleshutdown.mixin;

import com.sergeysav.idleshutdown.IdleShutdown;
import com.sergeysav.idleshutdown.IdleShutdownServer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * This mixin attaches to the ServerPlayerEntity and is responsible
 * for keeping the mod up to date on the number of players in the
 * server. It does this by notifying the mod of the player count
 * whenever a player connects or disconnects from the server.
 *
 * @author sergeys
 */
@Mixin(ServerPlayerEntity.class)
abstract class ServerPlayerEntityMixin {

    /**
     * This shadow allows access to ServerPlayerEntity's server field.
     */
    @Shadow
    @Final
    private MinecraftServer server;

    /**
     * This method is injected at the bottom of ServerPlayerEntity's constructor.
     * This then notifies us that a player has joined the server.
     *
     * @param info the callback info for the constructor
     */
    @Inject(method = "<init>(Lnet/minecraft/server/MinecraftServer;Lnet/minecraft/server/world/ServerWorld;Lcom/mojang/authlib/GameProfile;Lnet/minecraft/server/network/ServerPlayerInteractionManager;)V", at = @At("RETURN"))
    public void onConstructor(CallbackInfo info) {
        MinecraftServer server = this.server;
        if (server == null) {
            IdleShutdown.INSTANCE.getLog().warn("ServerPlayerEntity missing server.");
        } else {
            // getCurrentPlayerCount() will update AFTER this runs, so we need to pre-increment here
            IdleShutdownServer.INSTANCE.getShutdownController().notifyPlayerCountChange(server.getCurrentPlayerCount() + 1);
        }
    }

    /**
     * This method is injected at the bottom of ServerPlayerEntity's onDisconnect method.
     * This then notifies us that a player has left the server.
     *
     * @param info the callback info for the onDisconnect method
     */
    @Inject(method = "onDisconnect()V", at = @At("RETURN"))
    public void onDisconnect(CallbackInfo info) {
        MinecraftServer server = this.server;
        if (server == null) {
            IdleShutdown.INSTANCE.getLog().warn("ServerPlayerEntity missing server.");
        } else {
            // getCurrentPlayerCount() will update AFTER this runs, so we need to pre-decrement here
            IdleShutdownServer.INSTANCE.getShutdownController().notifyPlayerCountChange(server.getCurrentPlayerCount() - 1);
        }
    }
}














