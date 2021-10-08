package xyz.auriium.blockdrop.runnable;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import xyz.auriium.blockdrop.random.RlThreadLocal;

import java.util.List;
import java.util.Random;

public class RcStage implements Recurrable {

    private final Player player;

    private final List<Material> ordered;
    private final double radius;
    private final int saturation;

    public RcStage(Player player, List<Material> ordered, double radius, int saturation) {
        this.player = player;
        this.ordered = ordered;
        this.radius = radius;
        this.saturation = saturation;
    }

    @Override
    public void run(int recursion) {
        var blocks = new RlThreadLocal<>(ordered, saturation).randomized();

        Location location = player.getLocation();

        for (Material mat : blocks) {

            //shitcode
            double diameter = radius * 2;
            Random rand = new Random();
            double x = location.getX() + (rand.nextDouble() * diameter - radius);
            double z = location.getZ() + (rand.nextDouble() * diameter - radius);

            double yDD = 0;

            //to the head

            if (location.getWorld().getEnvironment().equals(World.Environment.NETHER)) {
                while (location.getWorld().getBlockAt(location.add(0, yDD, 0)).getType().equals(Material.AIR)) {
                    yDD++;
                }
            }

            Location spawn = new Location(location.getWorld(), x, location.getY() + yDD - 1, z);

            player.getWorld().spawnFallingBlock(spawn, mat.createBlockData()).setDropItem(false);
        }
    }
}
