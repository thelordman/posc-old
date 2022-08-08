package io.github.thelordman.posc.utilities.data;

import org.bukkit.Location;

import java.io.Serial;
import java.io.Serializable;

public class GlobalData implements Serializable {
    @Serial
    private static final long serialVersionUID = 4731763762640700228L;

    public int highID = 0;
    public int allPlayers = 0;
    public Location jailLocation;
}