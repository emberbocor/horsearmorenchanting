package io.github.emberbocor.horsearmorenchanting;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.item.enchantment.ThornsEnchantment;

public final class HorseArmorEnchanting {
    public static final String MODID = "horsearmorenchanting";

    private HorseArmorEnchanting() {
    }

    public static boolean isHorseArmor(ItemStack stack) {
        return enchantmentValue(stack.getItem()) > 0;
    }

    // The enchantability of the matching armor material, as horse armor has from 1.21 on.
    public static int enchantmentValue(Item item) {
        if (item == Items.LEATHER_HORSE_ARMOR) {
            return 15;
        } else if (item == Items.IRON_HORSE_ARMOR) {
            return 9;
        } else if (item == Items.GOLDEN_HORSE_ARMOR) {
            return 25;
        } else if (item == Items.DIAMOND_HORSE_ARMOR) {
            return 10;
        }
        return 0;
    }

    // Horse armor takes the enchantments of boots: the armor and feet armor categories, and Thorns, which takes any armor in the anvil.
    public static boolean acceptsAtTable(Enchantment enchantment) {
        return enchantment.category == EnchantmentCategory.ARMOR || enchantment.category == EnchantmentCategory.ARMOR_FEET;
    }

    public static boolean accepts(Enchantment enchantment) {
        return acceptsAtTable(enchantment) || enchantment instanceof ThornsEnchantment;
    }

    // Vanilla EnchantmentHelper#getAvailableEnchantmentResults with the categories of boots, in registry order.
    public static void addTableEnchantments(List<EnchantmentInstance> results, int level, boolean allowTreasure) {
        List<EnchantmentInstance> found = new ArrayList<>(results);
        results.clear();
        Enchantment enchantment;
        for (int id = 0; (enchantment = Enchantment.byId(id)) != null; id++) {
            boolean listed = false;
            for (EnchantmentInstance instance : found) {
                if (instance.enchantment == enchantment) {
                    results.add(instance);
                    listed = true;
                }
            }
            if (!listed && acceptsAtTable(enchantment) && (!enchantment.isTreasureOnly() || allowTreasure) && enchantment.isDiscoverable()) {
                for (int i = enchantment.getMaxLevel(); i > enchantment.getMinLevel() - 1; --i) {
                    if (level >= enchantment.getMinCost(i) && level <= enchantment.getMaxCost(i)) {
                        results.add(new EnchantmentInstance(enchantment, i));
                        break;
                    }
                }
            }
        }
    }

    // Frost Walker and Soul Speed only look at the feet; a horse wears its armor in the chest slot.
    public static boolean worksFromChest(EquipmentSlot[] slots, LivingEntity entity) {
        boolean feet = false;
        for (EquipmentSlot slot : slots) {
            if (slot == EquipmentSlot.CHEST) {
                return false;
            }
            feet |= slot == EquipmentSlot.FEET;
        }
        return feet && entity instanceof AbstractHorse && isHorseArmor(entity.getItemBySlot(EquipmentSlot.CHEST));
    }
}
