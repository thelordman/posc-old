package io.github.thelordman.costrength.utilities;

import io.github.thelordman.costrength.CoStrength;
import io.github.thelordman.costrength.quests.Advancement;
import io.github.thelordman.costrength.quests.AdvancementManager;
import io.github.thelordman.costrength.quests.trigger.TriggerType;
import org.bukkit.Material;

public class AdvancementHandler {

    public static void init() {
        Advancement advancement = new Advancement(CoStrength.get(), "quests/root");

        advancement.setDisplay(x -> {
            x.setTitle(Methods.cStr("&9Quests!"));
            x.setDescription(Methods.cStr("&7Complete quests for in-game rewards."));
            x.setBackground(Material.GOLD_BLOCK);
            x.setIcon(Material.WRITABLE_BOOK);
        });

        advancement.addCriteria("cobblestone", TriggerType.TICK, tick -> tick.equals(1));
        AdvancementManager manager = new AdvancementManager(CoStrength.get());
        manager.register(advancement);
        manager.createAll(true);
    }
}
