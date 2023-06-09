package net.hyper_pigeon.moretotems.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class ExplosiveTotemOfUndying extends Item {

    /*constructor that inherits Item constructor*/
    public ExplosiveTotemOfUndying(Settings settings)
    {
        super(settings);
    }

    /*makes Explosive Totem of Undying play the creeper primed sound when right clicked*/
    /*@Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand)
    {
        playerEntity.playSound(SoundEvents.ENTITY_CREEPER_PRIMED, 1.0F, 1.0F);
        return new TypedActionResult<>(ActionResult.SUCCESS, playerEntity.getStackInHand(hand));
    }*/


}
