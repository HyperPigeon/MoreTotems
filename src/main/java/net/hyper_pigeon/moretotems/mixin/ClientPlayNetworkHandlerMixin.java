package net.hyper_pigeon.moretotems.mixin;

import net.hyper_pigeon.moretotems.MoreTotemsMod;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {

    private static boolean isMoreTotem(ItemStack itemStack){
        return itemStack.isOf(MoreTotemsMod.EXPLOSIVE_TOTEM_OF_UNDYING) ||
                itemStack.isOf(MoreTotemsMod.GHASTLY_TOTEM_OF_UNDYING) ||
                itemStack.isOf(MoreTotemsMod.ROTTING_TOTEM_OF_UNDYING) ||
                itemStack.isOf(MoreTotemsMod.SKELETAL_TOTEM_OF_UNDYING) ||
                itemStack.isOf(MoreTotemsMod.STINGING_TOTEM_OF_UNDYING) ||
                itemStack.isOf(MoreTotemsMod.TELEPORTING_TOTEM_OF_UNDYING) ||
                itemStack.isOf(MoreTotemsMod.TENTACLED_TOTEM_OF_UNDYING);
    }


    @Inject(method = "getActiveTotemOfUndying", at = @At(value = "RETURN"), cancellable = true)
    private static void getActiveMoreTotemOfUndying(PlayerEntity player, CallbackInfoReturnable<ItemStack> cir){
        for(Hand hand : Hand.values()) {
            ItemStack itemStack = player.getStackInHand(hand);
            if(isMoreTotem(itemStack)) {
                cir.setReturnValue(itemStack);
            }
        }
    }
}
