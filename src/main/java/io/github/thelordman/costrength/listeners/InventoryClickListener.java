package io.github.thelordman.costrength.listeners;

import io.github.thelordman.costrength.CoStrength;
import io.github.thelordman.costrength.economy.EconomyManager;
import io.github.thelordman.costrength.items.ItemManager;
import io.github.thelordman.costrength.scoreboard.ScoreboardHandler;
import io.github.thelordman.costrength.guis.GUIHandler;
import io.github.thelordman.costrength.utilities.Methods;
import io.github.thelordman.costrength.utilities.data.Data;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;

public class InventoryClickListener implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!event.getView().getTitle().equals("Food Shop") && !event.getView().getTitle().equals("Kitchen") && !event.getView().getTitle().equals("Shop") && !event.getView().getTitle().equals("Block Shop") && !event.getView().getTitle().equals("Tool Menu") && !event.getView().getTitle().equals("Enchantment Menu")) return;
        if (event.getCurrentItem() == null) return;
        event.setCancelled(true);
        Player player = (Player) event.getWhoClicked();
        ItemStack itemInHand = event.getWhoClicked().getInventory().getItemInMainHand();

        double m = 0d;
        int i = event.getClick().isRightClick() ? 64 : 1;
        ItemStack item = null;
        String message = null;
        Enchantment enchant = null;
        byte CE = -1;
        if (event.getView().getTitle().equals("Food Shop")) {
            switch (event.getCurrentItem().getType()) {
                case BREAD:
                    m = event.getClick().isRightClick() ? 640d : 10d;
                    item = GUIHandler.quickItem(Material.BREAD, Methods.cStr("&fFresh Bread"), i, Methods.cStr("&6Restores &f2.5 hunger &6and &f3 saturation&6."));
                    message = Methods.cStr("&6Successfully bought &f" + i + " Fresh Bread&6.");
                    break;
                case COOKED_CHICKEN:
                    m = event.getClick().isRightClick() ? 3200d : 50d;
                    item = GUIHandler.quickItem(Material.COOKED_CHICKEN, Methods.cStr("&cHot Wings"), i, Methods.cStr("&6Restores &f3 hunger &6and &f4.5 saturation&6."), Methods.cStr("&6Has a &f10% chance &6of giving &fHaste +1&6."));
                    message = Methods.cStr("&6Successfully bought &f" + i + " &cHot Wings&6.");
                    break;
                case COOKED_BEEF:
                    m = event.getClick().isRightClick() ? 6400d : 100d;
                    item = GUIHandler.quickItem(Material.COOKED_BEEF, Methods.cStr("&4Well Done Steak"), i, Methods.cStr("&6Restores &f4 hunger &6and &f6.5 saturation&6."));
                    message = Methods.cStr("&6Successfully bought &f" + i + " &4Well Done Steak&6.");
                    break;
                case HONEY_BOTTLE:
                    m = event.getClick().isRightClick() ? 64000d : 1000d;
                    item = GUIHandler.quickItem(Material.HONEY_BOTTLE, Methods.cStr("&6Beer"), i, Methods.cStr("&6Restores &f3 hunger &6and &f0.5 saturation&6."), Methods.cStr("&6Can be drunk even when full to restore saturation."), Methods.cStr("&6Stats can be modified from the kitchen menu."));
                    message = Methods.cStr("&6Successfully bought &f" + i + " &4Beer&6.");
                    break;
                case RABBIT_STEW:
                    m = event.getClick().isRightClick() ? 320000d : 5000d;
                    item = GUIHandler.quickItem(Material.RABBIT_STEW, Methods.cStr("&3Thick Stew"), i, Methods.cStr("&6Restores &f20 hunger &6and &f20 saturation&6."));
                    message = Methods.cStr("&6Successfully bought &f" + i + " &3Thick Stew&6.");
                    break;
                case CAULDRON:
                    GUIHandler.openGUI(Data.GUIs[1], player);
                    player.sendMessage(Methods.cStr("&6Entered kitchen menu."));
                    return;
                case BARRIER:
                    player.sendMessage(Methods.cStr("&cYour level isn't high enough."));
                    return;
                default:
                    return;
            }
        }
        else if (event.getView().getTitle().equals("Kitchen")) {
            switch (event.getCurrentItem().getType()) {
                case MELON:
                    m = event.getClick().isRightClick() ? 32000d : 500d;
                    item = GUIHandler.quickItem(Material.MELON, Methods.cStr("&2Melon"), i, Methods.cStr("&6Restores &f+2 hunger &6after drinking."), "", Methods.cStr("&6Usage&7: &fCraft this with &6Beer&f."));
                    message = Methods.cStr("&6Successfully bought &f" + i + " &2Melon&6.");
                    break;
                case SWEET_BERRIES:
                    m = event.getClick().isRightClick() ? 64000d : 1000d;
                    item = GUIHandler.quickItem(Material.SWEET_BERRIES, Methods.cStr("&cBerries"), i, Methods.cStr("&6Restores &f+7 saturation &6after drinking."), "", Methods.cStr("&6Usage&7: &fCraft this with &6Beer&f."));
                    message = Methods.cStr("&6Successfully bought &f" + i + " &cBerries&6.");
                    break;
                case SUGAR:
                    m = event.getClick().isRightClick() ? 128000d : 2000d;
                    item = GUIHandler.quickItem(Material.SUGAR, Methods.cStr("&fSugar"), i, Methods.cStr("&6Gives &fspeed 1 for 20 seconds &6after drinking."), "", Methods.cStr("&6Usage&7: &fCraft this with &6Beer&f."));
                    message = Methods.cStr("&6Successfully bought &f" + i + " Sugar&6.");
                    break;
                case HONEY_BOTTLE:
                    m = event.getClick().isRightClick() ? 256000d : 4000d;
                    item = GUIHandler.quickItem(Material.HONEY_BOTTLE, Methods.cStr("&eIrn Bru"), i, Methods.cStr("&6Gives &fstrength 1 for 20 seconds &6after drinking."), "", Methods.cStr("&6Usage&7: &fCraft this with &6Beer&f."));
                    message = Methods.cStr("&6Successfully bought &f" + i + " &eIrn Bru&6.");
                    break;
                case BARRIER:
                    player.sendMessage(Methods.cStr("&cYour level isn't high enough."));
                    return;
                default:
                    return;
            }
        }
        else if (event.getView().getTitle().equals("Shop")) {
            switch (event.getCurrentItem().getType()) {
                case GRASS_BLOCK:
                    GUIHandler.openGUI(Data.GUIs[3], player);
                    player.sendMessage(Methods.cStr("&6Entered block shop."));
                    return;
                default:
                    return;
            }
        }
        else if (event.getView().getTitle().equals("Block Shop")) {
            if (event.getCurrentItem() == null) return;
            m = event.getCurrentItem().getType() == Material.OBSIDIAN | event.getCurrentItem().getType() == Material.COBWEB ? 1000d : 100d;
            m *= i;
            item = new ItemStack(event.getCurrentItem().getType(), i);
        }
        else if (event.getView().getTitle().equals("Tool Menu")) {
            switch (event.getCurrentItem().getType()) {
                case ENCHANTED_BOOK -> {
                    GUIHandler.openGUI(Data.GUIs[5], player);
                    player.sendMessage(Methods.cStr("&6Entered enchantment menu."));
                    return;
                }
                case BARRIER -> {
                    player.sendMessage(Methods.cStr("&cThis menu is coming soon."));
                    return;
                }
                default -> {
                    if (event.getCurrentItem().getType().equals(Material.AIR) | event.getCurrentItem().getType().equals(Material.WHITE_STAINED_GLASS_PANE))
                        return;
                    if (event.getCurrentItem().getLore().contains(Methods.cStr("&6&lMAX LEVEL"))) {
                        player.sendMessage(Methods.cStr("&cThat upgrade is maxed."));
                        return;
                    }
                    m = ItemManager.getMaterialPrice(event.getCurrentItem().getType());
                    if (EconomyManager.getBalance(player.getUniqueId()) < m) {
                        Methods.errorMessage("insufficientFunds", player);
                        return;
                    }

                    itemInHand.setType(event.getCurrentItem().getType());
                    ItemMeta meta = itemInHand.getItemMeta();
                    if (itemInHand.getType().name().contains("GOLDEN")) {
                        switch (event.getCurrentItem().getType()) {
                            case GOLDEN_HELMET -> {
                                meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.randomUUID(), "generic.armor", 4, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD));
                                meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(UUID.randomUUID(), "generic.armor_toughness", 4, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD));
                                meta.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE, new AttributeModifier(UUID.randomUUID(), "generic.knockback_resistance", 0.1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD));
                            }
                            case GOLDEN_CHESTPLATE -> {
                                meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.randomUUID(), "generic.armor", 9, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));
                                meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(UUID.randomUUID(), "generic.armor_toughness", 4, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));
                                meta.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE, new AttributeModifier(UUID.randomUUID(), "generic.knockback_resistance", 0.1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));
                            }
                            case GOLDEN_LEGGINGS -> {
                                meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.randomUUID(), "generic.armor", 7, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.LEGS));
                                meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(UUID.randomUUID(), "generic.armor_toughness", 4, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.LEGS));
                                meta.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE, new AttributeModifier(UUID.randomUUID(), "generic.knockback_resistance", 0.1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.LEGS));
                            }
                            case GOLDEN_BOOTS -> {
                                meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.randomUUID(), "generic.armor", 4, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET));
                                meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(UUID.randomUUID(), "generic.armor_toughness", 4, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET));
                                meta.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE, new AttributeModifier(UUID.randomUUID(), "generic.knockback_resistance", 0.1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET));
                            }
                            case GOLDEN_SWORD -> {
                                meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier(UUID.randomUUID(), "generic.attack_damage", 8, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
                                meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier(UUID.randomUUID(), "generic.attack_speed", -2.6, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
                                meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                            }
                        }
                        meta.setDisplayName(Methods.cStr("&6" + Methods.getMaterialName(itemInHand.getType())));
                    }
                    if (itemInHand.getType() == Material.NETHERITE_PICKAXE) {
                        meta.addAttributeModifier(Attribute.GENERIC_MAX_HEALTH, new AttributeModifier(UUID.randomUUID(), "generic.max_health", 4, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
                    }
                    itemInHand.setItemMeta(meta);

                    player.playSound(player, Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1f);
                    EconomyManager.setBalance(player.getUniqueId(), EconomyManager.getBalance(player.getUniqueId()) - m);
                    player.sendMessage(Methods.cStr("&6Successfully upgraded the material of your item."));
                    GUIHandler.openGUI(Data.GUIs[4], player);
                    ScoreboardHandler.updateBoard(player);

                    return;
                }
            }
        }
        else if (event.getView().getTitle().equals("Enchantment Menu")) {
            switch (event.getCurrentItem().getType()) {
                case ENCHANTED_BOOK:
                    switch (event.getCurrentItem().getItemMeta().getPersistentDataContainer().get(new NamespacedKey(CoStrength.instance, "gui-item"), PersistentDataType.BYTE)) {
                        case 0 -> enchant = Enchantment.DIG_SPEED;
                        case 1 -> enchant = Enchantment.SILK_TOUCH;
                        case 2 -> enchant = Enchantment.LOOT_BONUS_BLOCKS;
                        case 3 -> enchant = Enchantment.DAMAGE_ALL;
                        case 4 -> enchant = Enchantment.KNOCKBACK;
                        case 5 -> enchant = Enchantment.FIRE_ASPECT;
                        case 6 -> enchant = Enchantment.SWEEPING_EDGE;
                        case 7 -> enchant = Enchantment.PROTECTION_ENVIRONMENTAL;
                        case 8 -> enchant = Enchantment.PROTECTION_FIRE;
                        case 9 -> enchant = Enchantment.PROTECTION_PROJECTILE;
                        case 10 -> enchant = Enchantment.PROTECTION_EXPLOSIONS;
                        case 11 -> enchant = Enchantment.THORNS;
                    }
                    m = ItemManager.getEnchantmentPrice(itemInHand, enchant);
                    message = Methods.cStr("&6Successfully upgraded tool.");
                    break;
                case GOLD_ORE, POISONOUS_POTATO:
                    CE = 0;
                    break;
                case TNT, PLAYER_HEAD:
                    CE = 1;
                    break;
                case GOLDEN_BOOTS, SHIELD:
                    CE = 2;
                    break;
                case GOLDEN_PICKAXE, ENDER_EYE:
                    if (event.getCurrentItem().getItemMeta().isUnbreakable()) return;
                    CE = 3;
                    break;
                case EXPERIENCE_BOTTLE, BREAD:
                    CE = 4;
                    break;
                case GOLDEN_AXE, REDSTONE:
                    CE = 5;
                    break;
                case LIGHTNING_ROD, WITHER_ROSE:
                    CE = 6;
                    break;
                case TARGET, MILK_BUCKET:
                    CE = 7;
                    break;
                case BARRIER:
                    player.sendMessage(Methods.cStr("&cYour level isn't high enough."));
                    return;
                default:
                    return;
            }
        }
        if (CE != -1) {
            m = ItemManager.getCEPrice(itemInHand, itemInHand.getType().toString().contains("PICKAXE") ? ItemManager.pickaxeEnchantments[CE] : ItemManager.swordEnchantments[CE]);
            message = Methods.cStr("&6Successfully upgraded tool.");
        }
        if (event.getCurrentItem().getLore().contains(Methods.cStr("&6&lMAX LEVEL"))) {
            player.sendMessage(Methods.cStr("&cThat enchantment is maxed."));
            return;
        }
        if (EconomyManager.getBalance(player.getUniqueId()) < m) {
            Methods.errorMessage("insufficientFunds", player);
            return;
        }

        if (enchant != null) ItemManager.setEnchant(itemInHand, enchant, (byte) (itemInHand.getEnchantmentLevel(enchant) + 1));
        if (CE != -1) ItemManager.setCELevel(itemInHand, itemInHand.getType().toString().contains("PICKAXE") ? ItemManager.pickaxeEnchantments[CE] : ItemManager.swordEnchantments[CE], (byte) (ItemManager.getCELevel(itemInHand, itemInHand.getType().toString().contains("PICKAXE") ? ItemManager.pickaxeEnchantments[CE] : ItemManager.swordEnchantments[CE]) + 1));
        player.playSound(player, Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1f);
        EconomyManager.setBalance(player.getUniqueId(), EconomyManager.getBalance(player.getUniqueId()) - m);
        if (item != null) player.getInventory().addItem(item);
        if (message != null) player.sendMessage(message);

        if (event.getView().getTitle().equals("Enchantment Menu")) GUIHandler.openGUI(Data.GUIs[5], player);

        ScoreboardHandler.updateBoard(player);
    }
}