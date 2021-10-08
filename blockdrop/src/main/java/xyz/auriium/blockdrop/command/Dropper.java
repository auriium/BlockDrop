package xyz.auriium.blockdrop.command;

import org.bukkit.entity.Player;

public interface Dropper {

    void start(Player player);
    void stop();

    boolean isRunning();

}
