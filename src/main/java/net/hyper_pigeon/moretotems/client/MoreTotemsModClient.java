package net.hyper_pigeon.moretotems.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.hyper_pigeon.moretotems.MoreTotemsMod;
import net.minecraft.client.render.entity.BeeEntityRenderer;
import net.minecraft.client.render.entity.ZombieEntityRenderer;


public class MoreTotemsModClient implements ClientModInitializer{


    @Override
    public void onInitializeClient() {

        EntityRendererRegistry.register(MoreTotemsMod.SUMMONED_BEE_ENTITY,
                BeeEntityRenderer::new);

        EntityRendererRegistry.register(MoreTotemsMod.SUMMONED_ZOMBIE_ENTITY,
                ZombieEntityRenderer::new);


    }
}
