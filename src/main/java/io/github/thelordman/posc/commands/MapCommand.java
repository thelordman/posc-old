package io.github.thelordman.posc.commands;

import com.fastasyncworldedit.core.FaweAPI;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.world.block.BlockTypes;
import io.github.thelordman.posc.mining.MineHandler;
import io.github.thelordman.posc.utilities.CommandName;
import io.github.thelordman.posc.utilities.Methods;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

@CommandName("map")
public class MapCommand implements TabExecutor {
    private static final Region[] map = {new CuboidRegion(BlockVector3.at(14, -41, -14), BlockVector3.at(70, 0, -70)), new CuboidRegion(BlockVector3.at(14, -41, -13), BlockVector3.at(70, 0, 12)), new CuboidRegion(BlockVector3.at(13, -41, -14), BlockVector3.at(-12, 0, -70))};

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!Methods.checkCommandPermission(sender, 2)) return true;

        MineHandler.resetMine((byte) 2, (Player) sender);
        try (EditSession editSession = WorldEdit.getInstance().newEditSessionBuilder().world(FaweAPI.getWorld("world")).changeSetNull().fastMode(true).build()) {
            for (int i = 0; i < 3; i++) {
                editSession.setBlocks(map[i], BlockTypes.AIR);
            }
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return Collections.emptyList();
    }
}
