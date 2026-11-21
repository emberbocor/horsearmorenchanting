package io.github.emberbocor.horsearmorenchanting.mixin;

import io.github.emberbocor.horsearmorenchanting.HorseArmorEnchanting;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.EnchantmentMenu;
import net.minecraft.world.inventory.MenuType;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EnchantmentMenu.class)
public abstract class EnchantmentMenuMixin extends AbstractContainerMenu {
    @Shadow
    @Final
    private Container enchantSlots;

    private EnchantmentMenuMixin(@Nullable MenuType<?> type, int containerId) {
        super(type, containerId);
    }

    // A client without the mod clears the costs whenever a slot changes, because horse armor is not enchantable there.
    @Inject(method = "slotsChanged", at = @At("TAIL"))
    private void horsearmorenchanting$resendCosts(Container container, CallbackInfo ci) {
        if (container == this.enchantSlots && HorseArmorEnchanting.isHorseArmor(this.enchantSlots.getItem(0))) {
            this.sendAllDataToRemote();
        }
    }
}
