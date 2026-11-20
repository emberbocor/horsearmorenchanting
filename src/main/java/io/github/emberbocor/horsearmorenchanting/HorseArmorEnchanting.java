package io.github.emberbocor.horsearmorenchanting;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;

@Mod(HorseArmorEnchanting.MODID)
public class HorseArmorEnchanting {
    public static final String MODID = "horsearmorenchanting";
    public static final Logger LOGGER = LogUtils.getLogger();

    public HorseArmorEnchanting(IEventBus modEventBus, ModContainer modContainer) {
    }
}
