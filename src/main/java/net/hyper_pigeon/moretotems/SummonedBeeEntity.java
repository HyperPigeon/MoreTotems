package net.hyper_pigeon.moretotems;

import net.minecraft.entity.*;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class SummonedBeeEntity extends BeeEntity {

    private Entity summoner;

    public SummonedBeeEntity(World world_1) {
        super(EntityType.BEE, world_1);
    }


    protected void initAttributes() {
        this.getAttributeInstance(EntityAttributes.GENERIC_FLYING_SPEED).setBaseValue(10.0D);
        this.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).setBaseValue(5.0D);
        this.getAttributeInstance(EntityAttributes.GENERIC_FOLLOW_RANGE).setBaseValue(40.0D);

    }

    public void setSummoner(Entity player) {
        summoner = player;
    }



    protected void mobTick() {

        if(summoner instanceof PlayerEntity) {

           if(((PlayerEntity) summoner).getAttacker() != null) {


               this.setBeeAttacker(((PlayerEntity) summoner).getAttacker());

           }

            if(((PlayerEntity) summoner).getAttacking() != null) {

                this.setBeeAttacker(((PlayerEntity) summoner).getAttacking());

            }


        }

        super.mobTick();

    }

    private boolean setBeeAttacker(LivingEntity attacker) {

        if(attacker.equals(summoner)) {
            return false;
        }
        setAttacker(attacker);
        return true;



    }





    public boolean tryAttack(Entity target) {

        if(target.equals(summoner)) {
            return false;
        }
        else if (this.hasStung()){

            return false;

        }
        else {

            return super.tryAttack(target);

        }

    }





}
