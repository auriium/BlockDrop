package xyz.auriium.blockdrop;

import xyz.auriium.openmineplatform.api.HookData;
import xyz.auriium.openmineplatform.api.Platform;
import xyz.auriium.openmineplatform.api.PlatformProjectIdentity;
import xyz.auriium.openmineplatform.api.plugin.PluginRepresentation;
import xyz.auriium.openmineplatform.api.plugin.ReloadablePlugin;
import xyz.auriium.openmineplatform.api.plugin.ReloadablePluginState;
import xyz.auriium.openmineplatform.spigot.JavaPluginTelescope;

public class BlockDropRepresentation implements PluginRepresentation {
    @Override
    public PlatformProjectIdentity getIdentity() {
        return new PlatformProjectIdentity("blockdrop", "Auriium", "1.0.0-SNAPSHOT");
    }

    @Override
    public HookData getInsertionData() {
        return HookData.make();
    }

    @Override
    public ReloadablePlugin supply(Platform platform, ReloadablePluginState reloadablePluginState) {
        return new BlockDropPlugin(platform.telescope(JavaPluginTelescope.EXCEPTIONAL), reloadablePluginState, platform.scheduler());
    }
}
