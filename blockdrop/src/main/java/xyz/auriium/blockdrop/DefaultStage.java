package xyz.auriium.blockdrop;

import org.bukkit.Material;

import java.util.List;

public class DefaultStage implements Config.Stage {

    private final List<Material> materials;

    public DefaultStage(List<Material> materials) {
        this.materials = materials;
    }

    @Override
    public int duration() {
        return 1200;
    }

    @Override
    public int saturation() {
        return 1;
    }

    @Override
    public int radius() {
        return 30;
    }

    @Override
    public List<Material> blockTypes() {
        return materials;
    }
}
