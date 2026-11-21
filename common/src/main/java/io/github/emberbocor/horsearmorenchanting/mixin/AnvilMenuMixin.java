package io.github.emberbocor.horsearmorenchanting.mixin;

import io.github.emberbocor.horsearmorenchanting.HorseArmorAnvil;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.ItemCombinerMenu;
import net.minecraft.world.inventory.ItemCombinerMenuSlotDefinition;
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
    @Shadow
    private boolean onlyRenaming;

    private AnvilMenuMixin(@Nullable MenuType<?> type, int containerId, Inventory inventory, ContainerLevelAccess access, ItemCombinerMenuSlotDefinition slotDefinition) {
        super(type, containerId, inventory, access, slotDefinition);
    }

    // The first ItemStack#is(Item) call is the check where vanilla rejects two items of the same kind without durability.
    // NeoForge moves the vanilla logic of createResult into createResultInternal.
    @Inject(
            method = {"createResult", "createResultInternal"},
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z", ordinal = 0),
            cancellable = true
    )
    private void horsearmorenchanting$combineHorseArmor(CallbackInfo ci) {
        ItemStack left = this.inputSlots.getItem(0);
        ItemStack right = this.inputSlots.getItem(1);
        if (HorseArmorAnvil.isCombination(left, right)) {
            HorseArmorAnvil.Result result = HorseArmorAnvil.combine(left, right, this.itemName, this.player);
            this.resultSlots.setItem(0, result.stack());
            this.cost.set(result.cost());
            this.onlyRenaming = result.onlyRenaming();
            // A client without the mod computes no result and cost 0 for this pair, so send the server's values even when unchanged.
            this.sendAllDataToRemote();
            ci.cancel();
        }
    }
}
