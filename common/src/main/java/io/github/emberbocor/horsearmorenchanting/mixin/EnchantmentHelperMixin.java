package io.github.emberbocor.horsearmorenchanting.mixin;

import io.github.emberbocor.horsearmorenchanting.HorseArmorEnchanting;
import java.util.List;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnchantmentHelper.class)
public abstract class EnchantmentHelperMixin {
    // The enchanting table tests the enchantment's category, not Enchantment#canEnchant.
    @Inject(method = "getAvailableEnchantmentResults", at = @At("RETURN"))
    private static void horsearmorenchanting$tableEnchantments(int level, ItemStack stack, boolean allowTreasure, CallbackInfoReturnable<List<EnchantmentInstance>> cir) {
        if (HorseArmorEnchanting.isHorseArmor(stack)) {
            HorseArmorEnchanting.addTableEnchantments(cir.getReturnValue(), level, allowTreasure);
        }
    }
}
