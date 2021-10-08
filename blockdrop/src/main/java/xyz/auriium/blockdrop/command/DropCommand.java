package xyz.auriium.blockdrop.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.auriium.openmineplatform.api.plugin.ReloadablePluginState;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class DropCommand implements TabExecutor {

    private final static List<String> EMPTY = new ArrayList<>();

    private final ReloadablePluginState state;
    private final Dropper dropper;

    public DropCommand(ReloadablePluginState state, Dropper dropper) {
        this.state = state;
        this.dropper = dropper;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        Queue<String> queue = new ArrayDeque<>(List.of(strings));

        if (queue.isEmpty()) {
            commandSender.sendMessage(color("&cError: You must enter a valid subcommand. Valid subcommands include: start, stop, reload"));
            return true;
        }

        switch (queue.remove()) {
            case "start" -> {
                if (dropper.isRunning()) {
                    commandSender.sendMessage(color("&cError: The block drop is already running. Please stop it and try again."));
                    return true;
                }

                if (!(commandSender instanceof Player player)) {
                    commandSender.sendMessage(color("&cError: You must be a player to start the drop!"));
                    return true;
                }

                dropper.start(player);

                commandSender.sendMessage(color("&aSuccess: Started the block drop!"));

            }
            case "stop" -> {
                if (dropper.isRunning()) {
                    commandSender.sendMessage(color("&cError: The block drop is already stopped. Please start it and try again."));
                    return true;
                }

                dropper.stop();

                commandSender.sendMessage(color("&aSuccess: Stopped the block drop!"));
            }
            case "reload" -> {
                state.reload();
                commandSender.sendMessage(color("&aSuccess: Reloaded successfully!"));
            }
            default -> {
                commandSender.sendMessage(color("&cError: You must enter a valid subcommand. Valid subcommands include: start, stop, reload"));
            }
        }


        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (strings.length == 1) {
            return List.of("start", "stop", "reload");
        }

        return EMPTY;
    }

    String color(String string) {
        return ChatColor.translateAlternateColorCodes('&',string);
    }
}
