package io.github.thelordman.costrength.utilities;

import io.github.thelordman.costrength.CoStrength;
import io.github.thelordman.costrength.quests.Advancement;
import io.github.thelordman.costrength.quests.AdvancementManager;
import io.github.thelordman.costrength.quests.display.BackgroundType;
import io.github.thelordman.costrength.quests.trigger.TriggerType;
import org.bukkit.Material;

public class AdvancementHandler {

    public static void init() {
        AdvancementManager manager = new AdvancementManager(CoStrength.get());

        Advancement root = new Advancement(CoStrength.get(), "quests/root");
        root.setDisplay(x -> {
            x.setTitle(Methods.cStr("Quests!"));
            x.setDescription(Methods.cStr("&7Complete quests for in-game rewards."));
            x.setBackground(BackgroundType.GOLD_BLOCK);
            x.setIcon(Material.WRITABLE_BOOK);
        });
        root.addCriteria("crit", TriggerType.TICK, trigger -> {
            trigger.equals(1);
        });
        manager.register(root);

        Advancement mine_50_blocks = new Advancement(CoStrength.get(), "quests/mine_50_blocks");
        mine_50_blocks.setDisplay(x -> {
            x.setTitle(Methods.cStr("Mine 50 Blocks"));
            x.setDescription(Methods.cStr("&7Mine 50 blocks for &e$250&7!"));
            x.setIcon(Material.GRAVEL);
        });
        mine_50_blocks.addCriteria("crit", TriggerType.IMPOSSIBLE, trigger -> {
            trigger.equals(null);
        });
        mine_50_blocks.setParent(root.getKey());
        manager.register(mine_50_blocks);

        Advancement mine_250_blocks = new Advancement(CoStrength.get(), "quests/mine_250_blocks");
        mine_250_blocks.setDisplay(x -> {
            x.setTitle(Methods.cStr("Mine 250 Blocks"));
            x.setDescription(Methods.cStr("&7Mine 250 blocks for &e$1,000&7!"));
            x.setIcon(Material.MOSSY_COBBLESTONE);
        });
        mine_250_blocks.addCriteria("crit", TriggerType.IMPOSSIBLE, trigger -> {
            trigger.equals(null);
        });
        mine_250_blocks.setParent(mine_50_blocks.getKey());
        manager.register(mine_250_blocks);

        Advancement mine_800_blocks = new Advancement(CoStrength.get(), "quests/mine_800_blocks");
        mine_800_blocks.setDisplay(x -> {
            x.setTitle(Methods.cStr("Mine 800 Blocks"));
            x.setDescription(Methods.cStr("&7Mine 800 blocks for &e$1,750&7!"));
            x.setIcon(Material.COBBLESTONE);
        });
        mine_800_blocks.addCriteria("crit", TriggerType.IMPOSSIBLE, trigger -> {
            trigger.equals(null);
        });
        mine_800_blocks.setParent(mine_250_blocks.getKey());
        manager.register(mine_800_blocks);

        Advancement mine_1500_blocks = new Advancement(CoStrength.get(), "quests/mine_1500_blocks");
        mine_1500_blocks.setDisplay(x -> {
            x.setTitle(Methods.cStr("Mine 1,500 Blocks"));
            x.setDescription(Methods.cStr("&7Mine 1,500 blocks for &e$2,500&7!"));
            x.setIcon(Material.STONE);
        });
        mine_1500_blocks.addCriteria("crit", TriggerType.IMPOSSIBLE, trigger -> {
            trigger.equals(null);
        });
        mine_1500_blocks.setParent(mine_800_blocks.getKey());
        manager.register(mine_1500_blocks);

        manager.createAll(true);
    }
}
