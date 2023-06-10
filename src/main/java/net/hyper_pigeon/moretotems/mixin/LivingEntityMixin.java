package net.hyper_pigeon.moretotems.mixin;

import net.hyper_pigeon.moretotems.MoreTotemsMod;
import net.hyper_pigeon.moretotems.entity.SummonedBeeEntity;
import net.hyper_pigeon.moretotems.entity.SummonedZombieEntity;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerTask;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/*The goal of this Mixin class is to give the totems the same ability to save the player from death, along with
some unique custom features*/

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin  extends Entity{


    @Shadow
    public  native ItemStack getStackInHand(Hand hand_1);

    @Shadow
    public native boolean hasStatusEffect(StatusEffect effect);

    @Shadow public native void setHealth(float health);

    @Shadow public native boolean clearStatusEffects();

    @Shadow public native boolean addStatusEffect(StatusEffectInstance statusEffectInstance_1);

    @Shadow public native EntityGroup getGroup();

    public EntityType<SummonedBeeEntity> s_bee = MoreTotemsMod.SUMMONED_BEE_ENTITY;

    public MinecraftServer the_server = getServer();

    protected LivingEntityMixin(EntityType<?> entityType_1, World world_1) {
        super(entityType_1, world_1);
    }



    @Inject(at = @At("HEAD"), method = "tryUseTotem", cancellable = true)
    public void useExplosiveTotem(DamageSource damageSource_1, CallbackInfoReturnable<Boolean> callback) {
        /*inits PlayerEntity entity, which is a copy of this casted to Living Entity and then PlayerEntity*/
        Entity entity =  this;



        /*ItemStack object that is set to the offhand item that entity is carrying*/
        ItemStack offhand_stack = ((LivingEntityMixin) entity).getStackInHand(Hand.OFF_HAND);

        ItemStack mainhand_stack = ((LivingEntityMixin) entity).getStackInHand(Hand.MAIN_HAND);

        //Executes if the item in offhand_stack is equal to the explosive totem of Undying
        if ((offhand_stack.getItem() == MoreTotemsMod.EXPLOSIVE_TOTEM_OF_UNDYING) || (mainhand_stack.getItem() == MoreTotemsMod.EXPLOSIVE_TOTEM_OF_UNDYING) ) {

            /*If the damagesource is something that could kill a player in creative mode, the totem does not work*/
            if (damageSource_1.getType().equals(DamageTypes.OUT_OF_WORLD)) {

                callback.setReturnValue(false);
            }
            else {
                /*sets copy to offhand_stack*/
                /*deletes explosive totem from offhand*/

                if((offhand_stack.getItem() == MoreTotemsMod.EXPLOSIVE_TOTEM_OF_UNDYING)) {
                    offhand_stack.decrement(1);
                }
                else if((mainhand_stack.getItem() == MoreTotemsMod.EXPLOSIVE_TOTEM_OF_UNDYING)){

                    mainhand_stack.decrement(1);

                }



                /*totem saves player from an untimely death*/
                this.setHealth(1.0F);
                this.clearStatusEffects();
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 125, 2));
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 350, 4));
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 100, 2));
                this.getWorld().sendEntityStatus(this, (byte)35);

                /*Spawns a tntEntity on the player upon use of Explosive Totem*/

                TntEntity tntEntity = EntityType.TNT.create(getWorld());
                tntEntity.setFuse(5);
                tntEntity.refreshPositionAndAngles(this.getX() , this.getY() , this.getZ(), 0, 0);
                getWorld().spawnEntity(tntEntity);

                callback.setReturnValue(true);




            }

        }


    }


    @Inject(at = @At("HEAD"), method = "tryUseTotem", cancellable = true)
    public void useStingingTotem(DamageSource damageSource_1, CallbackInfoReturnable<Boolean> callback) {
        /*inits PlayerEntity entity, which is a copy of this casted to Living Entity and then PlayerEntity*/
        Entity entity =  this;


        /*ItemStack object that is set to the offhand item that entity is carrying*/
        ItemStack offhand_stack = ((LivingEntityMixin) entity).getStackInHand(Hand.OFF_HAND);
        ItemStack offhand_stack_copy;

        ItemStack mainhand_stack = ((LivingEntityMixin) entity).getStackInHand(Hand.MAIN_HAND);

        //Executes if the item in offhand_stack is equal to the explosive totem of Undying
        if ((offhand_stack.getItem() == MoreTotemsMod.STINGING_TOTEM_OF_UNDYING) || (mainhand_stack.getItem() == MoreTotemsMod.STINGING_TOTEM_OF_UNDYING)) {

            /*If the damagesource is something that could kill a player in creative mode, the totem does not work*/
            if (damageSource_1.getType().equals(DamageTypes.OUT_OF_WORLD)) {

                callback.setReturnValue(false);
            }
            else {
                /*deletes explosive totem from offhand*/
                if((offhand_stack.getItem() == MoreTotemsMod.STINGING_TOTEM_OF_UNDYING)) {
                    offhand_stack.decrement(1);
                }
                else if((mainhand_stack.getItem() == MoreTotemsMod.STINGING_TOTEM_OF_UNDYING)){
                    mainhand_stack.decrement(1);
                }

                /*totem saves player from an untimely death*/
                this.setHealth(1.0F);
                this.clearStatusEffects();
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 650, 1));
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 1500, 1));
                //this.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 500, 2));
                this.getWorld().sendEntityStatus(this, (byte)35);

                /*Spawns a SummonedBeeEntity on the player upon use of Stinging Totem*/



                SummonedBeeEntity summonedBeeEntity_1 = s_bee.create(getWorld());
                summonedBeeEntity_1.setSummoner(this);
                summonedBeeEntity_1.refreshPositionAndAngles(this.getX(), this.getY() + 1, this.getZ(), 0, 0);
                getWorld().spawnEntity(summonedBeeEntity_1);


                SummonedBeeEntity summonedBeeEntity_2 = s_bee.create(getWorld());
                summonedBeeEntity_2.setSummoner(this);
                summonedBeeEntity_2.refreshPositionAndAngles(this.getX(), this.getY() + 1, this.getZ(), 0, 0);
                getWorld().spawnEntity(summonedBeeEntity_2);

                SummonedBeeEntity summonedBeeEntity_3 = s_bee.create(getWorld());
                summonedBeeEntity_3.setSummoner(this);
                summonedBeeEntity_3.refreshPositionAndAngles(this.getX() + 1, this.getY() + 1, this.getZ(), 0, 0);
                getWorld().spawnEntity(summonedBeeEntity_3);

                SummonedBeeEntity summonedBeeEntity_4 = s_bee.create(getWorld());
                summonedBeeEntity_4.setSummoner(this);
                summonedBeeEntity_4.refreshPositionAndAngles(this.getX(), this.getY() + 1, this.getZ() + 1, 0, 0);
                getWorld().spawnEntity(summonedBeeEntity_4);

                SummonedBeeEntity summonedBeeEntity_5 = s_bee.create(getWorld());
                summonedBeeEntity_5.setSummoner(this);
                summonedBeeEntity_5.refreshPositionAndAngles(this.getX() - 1, this.getY() + 1, this.getZ(), 0, 0);
                getWorld().spawnEntity(summonedBeeEntity_5);


                SummonedBeeEntity summonedBeeEntity_6 = s_bee.create(getWorld());
                summonedBeeEntity_5.setSummoner(this);
                summonedBeeEntity_5.refreshPositionAndAngles(this.getX() , this.getY() + 1, this.getZ()-1, 0, 0);
                getWorld().spawnEntity(summonedBeeEntity_6);

                callback.setReturnValue(true);

            }
        }


    }

    @Inject(at = @At("HEAD"), method = "tryUseTotem", cancellable = true)
    public void useTeleportingTotem(DamageSource damageSource_1, CallbackInfoReturnable<Boolean> callback) {

        /*inits PlayerEntity entity, which is a copy of this casted to Living Entity and then PlayerEntity*/
        LivingEntity entity = (LivingEntity)(Object)this;

        /*ItemStack object that is set to the offhand item that entity is carrying*/
        ItemStack offhand_stack = entity.getStackInHand(Hand.OFF_HAND);
        ItemStack mainhand_stack = entity.getStackInHand(Hand.MAIN_HAND);

        //Executes if the item in offhand_stack is equal to the explosive totem of Undying
        if ((offhand_stack.getItem() == MoreTotemsMod.TELEPORTING_TOTEM_OF_UNDYING) || (mainhand_stack.getItem() == MoreTotemsMod.TELEPORTING_TOTEM_OF_UNDYING)) {

            /*sets copy to offhand_stack*/

            if((offhand_stack.getItem() == MoreTotemsMod.TELEPORTING_TOTEM_OF_UNDYING)) {
                offhand_stack.decrement(1);
            }
            else {

                mainhand_stack.decrement(1);

            }
            /*totem saves player from an untimely death*/
            this.setHealth(1.0F);
            this.clearStatusEffects();
            this.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 17500, 5));

            if(entity instanceof ServerPlayerEntity && !getWorld().isClient()){

                ServerPlayerEntity player = (ServerPlayerEntity) entity;
                BlockPos spawn_pointer = player.getSpawnPointPosition();


                if (player.getWorld().getRegistryKey() != player.getSpawnPointDimension()) {

                    RegistryKey<World> registryKey =  player.getSpawnPointDimension();
                    ServerWorld serverWorld2 = player.getServer().getWorld(registryKey);

                    ServerTask dimension_shift = new ServerTask((getServer().getTicks()) + 1, () -> player.moveToWorld(serverWorld2));
                    the_server.send(dimension_shift);

                }

                if(spawn_pointer != null) {
                    ServerTask teleport_shift = new ServerTask((getServer().getTicks()) + 1, () -> player.teleport(player.getServerWorld(), spawn_pointer.getX(), spawn_pointer.getY(), spawn_pointer.getZ(), 5.0F, 5.0F));
                    the_server.send(teleport_shift);
                }

                this.getWorld().addParticle(ParticleTypes.PORTAL,
                        this.getParticleX(0.5D),
                        this.getRandomBodyY() - 0.25D,
                        this.getParticleZ(0.5D),
                        (this.random.nextDouble() - 0.5D) * 2.0D, -this.random.nextDouble(),
                        (this.random.nextDouble() - 0.5D) * 2.0D);

                this.getWorld().sendEntityStatus(this, (byte) 35);
                callback.setReturnValue(true);
            }

        }
    }



    @Inject(at = @At("HEAD"), method = "tryUseTotem", cancellable = true)
    public void useGhastlyTotem(DamageSource damageSource_1, CallbackInfoReturnable<Boolean> callback) {
        /*inits PlayerEntity entity, which is a copy of this casted to Living Entity and then PlayerEntity*/
        Entity entity =  this;

        /*ItemStack object that is set to the offhand item that entity is carrying*/
        ItemStack offhand_stack = ((LivingEntityMixin) entity).getStackInHand(Hand.OFF_HAND);

        ItemStack mainhand_stack = ((LivingEntityMixin) entity).getStackInHand(Hand.MAIN_HAND);

        //Executes if the item in offhand_stack is equal to the ghastly totem of Undying
        if ((offhand_stack.getItem() == MoreTotemsMod.GHASTLY_TOTEM_OF_UNDYING) || (mainhand_stack.getItem() == MoreTotemsMod.GHASTLY_TOTEM_OF_UNDYING)) {

            /*If the damagesource is something that could kill a player in creative mode, the totem does not work*/
            if (damageSource_1.getType().equals(DamageTypes.OUT_OF_WORLD)) {

                callback.setReturnValue(false);
            }
            else {
                /*sets copy to offhand_stack*/

                if((offhand_stack.getItem() == MoreTotemsMod.GHASTLY_TOTEM_OF_UNDYING)) {
                    offhand_stack.decrement(1);
                }
                else if((mainhand_stack.getItem() == MoreTotemsMod.GHASTLY_TOTEM_OF_UNDYING)){

                    mainhand_stack.decrement(1);

                }


                /*if the offhand_stack_copy is not empty, then execute*/


                /*totem saves player from an untimely death*/
                this.setHealth(1.0F);
                this.clearStatusEffects();
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 1325, 1));
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 1525, 2));
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 1000, 1));
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 1750, 1));


                this.getWorld().sendEntityStatus(this, (byte)35);

                callback.setReturnValue(true);

            }

        }

    }


    @Inject(at = @At("HEAD"), method = "tryUseTotem", cancellable = true)
    public void useSkeletalTotem(DamageSource damageSource_1, CallbackInfoReturnable<Boolean> callback) {
        /*inits PlayerEntity entity, which is a copy of this casted to Living Entity and then PlayerEntity*/
        Entity entity =  this;

        /*ItemStack object that is set to the offhand item that entity is carrying*/
        ItemStack offhand_stack = ((LivingEntityMixin) entity).getStackInHand(Hand.OFF_HAND);

        ItemStack mainhand_stack = ((LivingEntityMixin) entity).getStackInHand(Hand.MAIN_HAND);

        //Executes if the item in offhand_stack is equal to the ghastly totem of Undying
        if ((offhand_stack.getItem() == MoreTotemsMod.SKELETAL_TOTEM_OF_UNDYING) || (mainhand_stack.getItem() == MoreTotemsMod.SKELETAL_TOTEM_OF_UNDYING)) {

            /*If the damagesource is something that could kill a player in creative mode, the totem does not work*/
            if (damageSource_1.getType().equals(DamageTypes.OUT_OF_WORLD)) {

                callback.setReturnValue(false);
            }
            else {
                /*sets copy to offhand_stack*/
                /*deletes  totem from offhand*/
                if((offhand_stack.getItem() == MoreTotemsMod.SKELETAL_TOTEM_OF_UNDYING)) {
                    offhand_stack.decrement(1);
                }
                else if((mainhand_stack.getItem() == MoreTotemsMod.SKELETAL_TOTEM_OF_UNDYING)){

                    mainhand_stack.decrement(1);

                }


                /*if the offhand_stack_copy is not empty, then execute*/


                /*totem saves player from an untimely death*/
                this.setHealth(1.0F);
                this.clearStatusEffects();
                this.addStatusEffect(new StatusEffectInstance(MoreTotemsMod.SNIPER, 2000, 0));
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 600, 0));
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 350, 1));
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 250, 0));

                this.getWorld().sendEntityStatus(this, (byte)35);

                callback.setReturnValue(true);


            }

        }

    }


    @Inject(at = @At("HEAD"), method = "tryUseTotem", cancellable = true)
    public void useTentacledTotem(DamageSource damageSource_1, CallbackInfoReturnable<Boolean> callback) {
        /*inits PlayerEntity entity, which is a copy of this casted to Living Entity and then PlayerEntity*/
        Entity entity =  this;

        /*ItemStack object that is set to the offhand item that entity is carrying*/
        ItemStack offhand_stack = ((LivingEntityMixin) entity).getStackInHand(Hand.OFF_HAND);

        ItemStack mainhand_stack = ((LivingEntityMixin) entity).getStackInHand(Hand.MAIN_HAND);

        //Executes if the item in offhand_stack is equal to the ghastly totem of Undying
        if ((offhand_stack.getItem() == MoreTotemsMod.TENTACLED_TOTEM_OF_UNDYING) || (mainhand_stack.getItem() == MoreTotemsMod.TENTACLED_TOTEM_OF_UNDYING)) {

            /*If the damagesource is something that could kill a player in creative mode, the totem does not work*/
            if (damageSource_1.getType().equals(DamageTypes.OUT_OF_WORLD)) {

                callback.setReturnValue(false);
            }
            else {
                /*sets copy to offhand_stack*/
                /*deletes  totem from offhand*/
                if((offhand_stack.getItem() == MoreTotemsMod.TENTACLED_TOTEM_OF_UNDYING)) {
                    offhand_stack.decrement(1);
                }
                else if((mainhand_stack.getItem() == MoreTotemsMod.TENTACLED_TOTEM_OF_UNDYING)){

                    mainhand_stack.decrement(1);

                }


                /*if the offhand_stack_copy is not empty, then execute*/


                /*totem saves player from an untimely death*/
                this.setHealth(1.0F);
                this.clearStatusEffects();
                this.addStatusEffect(new StatusEffectInstance(MoreTotemsMod.CEPHALOPOD, 2000, 0));
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.CONDUIT_POWER, 2000, 0));
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 950, 1));
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 200, 2));
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.DOLPHINS_GRACE, 950, 0));



                this.getWorld().sendEntityStatus(this, (byte)35);

                callback.setReturnValue(true);




            }

        }

    }



    @Inject(at = @At("RETURN"), method = "damage", cancellable = true)
    public void applyCephalopodEffect(DamageSource source, float amount, CallbackInfoReturnable<Boolean> callback) {

        Entity entity3 = source.getAttacker();

        Entity entity =  this;


        if(entity3 instanceof LivingEntity) {

            if(entity3 != null) {

                if(((LivingEntity) entity3).hasStatusEffect(MoreTotemsMod.CEPHALOPOD))
                {

                    this.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 450, 1));
                    callback.setReturnValue(true);

                }
                else {

                    if(((LivingEntityMixin) entity).hasStatusEffect(MoreTotemsMod.CEPHALOPOD)) {

                        ((LivingEntity) entity3).addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 450, 1));
                        ((LivingEntity) entity3).addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 150, 0));

                    }


                }

            }
            else {

                callback.setReturnValue(false);

            }

        }


    }


    @Inject(at = @At("HEAD"), method = "tryUseTotem", cancellable = true)
    public void useRottingTotem(DamageSource damageSource_1, CallbackInfoReturnable<Boolean> callback) {
        /*inits PlayerEntity entity, which is a copy of this casted to Living Entity and then PlayerEntity*/
        Entity entity =  this;

        /*ItemStack object that is set to the offhand item that entity is carrying*/
        ItemStack offhand_stack = ((LivingEntityMixin) entity).getStackInHand(Hand.OFF_HAND);

        ItemStack mainhand_stack = ((LivingEntityMixin) entity).getStackInHand(Hand.MAIN_HAND);

        //Executes if the item in offhand_stack is equal to the ghastly totem of Undying
        if ((offhand_stack.getItem() == MoreTotemsMod.ROTTING_TOTEM_OF_UNDYING) || (mainhand_stack.getItem() == MoreTotemsMod.ROTTING_TOTEM_OF_UNDYING)) {

            /*If the damagesource is something that could kill a player in creative mode, the totem does not work*/
            if (damageSource_1.getType().equals(DamageTypes.OUT_OF_WORLD)) {

                callback.setReturnValue(false);
            }
            else {
                /*sets copy to offhand_stack*/
                /*deletes  totem from offhand*/
                if((offhand_stack.getItem() == MoreTotemsMod.ROTTING_TOTEM_OF_UNDYING)) {
                    offhand_stack.decrement(1);
                }
                else if((mainhand_stack.getItem() == MoreTotemsMod.ROTTING_TOTEM_OF_UNDYING)){

                    mainhand_stack.decrement(1);

                }


                /*totem saves player from an untimely death*/
                this.setHealth(1.0F);
                this.clearStatusEffects();
                this.addStatusEffect(new StatusEffectInstance(MoreTotemsMod.NECROSIS,2000,0));
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 300,2));
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 400, 0));

                SummonedZombieEntity zombie_spawn = MoreTotemsMod.SUMMONED_ZOMBIE_ENTITY.create(getWorld());
                SummonedZombieEntity zombie_spawn_two = MoreTotemsMod.SUMMONED_ZOMBIE_ENTITY.create(getWorld());
                SummonedZombieEntity zombie_spawn_three = MoreTotemsMod.SUMMONED_ZOMBIE_ENTITY.create(getWorld());
                SummonedZombieEntity zombie_spawn_four = MoreTotemsMod.SUMMONED_ZOMBIE_ENTITY.create(getWorld());

                assert zombie_spawn != null;
                zombie_spawn.setSummoner(this);
                assert zombie_spawn_two != null;
                zombie_spawn_two.setSummoner(this);
                assert zombie_spawn_three != null;
                zombie_spawn_three.setSummoner(this);
                assert zombie_spawn_four != null;
                zombie_spawn_four.setSummoner(this);

                zombie_spawn.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ()+3, 0, 0);

                zombie_spawn_two.refreshPositionAndAngles(this.getX() , this.getY(), this.getZ()-3, 0, 0);

                zombie_spawn_three.refreshPositionAndAngles(this.getX() -3, this.getY(), this.getZ(), 0, 0);

                zombie_spawn_four.refreshPositionAndAngles(this.getX()+2, this.getY(), this.getZ()+2, 0, 0);

                getWorld().spawnEntity(zombie_spawn);

                getWorld().spawnEntity(zombie_spawn_two);

                getWorld().spawnEntity(zombie_spawn_three);

                getWorld().spawnEntity(zombie_spawn_four);

                this.getWorld().sendEntityStatus(this, (byte)35);

                callback.setReturnValue(true);


            }

        }

    }




    @Inject(at = @At("HEAD"), method = "isUndead", cancellable = true)
    public void NecroCheck(CallbackInfoReturnable<Boolean> callback) {

        if (this.hasStatusEffect(MoreTotemsMod.NECROSIS)){
            callback.setReturnValue(true);

        }
        else if(this.getGroup() == EntityGroup.UNDEAD) {
            callback.setReturnValue(true);
        }

    }


}
