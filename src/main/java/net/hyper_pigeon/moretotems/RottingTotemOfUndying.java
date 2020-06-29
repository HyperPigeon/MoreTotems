package net.hyper_pigeon.moretotems;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class RottingTotemOfUndying extends Item {


    public RottingTotemOfUndying(Settings settings) {
        super(settings);
    }

    /*@Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand)
    {
        playerEntity.playSound(SoundEvents.ENTITY_ZOMBIE_HURT, 1.0F, 1.0F);

        //SummonedZombieEntity zombie_spawn = MoreTotemsMod.SUMMONED_ZOMBIE_ENTITY.create(world);
        //SummonedZombieEntity zombie_spawn_two = MoreTotemsMod.SUMMONED_ZOMBIE_ENTITY.create(world);
        //SummonedZombieEntity zombie_spawn_three = MoreTotemsMod.SUMMONED_ZOMBIE_ENTITY.create(world);
        //zombie_spawn.setSummoner(playerEntity);
        //zombie_spawn_two.setSummoner(playerEntity);
        //zombie_spawn.setPosition(playerEntity.getX(), playerEntity.getY(), playerEntity.getZ()+3);
        //zombie_spawn.setSummoner(playerEntity);
       // zombie_spawn_two.setPosition(playerEntity.getX(), playerEntity.getY(), playerEntity.getZ()-3);

        //world.spawnEntity(zombie_spawn);
       // world.spawnEntity(zombie_spawn_two);



        return new TypedActionResult<>(ActionResult.SUCCESS, playerEntity.getStackInHand(hand));

    }*/


}
