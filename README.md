# Horse Armor Enchanting

Enchant your horse armor like your own armor, and the enchantments protect the horse that wears it.

In vanilla, horse armor cannot be enchanted at all. With Horse Armor Enchanting, leather, copper, iron, golden, diamond and netherite horse armor take the armor enchantments at the enchanting table and the anvil, with the same costs, levels and conflicts as player armor. A horse wearing the enchanted armor gets the effect: it takes less damage, hurts what attacks it, falls softer, moves faster through water, freezes water under its hooves or runs faster on soul sand.

The mod runs on the server only. Players join with a plain vanilla client.

Available for NeoForge, Forge and Fabric.

## Features

Nine enchantments can go on horse armor. Each works for the horse like it works for a player:

| Enchantment | Effect on the horse |
|---|---|
| Protection | Less damage from most sources. |
| Fire Protection | Less fire and lava damage, and the horse burns for a shorter time. |
| Blast Protection | Less explosion damage and less knockback from explosions. |
| Projectile Protection | Less damage from arrows and other projectiles. |
| Thorns | A mob or player that hits the horse may get hurt back. |
| Feather Falling | Less fall damage. |
| Depth Strider | The horse moves faster in water. |
| Frost Walker | Water under the horse turns into frosted ice while it walks on the ground, and magma blocks and campfires do not hurt it. |
| Soul Speed | The horse runs faster on soul sand and soul soil. |

- **Enchanting table.** Put horse armor in the table like any other armor. It offers Protection, Fire Protection, Blast Protection, Projectile Protection, Feather Falling and Depth Strider.
- **Anvil.** Combine horse armor with an enchanted book, or with another horse armor of the same kind that is enchanted. Thorns, Frost Walker and Soul Speed only come from books, as on boots.
- **Nothing else.** Every other enchantment is refused, among them Unbreaking, Mending, Curse of Binding, Curse of Vanishing, Respiration, Aqua Affinity and Swift Sneak.
- **No config, nothing to install on clients.**

## How it works / limits

- **Which armor.** Only the six horse armors of the game: leather, copper, iron, golden, diamond and netherite. Wolf armor and nautilus armor stay as they are in vanilla.
- **Which animals.** Horses and zombie horses wear horse armor, and the enchantments work for both. Donkeys, mules and skeleton horses cannot wear it, as in vanilla.
- **Same rules as player armor.** Enchantability of the material, level costs, the bookshelf bonus, the anvil cost and the prior work penalty, maximum levels and the conflicts between enchantments are the vanilla ones. The protection enchantments exclude each other, and Depth Strider and Frost Walker exclude each other.
- **Boots enchantments from the body slot.** Feather Falling, Depth Strider, Frost Walker and Soul Speed are made for boots. On horse armor they work from the horse's armor slot, ridden or not.
- **Riders are not protected.** The enchantments protect the horse only. When the horse lands from a big fall, the rider takes fall damage as in vanilla; the rider's own armor counts for that.
- **No durability.** Horse armor never breaks, so the durability cost of Thorns and Soul Speed does not apply.
- **Creative mode.** As with any item, a player in creative mode can put any enchantment on horse armor with the anvil.
- **No messages.** The mod never shows text on screen.

## Requirements

| | NeoForge | Forge | Fabric |
|---|---|---|---|
| Minecraft | 26.1 to 26.1.2 | 26.1 to 26.1.2 | 26.1 to 26.1.2 |
| Loader | NeoForge 26.1.0.1-beta or newer | Forge 62.0.0 or newer | Fabric Loader 0.18.4 or newer |
| Fabric API | | | 0.144.0 or newer |
| Jar | `horsearmorenchanting-neoforge-26.1-26.1.2-<version>.jar` | `horsearmorenchanting-forge-26.1-26.1.2-<version>.jar` | `horsearmorenchanting-fabric-26.1-26.1.2-<version>.jar` |

On Minecraft 26.1 to 26.1.2, NeoForge and Forge are separate loaders that do not run each other's mods, so each has its own jar. Pick the jar that matches your server's loader. On Fabric, Fabric API is needed: it loads the item tags that come with the mod.

### Client and server

Install the mod on the server only, on every loader. It adds no items, blocks, entities, commands, network messages or registries. The enchanting table, the anvil, the tooltip and the glint of the item and of the armor on the horse all work on a vanilla client, because the server works out the offers, costs and results and sends them, with the item tags and the enchanted item, to every client:

- **NeoForge:** vanilla clients and NeoForge clients without the mod can join.
- **Forge:** vanilla clients and Forge clients without the mod can join. The mod is marked as server-only, so a Forge client without it still shows the server as compatible in the server list.
- **Fabric:** vanilla clients and Fabric clients without the mod can join.

Installing it on a client as well does no harm.

In singleplayer, install it in your own game; the built-in server runs it.

## Configuration

No configuration.

## Compatibility

- **Datapacks and mods that change enchantments.** The mod adds the six horse armors to the item tags `minecraft:enchantable/armor` and `minecraft:enchantable/foot_armor`, which are the supported items of the nine enchantments. It does not replace any enchantment file. So:
  - a datapack that changes the costs, levels or effects of one of the nine enchantments changes it for horse armor too;
  - a datapack that gives one of the nine enchantments other supported items takes it away from horse armor, unless the new list still includes those tags;
  - an enchantment from a datapack or another mod that uses one of those two tags as its supported items can go on horse armor too;
  - a datapack that replaces both tags without horse armor removes horse armor from them; the enchanting table then still takes horse armor but offers nothing. Replacing only `minecraft:enchantable/armor` with its vanilla list keeps horse armor in it, because that list includes `minecraft:enchantable/foot_armor`.
- **Boots enchantments.** An enchantment that only works in the feet slot also works from the body armor slot of a mob, for example wolf armor or nautilus armor enchanted in creative mode. In survival, only horse armor can carry such enchantments, because only horse armor is added to the tags.
- **Mods that change the anvil** may conflict. Combining two horse armors uses the mod's own copy of the vanilla anvil rules, because vanilla only combines two items of the same kind that have durability.

## Building

```
./gradlew build
```

The jars are written to `neoforge/build/libs/`, `forge/build/libs/` and `fabric/build/libs/`.

## License

MIT.
