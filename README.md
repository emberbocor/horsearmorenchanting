# Horse Armor Enchanting

Enchant your horse armor like your own armor, and the enchantments protect the horse that wears it.

In vanilla, horse armor cannot be enchanted at all. With Horse Armor Enchanting, leather, iron, golden and diamond horse armor take the armor enchantments at the enchanting table and the anvil, with the same costs, levels and conflicts as player armor. A horse wearing the enchanted armor gets the effect: it takes less damage, hurts what attacks it, falls softer, moves faster through water, freezes water under its hooves or runs faster on soul sand.

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

- **Which armor.** Only the four horse armors of the game: leather, iron, golden and diamond.
- **Which animals.** Only horses wear horse armor. Donkeys, mules, zombie horses and skeleton horses cannot, as in vanilla.
- **Same rules as player armor.** Enchantability of the matching armor material, level costs, the bookshelf bonus, the anvil cost and the prior work penalty, maximum levels and the conflicts between enchantments are the vanilla ones. The protection enchantments exclude each other, and Depth Strider and Frost Walker exclude each other.
- **Boots enchantments from the body slot.** Feather Falling, Depth Strider, Frost Walker and Soul Speed are made for boots. On horse armor they work from the horse's armor slot, ridden or not.
- **Soul Speed on a ridden horse.** A horse ridden by a player without the mod is still slowed down by soul sand and shows no soul particles, because that player's game moves the horse. Without a rider, on soul soil, or when the rider has the mod installed too, Soul Speed works in full.
- **Riders are not protected.** The enchantments protect the horse only. When the horse lands from a big fall, the rider takes fall damage as in vanilla; the rider's own armor counts for that.
- **No durability.** Horse armor never breaks, so the durability cost of Thorns and Soul Speed does not apply.
- **No glint on the horse.** The armor item shows the enchantment glint and its enchantments in the tooltip, but the armor drawn on the horse has no glint. The vanilla client never draws a glint on horse armor, and the mod adds nothing on the client.
- **Creative mode.** As with any item, a player in creative mode can put any enchantment on horse armor with the anvil.
- **No messages.** The mod never shows text on screen.

## Requirements

| | NeoForge | Forge | Fabric |
|---|---|---|---|
| Minecraft | 1.20.1 | 1.20.1 | 1.20 or 1.20.1 |
| Loader | NeoForge 47.1.5 or newer | Forge 47.1.0 or newer | Fabric Loader 0.14.21 or newer |
| Jar | `horsearmorenchanting-forge-1.20.1-<version>.jar` | `horsearmorenchanting-forge-1.20.1-<version>.jar` | `horsearmorenchanting-fabric-1.20-1.20.1-<version>.jar` |

On Minecraft 1.20.1, NeoForge and Forge use the same Forge jar. Pick that jar for either loader, or the Fabric jar for Fabric. Fabric API is not needed.

### Client and server

Install the mod on the server only, on every loader. It adds no items, blocks, entities, commands, network messages or registries. The enchanting table, the anvil, the tooltip and the glint of the item all work on a vanilla client, because the server works out the offers, costs and results and sends them, with the enchanted item, to every client:

- **NeoForge:** vanilla clients and NeoForge clients without the mod can join.
- **Forge:** vanilla clients and Forge clients without the mod can join. The mod is marked as server-only, so a Forge client without it still shows the server as compatible in the server list.
- **Fabric:** vanilla clients and Fabric clients without the mod can join.

Installing it on a client as well does no harm.

In singleplayer, install it in your own game; the built-in server runs it.

## Configuration

No configuration.

## Compatibility

- **Enchantments from other mods.** Horse armor takes an enchantment when boots take it by its category (armor or boots enchantments). An enchantment from another mod in one of those categories can go on horse armor too; one that checks the item in its own way does not.
- **Mods that change the anvil** may conflict. Combining two horse armors uses the mod's own copy of the vanilla anvil rules, because vanilla only combines two items of the same kind that have durability.

## Building

```
./gradlew build
```

The jars are written to `forge/build/libs/` and `fabric/build/libs/`.

## License

MIT.
