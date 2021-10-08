package xyz.auriium.blockdrop.random;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RlThreadLocal<T> implements RandomList<T> {

    private final List<T> list;
    private final int saturation;

    public RlThreadLocal(List<T> list, int saturation) {
        this.list = list;
        this.saturation = saturation;
    }

    @Override
    public List<T> randomized() {

        List<T> newList = new ArrayList<>();

        for (int x = 0; x < saturation; x++) {
            newList.add(list.get(ThreadLocalRandom.current().nextInt(list.size())));
        }

        return newList;

    }
}
