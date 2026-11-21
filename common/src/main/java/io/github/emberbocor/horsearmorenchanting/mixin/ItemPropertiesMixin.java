package io.github.emberbocor.horsearmorenchanting.mixin;

import io.github.emberbocor.horsearmorenchanting.HorseArmorEnchanting;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.ArmorMaterial;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.Properties.class)
public abstract class ItemPropertiesMixin {
    @Shadow
    @Nullable
    private ResourceKey<Item> id;
    @Unique
    private int horsearmorenchanting$enchantmentValue;

    @Shadow
    public abstract Item.Properties enchantable(int enchantmentValue);

    @Inject(method = "horseArmor", at = @At("RETURN"))
    private void horsearmorenchanting$rememberMaterial(ArmorMaterial material, CallbackInfoReturnable<Item.Properties> cir) {
        this.horsearmorenchanting$enchantmentValue = material.enchantmentValue();
    }

    // The item id is only known when the item is built, after horseArmor.
    @Inject(method = "buildAndValidateComponents", at = @At("HEAD"))
    private void horsearmorenchanting$addEnchantable(Component itemName, Identifier itemModel, CallbackInfoReturnable<DataComponentMap> cir) {
        if (this.horsearmorenchanting$enchantmentValue > 0 && HorseArmorEnchanting.getsEnchantability(this.id)) {
            this.enchantable(this.horsearmorenchanting$enchantmentValue);
        }
    }
}
