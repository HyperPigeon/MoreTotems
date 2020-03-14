package net.hyper_pigeon.moretotems;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;


public class MoreTotemsModClient implements ClientModInitializer{


    @Override
    public void onInitializeClient() {

        EntityRendererRegistry.INSTANCE.register(MoreTotemsMod.SUMMONED_BEE_ENTITY,
                (dispatcher, context) ->
                new SummonedBeeRenderer(dispatcher));

        EntityRendererRegistry.INSTANCE.register(MoreTotemsMod.SUMMONED_ZOMBIE_ENTITY,
                (dispatcher, context) ->
                        new SummonedZombieRenderer(dispatcher));


    }
}
