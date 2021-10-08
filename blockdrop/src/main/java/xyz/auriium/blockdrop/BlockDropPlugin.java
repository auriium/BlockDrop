package xyz.auriium.blockdrop;

import org.bukkit.plugin.java.JavaPlugin;
import space.arim.dazzleconf.ConfigurationOptions;
import space.arim.dazzleconf.error.InvalidConfigException;
import space.arim.dazzleconf.ext.snakeyaml.SnakeYamlConfigurationFactory;
import space.arim.dazzleconf.ext.snakeyaml.SnakeYamlOptions;
import space.arim.dazzleconf.helper.ConfigurationHelper;
import xyz.auriium.blockdrop.command.BlockDropper;
import xyz.auriium.blockdrop.command.DropCommand;
import xyz.auriium.blockdrop.command.Dropper;
import xyz.auriium.openmineplatform.api.plugin.ReloadablePlugin;
import xyz.auriium.openmineplatform.api.plugin.ReloadablePluginState;
import xyz.auriium.openmineplatform.api.scheduling.Scheduler;
import xyz.auriium.openmineplatform.spigot.JavaPluginTelescope;

import java.io.IOException;
import java.util.Objects;

public class BlockDropPlugin implements ReloadablePlugin {

    private final JavaPlugin plugin;
    private final ReloadablePluginState state;
    private final Scheduler scheduler;

    public BlockDropPlugin(JavaPlugin plugin, ReloadablePluginState state, Scheduler scheduler) {
        this.plugin = plugin;
        this.state = state;
        this.scheduler = scheduler;
    }

    @Override
    public void onStart() {
        Config config;

        try {
            config = new ConfigurationHelper<>(
                    plugin.getDataFolder().toPath(),
                    "config.yml",
                    SnakeYamlConfigurationFactory.create(Config.class, ConfigurationOptions.defaults(), new SnakeYamlOptions.Builder().build()))

                    .reloadConfigData();
        } catch (IOException | InvalidConfigException e) {
            throw new IllegalStateException("Error on plugin startup: unable to load config.yml!");
        }

        Dropper dropper = new BlockDropper(scheduler, config);
        DropCommand command = new DropCommand(state, dropper);

        var pluginCommand = Objects.requireNonNull(plugin.getCommand("blockdrop"));

        pluginCommand.setExecutor(command);
        pluginCommand.setTabCompleter(command);
    }

    @Override
    public void onStop() {

    }
}
