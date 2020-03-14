package net.hyper_pigeon.moretotems;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.ZombieAttackGoal;
import net.minecraft.entity.mob.HuskEntity;
import net.minecraft.entity.mob.ZombiePigmanEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class SummonedHuskEntity extends HuskEntity {

    private LivingEntity summoner;


    public SummonedHuskEntity(EntityType<? extends HuskEntity> entityType, World world, LivingEntity conjurer) {
        super(entityType, world);
        setSummoner(conjurer);
    }

    protected void initAttributes() {
        super.initAttributes();

    }

    protected void initGoals() {
        this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(8, new LookAroundGoal(this));
        this.initCustomGoals();
    }

    protected void initCustomGoals() {
        this.goalSelector.add(2, new ZombieAttackGoal(this, 1.0D, false));
        this.targetSelector.add(1, (new RevengeGoal(this, new Class[0])).setGroupRevenge(ZombiePigmanEntity.class));
    }

    public void setSummoner(LivingEntity player) {
        summoner = player;
    }

    public void setAttacker(LivingEntity attacker) {
        if(attacker == summoner) {

            /*do nothing*/

        }
        else {

            super.setAttacker(attacker);

        }
    }


    public void tickMovement() {

        if (this.isAlive()) {

            if(summoner.getAttacker() != null) {

                this.setAttacker(summoner.getAttacker());

            }
            else if(summoner.getAttacking() != null) {

                this.setAttacker(summoner.getAttacker());

            }

        }


        super.tickMovement();

    }


}
