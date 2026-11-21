package io.github.emberbocor.horsearmorenchanting.mixin;

import io.github.emberbocor.horsearmorenchanting.HorseArmorEnchanting;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public abstract class ItemMixin {
    @Inject(method = "getEnchantmentValue()I", at = @At("HEAD"), cancellable = true)
    private void horsearmorenchanting$getEnchantmentValue(CallbackInfoReturnable<Integer> cir) {
        int value = HorseArmorEnchanting.enchantmentValue((Item) (Object) this);
        if (value > 0) {
            cir.setReturnValue(value);
        }
    }

    @Inject(method = "isEnchantable", at = @At("HEAD"), cancellable = true)
    private void horsearmorenchanting$isEnchantable(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (HorseArmorEnchanting.isHorseArmor(stack)) {
            cir.setReturnValue(true);
        }
    }
}
