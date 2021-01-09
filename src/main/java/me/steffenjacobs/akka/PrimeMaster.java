package me.steffenjacobs.akka;

import akka.actor.Props;
import akka.actor.UntypedActor;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PrimeMaster extends UntypedActor {
        private static final int WORKER_COUNT = 12;
        private final List<Long> primeResults = new ArrayList<>();
        private int resultCount = 0;

    @Override
    public void onReceive(Object msg) throws Exception {

        //message from PrimeApp received
        if (msg instanceof SegmentMessage) {

            // partition input domain
            SegmentMessage sm = (SegmentMessage) msg;
            int segLength = (int) ((sm.getEnd() - sm.getStart()) / WORKER_COUNT);

            for (int cnt = 0; cnt < WORKER_COUNT; cnt++) {
                long rightBound = sm.getEnd();

                if (cnt != WORKER_COUNT - 1) {
                    rightBound = sm.getStart() + (cnt + 1) * segLength;
                }

                // send partition to a new worker
                getContext().actorOf(Props.create(PrimeWorker.class))
                        .tell(new SegmentMessage(sm.getStart() + cnt * segLength,
                                rightBound), getSelf());
            }
        }

        // Message from PrimeWorker received
        else if (msg instanceof PrimeResult) {
            primeResults.addAll(((PrimeResult) msg).getResults());

            // check, if all subsets were received
            if (++resultCount >= WORKER_COUNT) {
                Collections.sort(primeResults);

                //Write to output.txt
                final PrintWriter p = new PrintWriter("./output.txt");
                p.println("Size: " + primeResults.size());
                primeResults.forEach(prime -> p.print(prime + ", "));
                p.close();

                System.out.println("Done.");
                this.getContext().system().terminate();
            }
        }
    }
}
