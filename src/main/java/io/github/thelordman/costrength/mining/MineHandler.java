package io.github.thelordman.costrength.mining;

import com.fastasyncworldedit.core.FaweAPI;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.function.pattern.RandomPattern;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.world.block.BlockType;
import com.sk89q.worldedit.world.block.BlockTypes;
import io.github.thelordman.costrength.economy.EconomyManager;
import io.github.thelordman.costrength.economy.LevelHandler;
import io.github.thelordman.costrength.items.ItemManager;
import io.github.thelordman.costrength.scoreboard.ScoreboardHandler;
import io.github.thelordman.costrength.utilities.Methods;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.javatuples.Triplet;

import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class MineHandler {
    private static final Region[] mine = {new CuboidRegion(BlockVector3.at(14, -42, -14), BlockVector3.at(70, -63, -70)), new CuboidRegion(BlockVector3.at(14, -42, -13), BlockVector3.at(70, -63, 12)), new CuboidRegion(BlockVector3.at(13, -42, -14), BlockVector3.at(-12, -63, -70))};
    private static final RandomPattern minePattern = new RandomPattern();

    private static final HashMap<Player, Triplet<Material, Long, Integer>> lastBlockData = new HashMap<>();

    public static void registerRandomPattern() {
        minePattern.add(BlockTypes.STONE, 75);
        minePattern.add(BlockTypes.COAL_ORE, 15);
        minePattern.add(BlockTypes.IRON_ORE, 5);
        minePattern.add(BlockTypes.LAPIS_ORE, 3.5);
        minePattern.add(BlockTypes.DIAMOND_ORE, 1);
        minePattern.add(BlockTypes.EMERALD_ORE, 0.5);
    }

    public static void resetMine(byte type, Player player) {
        String executor = player == null ? "Console" : player.getDisplayName();
        String broadcast = switch (type) {
            default -> "\n&6&lCoStrength &8| &cAn internal error has occurred, please contact a staff member if you see this message.";
            case 0 -> "\n&6&lCoStrength &8| &fMine is being refilled by " + executor + "&f.\n";
            case 1 -> "\n&6&lCoStrength &8| &fA beacon has been found by " + executor + "&f.\n&6&lCoStrength &8| &fMine is being refilled.\n";
        };
        Bukkit.broadcastMessage(Methods.cStr(broadcast));

        try (EditSession editSession = WorldEdit.getInstance().newEditSessionBuilder().world(FaweAPI.getWorld("world")).changeSetNull().fastMode(true).build()) {
            for (Region region : mine) {
                editSession.setBlocks(region, minePattern);
            }
            int[] xyz = {ThreadLocalRandom.current().nextInt(-12, 70), ThreadLocalRandom.current().nextInt(-63, -42), ThreadLocalRandom.current().nextInt(-70, 12)};
            while (xyz[0] < 14 && xyz[2] > -14) {
                xyz[0] = ThreadLocalRandom.current().nextInt(-12, 70);
                xyz[2] = ThreadLocalRandom.current().nextInt(-70, 12);
            }
            editSession.setBlock(xyz[0], xyz[1], xyz[2], BlockTypes.BEACON);
        }

        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.getLocation().getY() < -41 && !Methods.inSpawn(p.getLocation())) {
                p.teleportAsync(new Location(Bukkit.getWorld("world"), p.getLocation().getX(), p.getWorld().getHighestBlockYAt(p.getLocation()) + 1, p.getLocation().getZ()));
            }
        }
    }

    public static void mineBlock(Player player, Block block) {
        Material material = block.getType();
        ItemStack item = player.getInventory().getItemInMainHand();

        double[] rewards = processBlock(player, material, true);
        if (ItemManager.getCELevel(item, ItemManager.pickaxeEnchantments[0]) != 0) {
            if (Math.random() < ((double) ItemManager.getCELevel(item, ItemManager.pickaxeEnchantments[0])) / 50 && material.toString().contains("ORE")) {
                for (int y = -5; y < 5; y++) {
                    for (int x = -5; x < 5; x++) {
                        for (int z = -5; z < 5; z++) {
                            Block b = block.getRelative(x, y, z);
                            if (Math.sqrt((x * x) + (y * y) + (z * z)) <= 5 && b.getType().equals(block.getType())) {
                                if (b.getX() > 13 | b.getZ() < -13) {
                                    for (int i = 0; i < 2; i++) {
                                        rewards[i] += processBlock(player, b.getType(), false)[i];
                                    }
                                    b.setType(Material.AIR);
                                }
                            }
                        }
                    }
                }
            }
        }
        if (ItemManager.getCELevel(item, ItemManager.pickaxeEnchantments[1]) != 0) {
            if (Math.random() < ((double) ItemManager.getCELevel(item, ItemManager.pickaxeEnchantments[1])) / 20) {
                for (int y = -3; y < 3; y++) {
                    for (int x = -3; x < 3; x++) {
                        for (int z = -3; z < 3; z++) {
                            Block b = block.getRelative(x, y, z);
                            if (Math.sqrt((x * x) + (y * y) + (z * z)) <= 3 && !b.getType().equals(Material.BEDROCK) && !b.getType().equals(Material.BEACON) && !b.getType().equals(Material.DIRT) && !b.getType().equals(Material.GRASS_BLOCK)) {
                                if (b.getX() > 13 | b.getZ() < -13) {
                                    for (int i = 0; i < 2; i++) {
                                        rewards[i] += processBlock(player, b.getType(), false)[i];
                                    }
                                    b.setType(Material.AIR);
                                }
                            }
                        }
                    }
                }
            }
        }
        if (ItemManager.getCELevel(item, ItemManager.pickaxeEnchantments[2]) != 0) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100, ItemManager.getCELevel(item, ItemManager.pickaxeEnchantments[2]) - 1));
        }
        if (ItemManager.getCELevel(item, ItemManager.pickaxeEnchantments[5]) != 0) {
            if (Math.random() < ((double) ItemManager.getCELevel(item, ItemManager.pickaxeEnchantments[5])) / 200) {
                for (int x = -13; x < 13; x++) {
                    for (int z = -13; z < 13; z++) {
                        Block b = block.getRelative(x, 0, z);
                        if (Math.sqrt((x * x) + 1 + (z * z)) <= 13 && !b.getType().equals(Material.BEDROCK) && !b.getType().equals(Material.BEACON) && !b.getType().equals(Material.DIRT) && !b.getType().equals(Material.GRASS_BLOCK)) {
                            if (b.getX() > 13 | b.getZ() < -13) {
                                for (int i = 0; i < 2; i++) {
                                    rewards[i] += processBlock(player, b.getType(), false)[i];
                                }
                                b.setType(Material.AIR);
                            }
                        }
                    }
                }
            }
        }
        if (ItemManager.getCELevel(item, ItemManager.pickaxeEnchantments[6]) != 0) {
            if (Math.random() < ((double) ItemManager.getCELevel(item, ItemManager.pickaxeEnchantments[6])) / 20) {
                for (int i = block.getY(); i < -41; i++) {
                    Block b = Objects.requireNonNull(Bukkit.getWorld("world")).getBlockAt(block.getX(), i, block.getZ());
                    for (int integer = 0; integer < 2; integer++) {
                        rewards[integer] += processBlock(player, b.getType(), false)[integer] * 2;
                    }
                    b.setType(Material.AIR);
                }
                for (int i = block.getY(); i > -64; i--) {
                    Block b = Objects.requireNonNull(Bukkit.getWorld("world")).getBlockAt(block.getX(), i, block.getZ());
                    for (int integer = 0; integer < 2; integer++) {
                        rewards[integer] += processBlock(player, b.getType(), false)[integer] * 2;
                    }
                    b.setType(Material.AIR);
                }
                rewards[2]++;
                rewards[3]++;
            }
        }
        if (ItemManager.getCELevel(item, ItemManager.pickaxeEnchantments[7]) != 0) ItemManager.updateCounterAmount(item, true);

        player.sendActionBar(Methods.cStr("&f+$" + Methods.rStr(rewards[0]) + " &7(" + Methods.rStr(rewards[2]) + "x) &8| &f+" + Methods.rStr(rewards[1]) + "xp &7(" + Methods.rStr(rewards[3]) + "x) &8| &6Streak&7: &f" + Methods.rStr((double) lastBlockData.get(player).getValue2())));
        EconomyManager.setBalance(player.getUniqueId(), EconomyManager.getBalance(player.getUniqueId()) + rewards[0]);
        EconomyManager.setXp(player.getUniqueId(), EconomyManager.getXp(player.getUniqueId()) + rewards[1]);
        LevelHandler.xp(player);
        ScoreboardHandler.updateBoard(player);
    }

    private static double[] processBlock(Player player, Material block, boolean streak) {
        ItemStack item = player.getInventory().getItemInMainHand();

        double value = switch (block) {
            case STONE -> 2;
            case COAL_ORE -> 5;
            case IRON_ORE -> 10;
            case LAPIS_ORE -> 12.5;
            case DIAMOND_ORE -> 15;
            case EMERALD_ORE -> 25;
            case BEACON -> EconomyManager.getBalance(player.getUniqueId()) / 100 + 1000;
            default -> 0;
        };

        double multi = 0;
        if (streak) {
            Material m = !lastBlockData.containsKey(player) ? Material.AIR : lastBlockData.get(player).getValue0();
            long l = !lastBlockData.containsKey(player) ? 0 : lastBlockData.get(player).getValue1();
            lastBlockData.put(player, new Triplet<>(block, System.currentTimeMillis(),
                    !lastBlockData.containsKey(player) | m != block | 5000 < System.currentTimeMillis() - l
                            ? 0 : lastBlockData.get(player).getValue2() + 1));
            multi = lastBlockData.containsKey(player)
                    ? (float) (block.equals(Material.STONE)
                    ? lastBlockData.get(player).getValue2() / 2
                    : lastBlockData.get(player).getValue2()) / 100 : 0;
        }

        if (block.equals(Material.DIAMOND_ORE) | block.equals(Material.EMERALD_ORE)) {
            multi += 0.5d * item.getEnchantmentLevel(Enchantment.SILK_TOUCH);
        }
        if (item.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS) != 0 && Math.random() <= ((double) item.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS)) / 20d) {
            multi++;
        }
        if (ItemManager.getCELevel(item, ItemManager.pickaxeEnchantments[7]) != 0) {
            multi += ItemManager.getCounterMulti(item);
        }

        double moneyMulti = 1 + multi, xpMulti = 1 + multi + ItemManager.getCELevel(item, ItemManager.pickaxeEnchantments[4]);
        double money = value * moneyMulti, xp = value * xpMulti;

        if (block.equals(Material.BEACON)) xp = LevelHandler.xpRequirement(player.getUniqueId()) / 3;

        return new double[]{money, xp, moneyMulti, xpMulti};
    }
}