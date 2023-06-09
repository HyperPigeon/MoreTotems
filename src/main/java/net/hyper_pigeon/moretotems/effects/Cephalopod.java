package net.hyper_pigeon.moretotems.effects;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class Cephalopod extends StatusEffect {

    public Cephalopod(StatusEffectCategory statusEffectCategory, int i) {
        super(statusEffectCategory, i);
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
