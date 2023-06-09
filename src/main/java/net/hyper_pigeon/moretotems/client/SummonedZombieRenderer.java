package net.hyper_pigeon.moretotems.client;

import net.hyper_pigeon.moretotems.entity.SummonedZombieEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.ZombieEntityModel;
import net.minecraft.util.Identifier;

public class SummonedZombieRenderer extends MobEntityRenderer<SummonedZombieEntity, ZombieEntityModel<SummonedZombieEntity>> {
    public SummonedZombieRenderer(EntityRendererFactory.Context context, ZombieEntityModel<SummonedZombieEntity> entityModel, float f) {
        super(context, entityModel, f);
    }


//    public SummonedZombieRenderer(EntityRenderDispatcher dispatcher) {
//        super(dispatcher, new ZombieEntityModel<>(1, false), 1);
//    }

    @Override
    public Identifier getTexture(SummonedZombieEntity entity) {
        return new Identifier("textures/entity/zombie/zombie.png");
    }


}
