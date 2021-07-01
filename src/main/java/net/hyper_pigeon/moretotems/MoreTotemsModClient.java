package net.hyper_pigeon.moretotems;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.BeeEntityRenderer;
import net.minecraft.client.render.entity.ZombieEntityRenderer;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.passive.BeeEntity;


public class MoreTotemsModClient implements ClientModInitializer{


    @Override
    public void onInitializeClient() {

        EntityRendererRegistry.INSTANCE.register(MoreTotemsMod.SUMMONED_BEE_ENTITY,
                BeeEntityRenderer::new);

        EntityRendererRegistry.INSTANCE.register(MoreTotemsMod.SUMMONED_ZOMBIE_ENTITY,
                ZombieEntityRenderer::new);


    }
}
