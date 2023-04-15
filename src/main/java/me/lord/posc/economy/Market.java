package me.lord.posc.economy;

import me.lord.posc.Posc;
import me.lord.posc.data.DataManager;
import org.bukkit.Material;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Market implements Serializable {
    @Serial
    private static final long serialVersionUID = 4133920117858777568L;

    private final HashMap<Material, MarketEntry> marketEntryMap;

    public Market() {
        int i = 0;
        ArrayList<Material> materials = new ArrayList<>();
        for (Material material : Material.values()) {
            if (material.isItem() && !material.isLegacy()) {
                materials.add(material);
                i++;
            }
        }
        marketEntryMap = new HashMap<>((int) Math.round(i * 1.5d));
        for (Material material : materials) {
            marketEntryMap.put(material, new MarketEntry());
        }
    }

    public static Market getCurrent() {
        return DataManager.getGlobal().getMarket();
    }

    public MarketEntry getEntry(Material material) {
        return marketEntryMap.get(material);
    }
}
