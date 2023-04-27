package me.lord.posc.gui;

import me.lord.posc.utilities.TextUtil;
import net.kyori.adventure.text.Component;

public class MarketGUI extends ChestGUI {
    public MarketGUI() {

    }

    @Override
    public int getRows() {
        return 4;
    }

    @Override
    public Component getTitle() {
        return TextUtil.c("Market");
    }

    public class Category extends ChestGUI {
        @Override
        public int getRows() {
            return 4;
        }

        @Override
        public Component getTitle() {
            return null;
        }
    }
}
