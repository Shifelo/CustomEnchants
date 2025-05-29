# CustomEnchants (Spigot 1.21.4)

⚠️ WARNING: This is a sample code project meant for demonstration and knowledge sharing. Not all features, checks, and protections required for a real server plugin are included. Not ‘production-ready’. Use at your own risk!

## Description

CustomEnchants is a fairly simple Spigot plugin demonstrating how one might do the implementation of custom enchantments, custom books, and anvil logic using the Spigot API with Java for Minecraft version 1.21.4. It’s more of a proof of knowledge than an actual thing to be used as-is in any real server without further development and a security review.


## Features
- Customized Enchantment Books: configurable names, descriptions (multi-language ready).
- All the enchantments can be applied to compatible tools and weapons via an anvil.
- Combine books of the same enchantment and level in an anvil to create higher level books (if the enchantment supports it).
- Some enchantments, such as AutoSmelt, Replenish, Decapitate may have levels limited to only one.
- Compatibility logic for enchantments like Excavator + AutoSmelt and VeinMiner + AutoSmelt
- Command `/givecustombook <enchant> [level] [player]` to give books to yourself or other players.

## Usage
- Move the compiled `.jar` to the `plugins` directory of your server and restart the server to apply changes.
- Run `/givecustombook <enchant> [level] [player]` to give a player an enchantment book with custom levels.
- Combine them into a book or apply direct to a tool through an anvil.

## Example
```bash
/givecustombook excavator 2 Steve
```
Gives Excavator II book to the player Steve.


## Limitations & Warnings
**This code is example code only.**
- No permissions system implemented, no anti-duplication, and no anti-exploit beyond null/basic type checks.
- As well, there is no support for plugin reloads, persistence, or advanced error handling.
- The plugin does not cover all edge cases (for instance, lore tampering, or stackable items or plugin reloads).
- Not performance tested nor tested for compatibility with other plugins.
- **Do NOT use on a production server without a full code review and additional protections!**

## License
This code is provided as it is for educational purposes. You may modify it, use it to learn from it.


---

**Author:** Shifelo

> _This project is a sample to demonstrate knowledge of Spigot plugin development, custom enchantments, and Java best practices. It is not a finished product._
