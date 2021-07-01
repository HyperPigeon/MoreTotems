package net.hyper_pigeon.moretotems;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.world.World;

/*A now defunct class being kept for possible future updates*/

public class HomingArrowEntity extends ArrowEntity {

    private Entity target;

    public HomingArrowEntity(EntityType<? extends ArrowEntity> entityType, World world, Entity target) {
        super(entityType, world);

    }


//    public void tick(){
//
//        super.tick();
//
//        Box box = this.getBoundingBox().expand(10D);
//
//         TargetPredicate arrow_target_conditions = new TargetPredicate();
//
//        arrow_target_conditions.setBaseMaxDistance(10D);
//
//        /*
//        Iterator var8 = BlockPos.iterate(MathHelper.floor(box.x1), MathHelper.floor(box.y1), MathHelper.floor(box.z1), MathHelper.floor(box.x2), MathHelper.floor(box.y2), MathHelper.floor(box.z2)).iterator();
//
//        BlockPos blockPos;
//        Block block;
//        Entity target;
//
//        blockPos = (BlockPos)var8.next();
//        BlockState blockState = this.world.getBlockState(blockPos);
//        block = blockState.getBlock();*/
//
//        //float the_distance = this.getOwner().distanceTo(this.getOwner().world.getClosestEntity(LivingEntity.class, arrow_target_conditions, (LivingEntity) this.getOwner(), this.getOwner().getX(), this.getOwner().getY(), this.getOwner().getZ(), box));
//        target = this.getOwner().world.getClosestEntity(LivingEntity.class, arrow_target_conditions, (LivingEntity) this.getOwner(), this.getOwner().getX(), this.getOwner().getY(), this.getOwner().getZ(), box);
//
//    }



}
