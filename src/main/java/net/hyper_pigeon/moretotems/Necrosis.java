package net.hyper_pigeon.moretotems;


import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffects;

public class Necrosis extends StatusEffect {

    protected Necrosis(StatusEffectCategory statusEffectCategory, int i) {
        super(statusEffectCategory, i);
    }

    public void applyUpdateEffect(LivingEntity entity, int i) {

            if(entity.hasStatusEffect(StatusEffects.POISON)) {

                if (entity.getHealth() < entity.getMaxHealth()) {
                    entity.heal(0.3F);
                }

            }


    }

    public boolean canApplyUpdateEffect(int duration, int i) {
        return true;
    }






}
