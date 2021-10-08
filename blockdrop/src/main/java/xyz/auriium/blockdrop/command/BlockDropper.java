package xyz.auriium.blockdrop.command;

import org.bukkit.entity.Player;
import xyz.auriium.blockdrop.Config;
import xyz.auriium.blockdrop.runnable.RcDistributor;
import xyz.auriium.blockdrop.runnable.RcStage;
import xyz.auriium.blockdrop.runnable.Recurrable;
import xyz.auriium.openmineplatform.api.scheduling.Scheduler;
import xyz.auriium.openmineplatform.api.scheduling.SchedulerTask;

import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicReference;

public class BlockDropper implements Dropper {

    private final AtomicReference<SchedulerTask> running = new AtomicReference<>();

    private final Scheduler scheduler;
    private final Config config;

    public BlockDropper(Scheduler scheduler, Config config) {
        this.scheduler = scheduler;
        this.config = config;
    }


    @Override
    public void start(Player player) {

        NavigableMap<Integer, Recurrable> map = new TreeMap<>();

        int increment = 1;

        for (Config.Stage stage : config.stages()) {
            map.put(increment, new RcStage(player, stage.blockTypes(), stage.radius(), stage.saturation()));

            increment = increment + stage.duration() + 1;
        }

        running.set(scheduler.runRepeated(
                new Recurrable.Fake(
                        new RcDistributor(map)
                )
        , 1, false));
    }

    @Override
    public void stop() {
        running.getAndUpdate(old -> {
            old.cancel();

            return null;
        });
    }

    @Override
    public boolean isRunning() {
        return running.get() == null;
    }
}
