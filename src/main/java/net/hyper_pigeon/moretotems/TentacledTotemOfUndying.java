package net.hyper_pigeon.moretotems;

import net.minecraft.item.Item;

public class TentacledTotemOfUndying extends Item {


    public TentacledTotemOfUndying(Settings settings) {
        super(settings);
    }

    /*@Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand)
    {
        playerEntity.playSound(SoundEvents.ENTITY_SQUID_SQUIRT, 1.0F, 1.0F);
        return new TypedActionResult<>(ActionResult.SUCCESS, playerEntity.getStackInHand(hand));

    }*/
}
