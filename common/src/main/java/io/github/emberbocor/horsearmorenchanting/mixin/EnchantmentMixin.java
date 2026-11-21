package io.github.emberbocor.horsearmorenchanting.mixin;

import io.github.emberbocor.horsearmorenchanting.HorseArmorEnchanting;
import java.util.Map;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Enchantment.class)
public abstract class EnchantmentMixin {
    @Shadow
    @Final
    private EquipmentSlot[] slots;

    @Inject(method = "canEnchant", at = @At("HEAD"), cancellable = true)
    private void horsearmorenchanting$canEnchant(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (HorseArmorEnchanting.isHorseArmor(stack) && HorseArmorEnchanting.accepts((Enchantment) (Object) this)) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "getSlotItems", at = @At("RETURN"))
    private void horsearmorenchanting$getSlotItems(LivingEntity entity, CallbackInfoReturnable<Map<EquipmentSlot, ItemStack>> cir) {
        if (HorseArmorEnchanting.worksFromChest(this.slots, entity)) {
            cir.getReturnValue().put(EquipmentSlot.CHEST, entity.getItemBySlot(EquipmentSlot.CHEST));
        }
    }
}
