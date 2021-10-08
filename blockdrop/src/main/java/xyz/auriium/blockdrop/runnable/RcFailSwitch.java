package xyz.auriium.blockdrop.runnable;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

public class RcFailSwitch implements Recurrable {

    private final AtomicBoolean hasFailed = new AtomicBoolean(false);
    private final Supplier<RuntimeException> exceptionSupplier;

    public RcFailSwitch(Supplier<RuntimeException> exceptionSupplier) {
        this.exceptionSupplier = exceptionSupplier;
    }

    public RcFailSwitch() {
        this.exceptionSupplier = () -> new IllegalStateException("FailSwitch called!");
    }

    @Override
    public void run(int recursion) {

        if (hasFailed.compareAndSet(false, true)) {
            throw exceptionSupplier.get();
        }

    }
}
