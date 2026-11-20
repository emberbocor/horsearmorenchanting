package io.github.emberbocor.horsearmorenchanting.mixin;

import io.github.emberbocor.horsearmorenchanting.HorseArmorEnchanting;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Enchantment.class)
public abstract class EnchantmentMixin {
    @Inject(method = "matchingSlot", at = @At("HEAD"), cancellable = true)
    private void horsearmorenchanting$matchingSlot(EquipmentSlot slot, CallbackInfoReturnable<Boolean> cir) {
        if (HorseArmorEnchanting.worksFromBody((Enchantment) (Object) this, slot)) {
            cir.setReturnValue(true);
        }
    }
}
