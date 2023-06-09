package net.hyper_pigeon.moretotems.entity;

import net.hyper_pigeon.moretotems.goals.FollowZombieSummonerGoal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.ZombieAttackGoal;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;

import java.util.Optional;
import java.util.UUID;

public class SummonedZombieEntity extends ZombieEntity {


    protected static final TrackedData<Optional<UUID>> SUMMONER_UUID;


    public SummonedZombieEntity(EntityType<? extends ZombieEntity> type, World world) {
        super(type, world);
        //setSummoner(world.getClosestPlayer(this.getX(), this.getZ(), 20));

    }



    @Override
    protected void initAttributes() {
        super.initAttributes();
        this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).setBaseValue(0.35D);
        this.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).setBaseValue(3.5D);
        this.getAttributeInstance(EntityAttributes.GENERIC_ARMOR).setBaseValue(2.8D);
    }

    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(SUMMONER_UUID, Optional.empty());
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(6, new FollowZombieSummonerGoal(this, this.getSummoner(), this.world, 1.0, this.getNavigation(), 90.0F, 3.0F, true));
        this.initCustomGoals();
    }

    @Override
    protected void initCustomGoals() {
        this.goalSelector.add(1, new ZombieAttackGoal(this, 2.0D, true));
        this.targetSelector.add(2, (new RevengeGoal(this, new Class[0])).setGroupRevenge());
    }


    private void setSummonerUuid(UUID uuid) {
        this.dataTracker.set(SUMMONER_UUID, Optional.ofNullable(uuid));
    }

    public Optional<UUID> getSummonerUuid() {
        return this.dataTracker.get(SUMMONER_UUID);
    }

    public void setSummoner(Entity player) {
        this.setSummonerUuid(player.getUuid());
    }



    public void writeCustomDataToNbt(NbtCompound tag) {
        super.writeCustomDataToNbt(tag);
        tag.putUuid("SummonerUUID", getSummonerUuid().get());
    }


    public void readCustomDataFromNbt(NbtCompound tag) {
        super.readCustomDataFromNbt(tag);
        UUID id;
        if (tag.contains("SummonerUUID")) {
           id = tag.getUuid("SummonerUUID");
        } else {
            id = tag.getUuid("SummonerUUID");
        }
        if (id != null) {
            this.setSummonerUuid(tag.getUuid("SummonerUUID"));
        }
    }



    @Override
    public void setAttacker(LivingEntity attacker) {
        if(attacker == getSummoner()) {
        }
        else {
            super.setAttacker(attacker);
        }
    }


    @Override
    public void tickMovement() {
        if (this.isAlive()) {
            if (getSummoner() != null) {
                if (getSummoner().getAttacker() != null) {
                    this.setTarget(getSummoner().getAttacker());
                } else if (getSummoner().getAttacking() != null) {
                    this.setTarget(getSummoner().getAttacking());
                }
            }
            else {


            }
        }
        super.tickMovement();
    }


    public LivingEntity getSummoner() {
        try {
            Optional<UUID> uUID = this.getSummonerUuid();
            return uUID.map(value -> this.world.getPlayerByUuid(value)).orElse(null);
        } catch (IllegalArgumentException var2) {
            return null;
        }
    }

    static {
        SUMMONER_UUID = DataTracker.registerData(SummonedZombieEntity.class, TrackedDataHandlerRegistry.OPTIONAL_UUID);
    }




}
