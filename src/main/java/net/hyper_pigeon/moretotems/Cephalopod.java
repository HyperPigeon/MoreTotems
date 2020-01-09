package net.hyper_pigeon.moretotems;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.entity.effect.StatusEffects;

public class Cephalopod extends StatusEffect {

    protected Cephalopod(StatusEffectType statusEffectType, int i) {
        super(statusEffectType, i);
    }

    public void applyUpdateEffect(LivingEntity entity, int i) {

        /*
     LivingEntity attacker;
     LivingEntity attacked;

       if(entity.getAttacker() != null) {

           attacker = entity.getAttacker();
           attacker.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 400, 1));

       }
       if(entity.getAttacking() != null) {

           attacked = entity.getAttacking();
           attacked.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 400, 1));

       } */



    }



}
