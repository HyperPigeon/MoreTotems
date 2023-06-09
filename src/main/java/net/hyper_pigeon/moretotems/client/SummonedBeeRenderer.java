package net.hyper_pigeon.moretotems.client;

import net.hyper_pigeon.moretotems.entity.SummonedBeeEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.BeeEntityModel;
import net.minecraft.util.Identifier;

public class SummonedBeeRenderer extends MobEntityRenderer<SummonedBeeEntity, BeeEntityModel<SummonedBeeEntity>> {
    public SummonedBeeRenderer(EntityRendererFactory.Context context, BeeEntityModel<SummonedBeeEntity> entityModel, float f) {
        super(context, entityModel, f);
    }
//    public SummonedBeeRenderer(EntityRenderDispatcher renderManager) {
//        super(renderManager, new BeeEntityModel<>(), 1);
//    }

    @Override
    public Identifier getTexture(SummonedBeeEntity entity) {

        if(!entity.hasStung()) {
            return new Identifier("textures/entity/bee/bee_angry.png");
        }
        else if(entity.hasStung()){
            return new Identifier("textures/entity/bee/bee.png");
        }

        return new Identifier("textures/entity/bee/bee.png");
    }
}
