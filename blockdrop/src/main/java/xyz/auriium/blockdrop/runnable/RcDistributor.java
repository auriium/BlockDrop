package xyz.auriium.blockdrop.runnable;

import java.util.NavigableMap;

public class RcDistributor implements Recurrable {

    private final NavigableMap<Integer, Recurrable> map;
    private final Recurrable fallback;

    public RcDistributor(NavigableMap<Integer, Recurrable> map, RcFailSwitch fallback) {
        this.map = map;
        this.fallback = fallback;
    }

    public RcDistributor(NavigableMap<Integer, Recurrable> map) {
        this.map = map;
        this.fallback = new RcFailSwitch();
    }

    @Override
    public void run(int recursion) {
        var entry = map.floorEntry(recursion);

        if (entry != null && entry.getValue() == null) {
            entry = map.lowerEntry(recursion);
        }

        if (entry != null) {
            entry.getValue().run(recursion);
            return;
        }

        fallback.run(recursion);
    }
}
