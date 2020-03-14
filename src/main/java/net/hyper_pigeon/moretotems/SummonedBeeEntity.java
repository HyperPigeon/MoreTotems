package net.hyper_pigeon.moretotems;

import net.minecraft.entity.*;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class SummonedBeeEntity extends BeeEntity {

    private Entity summoner;


    public SummonedBeeEntity(EntityType<? extends BeeEntity> entityType_1, World world_1) {

        super(entityType_1, world_1);
        setSummoner(world_1.getClosestPlayer(this.getX(), this.getZ(), 5));


    }



    protected void initAttributes() {
        super.initAttributes();
        this.getAttributeInstance(EntityAttributes.FLYING_SPEED).setBaseValue(8.5D);
        this.getAttributeInstance(EntityAttributes.ATTACK_DAMAGE).setBaseValue(4.5D);

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

    public boolean setBeeAttacker(Entity attacker) {

        if(attacker.equals(summoner)) {

            return false;

        }
            return super.setBeeAttacker(attacker);


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
