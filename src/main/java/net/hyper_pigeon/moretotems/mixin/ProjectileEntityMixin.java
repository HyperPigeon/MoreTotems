package net.hyper_pigeon.moretotems.mixin;

import net.hyper_pigeon.moretotems.MoreTotemsMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.Projectile;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ProjectileEntity.class)
public abstract class ProjectileEntityMixin extends Entity implements Projectile {

    //@Shadow public native void setDamage(double damage);
    @Shadow public native Entity getOwner();

    @Shadow private double damage;

    public ProjectileEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }


    @Inject(method = "<init>(Lnet/minecraft/entity/EntityType;Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/world/World;)V", at = @At("RETURN"))
    private void onInit(EntityType type, LivingEntity owner, World world, CallbackInfo callbackInfo) {

        if (owner.hasStatusEffect(MoreTotemsMod.SNIPER)) {

            this.damage = damage * 2;


        }

    }




    /*
    @Inject(at = @At("HEAD"), method = "setDamage")
    public void setSniperDamage(double damage, CallbackInfo callbackInfo) {

        LivingEntity entity = (LivingEntity) this.getOwner();
        System.out.println("TESTING123");

        if (entity.hasStatusEffect(MoreTotemsMod.SNIPER)) {

                this.damage = damage * 2;
            System.out.println(damage);


        }
        else {

        }

    } */
}
