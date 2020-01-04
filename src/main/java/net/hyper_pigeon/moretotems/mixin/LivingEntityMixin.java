package net.hyper_pigeon.moretotems.mixin;

import net.hyper_pigeon.moretotems.MoreTotemsMod;
import net.hyper_pigeon.moretotems.SummonedBeeEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerTask;
import net.minecraft.util.DefaultedList;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/*The goal of this Mixin class is to give the totems the same ability to save the player from death, along with
some unique custom features*/

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin  extends Entity{

    @Shadow @Final private DefaultedList<ItemStack> equippedArmor;

    @Shadow
    public  native ItemStack getStackInHand(Hand hand_1);

    @Shadow public native void setHealth(float health);

    @Shadow public native boolean clearStatusEffects();

    @Shadow public native boolean addStatusEffect(StatusEffectInstance statusEffectInstance_1);

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
        ItemStack offhand_stack_copy;

        //Executes if the item in offhand_stack is equal to the explosive totem of Undying
        if (offhand_stack.getItem() == MoreTotemsMod.EXPLOSIVE_TOTEM_OF_UNDYING) {

            /*If the damagesource is something that could kill a player in creative mode, the totem does not work*/
            if (damageSource_1.isOutOfWorld()) {

                callback.setReturnValue(false);
            }
            else {
                /*sets copy to offhand_stack*/
                offhand_stack_copy = offhand_stack;
                /*deletes explosive totem from offhand*/
                offhand_stack.decrement(1);



                /*if the offhand_stack_copy is not empty, then execute*/


                    /*totem saves player from an untimely death*/
                    this.setHealth(1.0F);
                    this.clearStatusEffects();
                    this.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 125, 2));
                    this.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 350, 3));
                    this.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 100, 2));
                    this.world.sendEntityStatus(this, (byte)35);

                    /*Spawns a tntEntity on the player upon use of Explosive Totem*/

                    TntEntity tntEntity = EntityType.TNT.create(world);
                    tntEntity.setFuse(5);
                    tntEntity.setPosition(this.getX(), this.getY(), this.getZ());
                    world.spawnEntity(tntEntity);

                    callback.setReturnValue(true);




            }

        }
        else {



        }


    }


    @Inject(at = @At("HEAD"), method = "tryUseTotem", cancellable = true)
    public void useStingingTotem(DamageSource damageSource_1, CallbackInfoReturnable<Boolean> callback) {
        /*inits PlayerEntity entity, which is a copy of this casted to Living Entity and then PlayerEntity*/
        Entity entity =  this;




        /*ItemStack object that is set to the offhand item that entity is carrying*/
        ItemStack offhand_stack = ((LivingEntityMixin) entity).getStackInHand(Hand.OFF_HAND);
        ItemStack offhand_stack_copy;

        //Executes if the item in offhand_stack is equal to the explosive totem of Undying
        if (offhand_stack.getItem() == MoreTotemsMod.STINGING_TOTEM_OF_UNDYING) {

            /*If the damagesource is something that could kill a player in creative mode, the totem does not work*/
            if (damageSource_1.isOutOfWorld()) {

                callback.setReturnValue(false);
            }
            else {
                /*sets copy to offhand_stack*/
                offhand_stack_copy = offhand_stack;
                /*deletes explosive totem from offhand*/
                offhand_stack.decrement(1);



                /*if the offhand_stack_copy is not empty, then execute*/


                /*totem saves player from an untimely death*/
                this.setHealth(1.0F);
                this.clearStatusEffects();
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 650, 1));
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 1500, 1));
                //this.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 500, 2));
                this.world.sendEntityStatus(this, (byte)35);

                /*Spawns a SummonedBeeEntity on the player upon use of Stinging Totem*/

                /*TntEntity tntEntity = EntityType.TNT.create(world);
                tntEntity.setFuse(5);
                tntEntity.setPosition(this.getX(), this.getY(), this.getZ());
                world.spawnEntity(tntEntity);*/

                SummonedBeeEntity summonedBeeEntity_1 = s_bee.create(world);
                summonedBeeEntity_1.setSummoner(this);
                summonedBeeEntity_1.setPosition(this.getX(), this.getY(), this.getZ());
                world.spawnEntity(summonedBeeEntity_1);

                SummonedBeeEntity summonedBeeEntity_2 = s_bee.create(world);
                summonedBeeEntity_2.setSummoner(this);
                summonedBeeEntity_2.setPosition(this.getX(), this.getY(), this.getZ());
                world.spawnEntity(summonedBeeEntity_2);

                SummonedBeeEntity summonedBeeEntity_3 = s_bee.create(world);
                summonedBeeEntity_3.setSummoner(this);
                summonedBeeEntity_3.setPosition(this.getX(), this.getY(), this.getZ());
                world.spawnEntity(summonedBeeEntity_3);

                SummonedBeeEntity summonedBeeEntity_4 = s_bee.create(world);
                summonedBeeEntity_4.setSummoner(this);
                summonedBeeEntity_4.setPosition(this.getX(), this.getY(), this.getZ());
                world.spawnEntity(summonedBeeEntity_4);

                SummonedBeeEntity summonedBeeEntity_5 = s_bee.create(world);
                summonedBeeEntity_5.setSummoner(this);
                summonedBeeEntity_5.setPosition(this.getX(), this.getY(), this.getZ());
                world.spawnEntity(summonedBeeEntity_5);


                callback.setReturnValue(true);




            }

        }
        else {



        }


    }

    @Inject(at = @At("HEAD"), method = "tryUseTotem", cancellable = true)
    public void useTeleportingTotem(DamageSource damageSource_1, CallbackInfoReturnable<Boolean> callback) {
        /*inits PlayerEntity entity, which is a copy of this casted to Living Entity and then PlayerEntity*/
        Entity entity =  this;


        /*ItemStack object that is set to the offhand item that entity is carrying*/
        ItemStack offhand_stack = ((LivingEntityMixin) entity).getStackInHand(Hand.OFF_HAND);
        ItemStack offhand_stack_copy;

        //Executes if the item in offhand_stack is equal to the explosive totem of Undying
        if (offhand_stack.getItem() == MoreTotemsMod.TELEPORTING_TOTEM_OF_UNDYING) {

                /*sets copy to offhand_stack*/
                offhand_stack_copy = offhand_stack;

                offhand_stack.decrement(1);

                /*totem saves player from an untimely death*/
                this.setHealth(1.0F);
                this.clearStatusEffects();
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 9500, 10));




            if (entity instanceof PlayerEntity) {

                PlayerEntity the_player = (PlayerEntity) entity;

                BlockPos spawn_pointer = the_player.getSpawnPosition();

                //ServerTask dimension_shift = new ServerTask((getServer().getTicks())+1, (Runnable) the_player.changeDimension(DimensionType.OVERWORLD));


                if(!(the_player.dimension == DimensionType.OVERWORLD)) {


                    ServerTask dimension_shift = new ServerTask((getServer().getTicks())+1, () -> changeDimension(DimensionType.OVERWORLD));

                    the_server.send(dimension_shift);

                    /*ServerTask teleport_shift = new ServerTask((getServer().getTicks())+1, () -> the_player.teleport(spawn_pointer.getX(),spawn_pointer.getY(),spawn_pointer.getZ()));

                   the_server.send(dimension_shift);*/

                }

                ServerTask teleport_shift = new ServerTask((getServer().getTicks())+1, () -> the_player.teleport(spawn_pointer.getX(),spawn_pointer.getY(),spawn_pointer.getZ()));

                the_server.send(teleport_shift);

                /*the_player.teleport(spawn_pointer.getX(),spawn_pointer.getY(),spawn_pointer.getZ());*/

                this.world.addParticle(ParticleTypes.PORTAL,
                        this.getParticleX(0.5D),
                        this.getRandomBodyY() - 0.25D,
                        this.getParticleZ(0.5D),
                        (this.random.nextDouble() - 0.5D) * 2.0D, - this.random.nextDouble(),
                        (this.random.nextDouble() - 0.5D) * 2.0D);



            }

                this.world.sendEntityStatus(this, (byte)35);




                callback.setReturnValue(true);






        }
        else {



        }


    }



    @Inject(at = @At("HEAD"), method = "tryUseTotem", cancellable = true)
    public void useGhastlyTotem(DamageSource damageSource_1, CallbackInfoReturnable<Boolean> callback) {
        /*inits PlayerEntity entity, which is a copy of this casted to Living Entity and then PlayerEntity*/
        Entity entity =  this;

        /*ItemStack object that is set to the offhand item that entity is carrying*/
        ItemStack offhand_stack = ((LivingEntityMixin) entity).getStackInHand(Hand.OFF_HAND);
        ItemStack offhand_stack_copy;

        //Executes if the item in offhand_stack is equal to the ghastly totem of Undying
        if (offhand_stack.getItem() == MoreTotemsMod.GHASTLY_TOTEM_OF_UNDYING) {

            /*If the damagesource is something that could kill a player in creative mode, the totem does not work*/
            if (damageSource_1.isOutOfWorld()) {

                callback.setReturnValue(false);
            }
            else {
                /*sets copy to offhand_stack*/
                offhand_stack_copy = offhand_stack;
                /*deletes explosive totem from offhand*/
                offhand_stack.decrement(1);


                /*if the offhand_stack_copy is not empty, then execute*/


                /*totem saves player from an untimely death*/
                this.setHealth(1.0F);
                this.clearStatusEffects();
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 525, 1));
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 500, 2));
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 900, 1));
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 1250, 1));


                this.world.sendEntityStatus(this, (byte)35);

                callback.setReturnValue(true);




            }

        }
        else {



        }

    }


    @Inject(at = @At("HEAD"), method = "tryUseTotem", cancellable = true)
    public void useSkeletalTotem(DamageSource damageSource_1, CallbackInfoReturnable<Boolean> callback) {
        /*inits PlayerEntity entity, which is a copy of this casted to Living Entity and then PlayerEntity*/
        Entity entity =  this;

        /*ItemStack object that is set to the offhand item that entity is carrying*/
        ItemStack offhand_stack = ((LivingEntityMixin) entity).getStackInHand(Hand.OFF_HAND);
        ItemStack offhand_stack_copy;

        //Executes if the item in offhand_stack is equal to the ghastly totem of Undying
        if (offhand_stack.getItem() == MoreTotemsMod.SKELETAL_TOTEM_OF_UNDYING) {

            /*If the damagesource is something that could kill a player in creative mode, the totem does not work*/
            if (damageSource_1.isOutOfWorld()) {

                callback.setReturnValue(false);
            }
            else {
                /*sets copy to offhand_stack*/
                offhand_stack_copy = offhand_stack;
                /*deletes explosive totem from offhand*/
                offhand_stack.decrement(1);


                /*if the offhand_stack_copy is not empty, then execute*/


                /*totem saves player from an untimely death*/
                this.setHealth(1.0F);
                this.clearStatusEffects();
                this.addStatusEffect(new StatusEffectInstance(MoreTotemsMod.SNIPER, 2500, 0));
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 400, 1));
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 450, 1));
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 300, 1));


                this.world.sendEntityStatus(this, (byte)35);

                callback.setReturnValue(true);




            }

        }
        else {



        }

    }





}
