package io.github.emberbocor.horsearmorenchanting;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;

public final class HorseArmorEnchanting {
    public static final String MODID = "horsearmorenchanting";
    public static final TagKey<Item> HORSE_ARMOR = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MODID, "horse_armor"));

    private HorseArmorEnchanting() {
    }

    public static boolean isHorseArmor(ItemStack stack) {
        return stack.is(HORSE_ARMOR);
    }

    public static boolean worksFromBody(Enchantment enchantment, EquipmentSlot slot) {
        return slot == EquipmentSlot.BODY && enchantment.definition().slots().contains(EquipmentSlotGroup.FEET);
    }
}
