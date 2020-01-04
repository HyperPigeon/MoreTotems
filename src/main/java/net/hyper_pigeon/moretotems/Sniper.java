package net.hyper_pigeon.moretotems;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.BowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.function.Predicate;

public class Sniper extends StatusEffect {


    public Sniper(StatusEffectType statusEffectType, int i) {
        super(statusEffectType, i);
    }


    public void applyUpdateEffect(LivingEntity entity, int i) {
         /*
        Item offhand_item = entity.getOffHandStack().getItem();

        Item mainhand_item = entity.getMainHandStack().getItem();

        ArrowItem the_arrow;

        if(mainhand_item instanceof BowItem){
            ItemStack arrowStack = BowItem.getHeldProjectile(entity, BowItem.BOW_PROJECTILES);
            the_arrow = (ArrowItem) arrowStack.getItem();
            ProjectileEntity projectileEntity = the_arrow.createArrow(entity.world, arrowStack, entity);
            projectileEntity.setDamage(projectileEntity.getDamage() * 2);
        }
        if(entity.getArrowType(mainhand_stack) != ItemStack.EMPTY) {
            System.out.println("Not empty");
        }
        else {
            System.out.println("empty");
        }*/

    }


    public boolean canApplyUpdateEffect(int duration, int i) {
        return true;
    }



}
