package cn.little1yuan.randomeffects;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.effect.InstantStatusEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Objects;
import java.util.Random;

public class RandomEffects implements ModInitializer {
    public static final Random RANDOM = new Random();
    @Override
    public void onInitialize() {
        System.out.println("Mod init.");
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            if (server.getTicks() % 200 == 0) {
                for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                    StatusEffect effect = Registries.STATUS_EFFECT.get(RANDOM.nextInt(Registries.STATUS_EFFECT.size()));
                    if (effect == null) return;
                    while (effect == null || effect instanceof InstantStatusEffect) {
                        effect = Registries.STATUS_EFFECT.get(RANDOM.nextInt(Registries.STATUS_EFFECT.size()));
                    }
                    player.addStatusEffect(new StatusEffectInstance(effect, 200, 0));
                }
            }
        });
    }
}