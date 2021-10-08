package xyz.auriium.blockdrop.runnable;

import java.util.concurrent.atomic.AtomicInteger;

public interface Recurrable {

    /**
     * Runs the recurrable with knowledge of which execution this is
     *
     * @param recursion how many times the recurrable has been ran
     */
    void run(int recursion);

    class Fake implements Runnable {

        private final Recurrable recurrable;
        private final AtomicInteger integer = new AtomicInteger(0);

        public Fake(Recurrable recurrable) {
            this.recurrable = recurrable;
        }

        @Override
        public void run() {
            int recursion = integer.incrementAndGet();

            recurrable.run(recursion);
        }
    }

}
