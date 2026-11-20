package io.github.emberbocor.horsearmorenchanting;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.util.StringUtil;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
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
        ItemEnchantments.Mutable enchantments = new ItemEnchantments.Mutable(EnchantmentHelper.getEnchantmentsForCrafting(result));
        long baseCost = (long) left.getOrDefault(DataComponents.REPAIR_COST, 0) + right.getOrDefault(DataComponents.REPAIR_COST, 0);
        int price = 0;
        boolean applied = false;
        boolean rejected = false;

        for (Object2IntMap.Entry<Holder<Enchantment>> entry : EnchantmentHelper.getEnchantmentsForCrafting(right).entrySet()) {
            Holder<Enchantment> holder = entry.getKey();
            Enchantment enchantment = holder.value();
            int current = enchantments.getLevel(holder);
            int level = entry.getIntValue();
            level = current == level ? level + 1 : Math.max(level, current);
            boolean allowed = left.is(enchantment.definition().supportedItems()) || player.getAbilities().instabuild;

            for (Holder<Enchantment> other : enchantments.keySet()) {
                if (!other.equals(holder) && !Enchantment.areCompatible(holder, other)) {
                    allowed = false;
                    price++;
                }
            }

            if (!allowed) {
                rejected = true;
            } else {
                applied = true;
                level = Math.min(level, enchantment.getMaxLevel());
                enchantments.set(holder, level);
                price += enchantment.getAnvilCost() * level;
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
        if (itemName != null && !StringUtil.isBlank(itemName)) {
            if (!itemName.equals(left.getHoverName().getString())) {
                renameCost = 1;
                price += renameCost;
                result.set(DataComponents.CUSTOM_NAME, Component.literal(itemName));
            }
        } else if (left.has(DataComponents.CUSTOM_NAME)) {
            renameCost = 1;
            price += renameCost;
            result.remove(DataComponents.CUSTOM_NAME);
        }

        cost.set((int) Mth.clamp(baseCost + price, 0L, Integer.MAX_VALUE));
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
            int repairCost = Math.max(result.getOrDefault(DataComponents.REPAIR_COST, 0), right.getOrDefault(DataComponents.REPAIR_COST, 0));
            if (renameCost != price || renameCost == 0) {
                repairCost = AnvilMenu.calculateIncreasedRepairCost(repairCost);
            }

            result.set(DataComponents.REPAIR_COST, repairCost);
            EnchantmentHelper.setEnchantments(result, enchantments.toImmutable());
        }

        return result;
    }
}
