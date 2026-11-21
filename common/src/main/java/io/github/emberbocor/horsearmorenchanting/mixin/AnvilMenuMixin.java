package io.github.emberbocor.horsearmorenchanting.mixin;

import io.github.emberbocor.horsearmorenchanting.HorseArmorAnvil;
import io.github.emberbocor.horsearmorenchanting.HorseArmorEnchanting;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.ItemCombinerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnvilMenu.class)
public abstract class AnvilMenuMixin extends ItemCombinerMenu {
    @Shadow
    @Nullable
    private String itemName;
    @Shadow
    @Final
    private DataSlot cost;

    private AnvilMenuMixin(@Nullable MenuType<?> type, int containerId, Inventory playerInventory, ContainerLevelAccess access) {
        super(type, containerId, playerInventory, access);
    }

    // The first isDamageableItem() call is the repair check, reached only with a second item, after the book check.
    @Inject(
            method = "createResult",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;isDamageableItem()Z", ordinal = 0),
            cancellable = true
    )
    private void horsearmorenchanting$combineHorseArmor(CallbackInfo ci) {
        ItemStack left = this.inputSlots.getItem(0);
        ItemStack right = this.inputSlots.getItem(1);
        if (HorseArmorAnvil.isCombination(left, right)) {
            this.resultSlots.setItem(0, HorseArmorAnvil.combine(left, right, this.itemName, this.player, this.cost));
            this.sendAllDataToRemote();
            ci.cancel();
        }
    }

    // A client without the mod rejects every enchantment on horse armor and computes no result and cost 0, so send the server's values even when unchanged.
    @Inject(method = "createResult", at = @At("RETURN"))
    private void horsearmorenchanting$resendResult(CallbackInfo ci) {
        if (HorseArmorEnchanting.isHorseArmor(this.inputSlots.getItem(0))) {
            this.sendAllDataToRemote();
        }
    }
}
