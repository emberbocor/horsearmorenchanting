package io.github.emberbocor.horsearmorenchanting;

import java.util.Map;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.jetbrains.annotations.Nullable;

public final class HorseArmorAnvil {
    private HorseArmorAnvil() {
    }

    public static boolean isCombination(ItemStack left, ItemStack right) {
        return HorseArmorEnchanting.isHorseArmor(left) && right.is(left.getItem());
    }

    // Vanilla AnvilMenu#createResult for two items of the same kind, without the durability part: horse armor has none.
    public static ItemStack combine(ItemStack left, ItemStack right, @Nullable String itemName, Player player, DataSlot cost) {
        ItemStack result = left.copy();
        Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(result);
        int baseCost = left.getBaseRepairCost() + right.getBaseRepairCost();
        int price = 0;
        boolean applied = false;
        boolean rejected = false;

        for (Map.Entry<Enchantment, Integer> entry : EnchantmentHelper.getEnchantments(right).entrySet()) {
            Enchantment enchantment = entry.getKey();
            int current = enchantments.getOrDefault(enchantment, 0);
            int level = entry.getValue();
            level = current == level ? level + 1 : Math.max(level, current);
            boolean allowed = enchantment.canEnchant(left) || player.getAbilities().instabuild;

            for (Enchantment other : enchantments.keySet()) {
                if (other != enchantment && !enchantment.isCompatibleWith(other)) {
                    allowed = false;
                    price++;
                }
            }

            if (!allowed) {
                rejected = true;
            } else {
                applied = true;
                level = Math.min(level, enchantment.getMaxLevel());
                enchantments.put(enchantment, level);
                price += rarityCost(enchantment) * level;
                if (left.getCount() > 1) {
                    price = 40;
                }
            }
        }

        if (rejected && !applied) {
            cost.set(0);
            return ItemStack.EMPTY;
        }

        int renameCost = 0;
        if (itemName != null && !Util.isBlank(itemName)) {
            if (!itemName.equals(left.getHoverName().getString())) {
                renameCost = 1;
                price += renameCost;
                result.setHoverName(Component.literal(itemName));
            }
        } else if (left.hasCustomHoverName()) {
            renameCost = 1;
            price += renameCost;
            result.resetHoverName();
        }

        cost.set(baseCost + price);
        if (price <= 0) {
            result = ItemStack.EMPTY;
        }

        if (renameCost == price && renameCost > 0 && cost.get() >= 40) {
            cost.set(39);
        }

        if (cost.get() >= 40 && !player.getAbilities().instabuild) {
            result = ItemStack.EMPTY;
        }

        if (!result.isEmpty()) {
            int repairCost = Math.max(result.getBaseRepairCost(), right.getBaseRepairCost());
            if (renameCost != price || renameCost == 0) {
                repairCost = AnvilMenu.calculateIncreasedRepairCost(repairCost);
            }

            result.setRepairCost(repairCost);
            EnchantmentHelper.setEnchantments(enchantments, result);
        }

        return result;
    }

    private static int rarityCost(Enchantment enchantment) {
        return switch (enchantment.getRarity()) {
            case COMMON -> 1;
            case UNCOMMON -> 2;
            case RARE -> 4;
            case VERY_RARE -> 8;
        };
    }
}
