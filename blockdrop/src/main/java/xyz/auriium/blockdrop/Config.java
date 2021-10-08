package xyz.auriium.blockdrop;

import org.bukkit.Material;
import space.arim.dazzleconf.annote.ConfComments;
import space.arim.dazzleconf.annote.ConfDefault;
import space.arim.dazzleconf.annote.SubSection;

import java.util.ArrayList;
import java.util.List;

public interface Config {

    @ConfDefault.DefaultObject("defaultStages")
    List<@SubSection Stage> stages();

    interface Stage {

        @ConfDefault.DefaultString("60")
        @ConfComments("The duration of each stage in ticks")
        int duration();

        @ConfDefault.DefaultInteger(4)
        @ConfComments("The amount of blocks to drop per interval")
        int saturation();

        @ConfDefault.DefaultInteger(20)
        @ConfComments("The radius of which inside blocks should be dropped")
        int radius();

        @ConfComments("The different types of blocks to rain down on the player")
        @ConfDefault.DefaultObject("defaultBlocks")
        List<Material> blockTypes();

    }

    static List<Stage> defaultStages() {
        return List.of(new DefaultStage(List.of(Material.DIRT)), new DefaultStage(List.of(Material.STONE)));
    }

    static List<Material> defaultBlocks() {
        return new ArrayList<>();
    }

}
