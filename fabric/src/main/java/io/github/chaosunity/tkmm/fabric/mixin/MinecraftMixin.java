package io.github.chaosunity.tkmm.fabric.mixin;

import io.github.chaosunity.tkmm.fabric.TKMManagerFabric;
import net.minecraft.client.Minecraft;
import net.minecraft.client.main.GameConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MinecraftMixin {
    @Inject(method = "<init>(Lnet/minecraft/client/main/GameConfig;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;setScreen(Lnet/minecraft/client/gui/screens/Screen;)V"))
    private void ctor(GameConfig gameConfig, CallbackInfo ci) {
        TKMManagerFabric.INSTANCE.onMinecraftLoaded();
    }
}
