package net.hyper_pigeon.moretotems;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.world.World;

public class SummonedSkeletonEntity extends SkeletonEntity {
    public SummonedSkeletonEntity(EntityType<? extends SkeletonEntity> entityType, World world) {
        super(entityType, world);
    }
}
