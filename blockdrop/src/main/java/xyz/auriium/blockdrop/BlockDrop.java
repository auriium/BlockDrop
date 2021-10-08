package xyz.auriium.blockdrop;

import xyz.auriium.openmineplatform.api.plugin.PluginRepresentation;
import xyz.auriium.openmineplatform.spigot.SpigotBootstrap;

public class BlockDrop extends SpigotBootstrap {
    @Override
    public PluginRepresentation representation() {
        return new BlockDropRepresentation();
    }
}
