package net.hyper_pigeon.moretotems;

import net.hyper_pigeon.moretotems.goals.FollowZombieSummonerGoal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.*;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.ServerConfigHandler;
import net.minecraft.world.World;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class SummonedZombieEntity extends ZombieEntity {


    protected static final TrackedData<Optional<UUID>> SUMMONER_UUID;

    //private final static CompoundTag ENTITY_SUMMONER = new CompoundTag();


    public SummonedZombieEntity(EntityType<? extends ZombieEntity> type, World world) {
        super(type, world);
        //setSummoner(world.getClosestPlayer(this.getX(), this.getZ(), 20));

    }




    @Override
    protected void initAttributes() {
        super.initAttributes();
        this.getAttributeInstance(EntityAttributes.MOVEMENT_SPEED).setBaseValue(0.35D);
        this.getAttributeInstance(EntityAttributes.ATTACK_DAMAGE).setBaseValue(3.5D);
        this.getAttributeInstance(EntityAttributes.ARMOR).setBaseValue(2.8D);
        this.dataTracker.startTracking(SUMMONER_UUID, Optional.empty());
    }

    protected void initDataTracker() {
        super.initDataTracker();
    }

    @Override
    protected void initGoals() {
        //this.goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        //this.goalSelector.add(3, new LookAroundGoal(this));
        this.goalSelector.add(6, new FollowZombieSummonerGoal(this, (LivingEntity) this.getSummoner(), this.world, 1.0, this.getNavigation(), 90.0F, 3.0F, true));
        this.initCustomGoals();
    }

    @Override
    protected void initCustomGoals() {
        this.goalSelector.add(1, new ZombieAttackGoal(this, 2.0D, true));
        this.targetSelector.add(2, (new RevengeGoal(this, new Class[0])).setGroupRevenge());
    }




    public void setSummonerUuid(UUID uuid) {
        this.dataTracker.set(SUMMONER_UUID, Optional.ofNullable(uuid));
    }

    public UUID getSummonerUuid() {
        return (UUID)((Optional)this.dataTracker.get(SUMMONER_UUID)).orElse((Object)null);
    }

    public void setSummoner(Entity player) {
        //riteCustomDataToTag(SUMMONER_UUID);
        this.setSummonerUuid(player.getUuid());
    }



    public void writeCustomDataToTag(CompoundTag tag) {
        super.writeCustomDataToTag(tag);
        if (getSummoner().getUuid() == null) {
            tag.putString("SummonerUUID", "");
        } else {
            tag.putString("SummonerUUID", getSummoner().getUuid().toString());
        }
    }


    public void readCustomDataFromTag(CompoundTag tag) {
        super.readCustomDataFromTag(tag);
        String string3;
        if (tag.contains("SummonerUUID", 8)) {
            string3 = tag.getString("SummonerUUID");
        } else {
            String string2 = tag.getString("Summoner");
            string3 = ServerConfigHandler.getPlayerUuidByName(this.getServer(), string2);
        }


        if (!string3.isEmpty()) {
            try {
                this.setSummonerUuid(UUID.fromString(string3));
            } catch (Throwable var4) {

            }
        }
    }



    @Override
    public void setAttacker(LivingEntity attacker) {
        if(attacker == getSummoner()) {
        }
        else if(getSummoner().getAttacker() != null) {
            super.setAttacker(getSummoner().getAttacker());
        }
        else if (getSummoner().getAttacking() != null) {
            super.setAttacker(getSummoner().getAttacking());
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
            UUID uUID = this.getSummonerUuid();
            return uUID == null ? null : this.world.getPlayerByUuid(uUID);
        } catch (IllegalArgumentException var2) {
            return null;
        }
    }

    static {
        SUMMONER_UUID = DataTracker.registerData(SummonedZombieEntity.class, TrackedDataHandlerRegistry.OPTIONAL_UUID);
    }




}
