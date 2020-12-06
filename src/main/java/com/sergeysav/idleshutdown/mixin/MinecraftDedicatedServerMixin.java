package com.sergeysav.idleshutdown.mixin;

import com.sergeysav.idleshutdown.IdleShutdownServer;
import net.minecraft.server.dedicated.MinecraftDedicatedServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * This mixin attaches to the MinecraftDedicatedServer and is responsible
 * for initializing the shutdown controller based on the running server.
 *
 * @author sergeys
 */
@Mixin(MinecraftDedicatedServer.class)
abstract class MinecraftDedicatedServerMixin {

    /**
     * This method is injected before the return statements of MinecraftDedicatedServer's setupServer method.
     * It will check that the setup was successful and (if it was) start the mod's timer.
     *
     * @param info the callback info containing the return value of the server's setup method
     */
    @SuppressWarnings("ConstantConditions")
    @Inject(method = "setupServer()Z", at = @At("RETURN"))
    public void setupServer(CallbackInfoReturnable<Boolean> info) {
        // Make sure that the server successfully started
        if (info.getReturnValueZ()) {
            IdleShutdownServer.INSTANCE.getShutdownController().notifyServerStart((MinecraftDedicatedServer)(Object)this);
        }
    }
}
