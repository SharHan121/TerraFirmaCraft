package net.dries007.tfc.mixin.client.world;

import net.minecraft.client.world.ClientWorld;

import net.dries007.tfc.world.TFCChunkGenerator;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientWorld.ClientWorldInfo.class)
public class ClientWorldInfoMixin
{
    @Shadow @Final private boolean isFlat;

    /**
     * Override horizon height (where the fog color changes from sky to black/dark, as in vanilla it's hardcoded to the sea level
     * todo: some way to only enable this modification for TFC worlds
     */
    @Inject(method = "getHorizonHeight", at = @At("HEAD"), cancellable = true)
    private void inject$getHorizonHeight(CallbackInfoReturnable<Double> cir)
    {
        cir.setReturnValue(this.isFlat ? 0 : (double) TFCChunkGenerator.SEA_LEVEL);
    }
}
