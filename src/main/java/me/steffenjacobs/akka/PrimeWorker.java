package me.steffenjacobs.akka;

import akka.actor.UntypedActor;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class PrimeWorker extends UntypedActor {
    @Override
    public void onReceive(Object msg) {
        // receive partition from PrimeMaster
        if (msg instanceof SegmentMessage) {
            List<Long> primes = new ArrayList<>();
            for (long i = ((SegmentMessage) msg).getStart(); i < ((SegmentMessage) msg).getEnd(); i++) {
                // add to result, if element is a prime number
                if (isPrime(i)) {
                    primes.add(i);
                }
            }
            // send resulting subset of primes to PrimeMaster
            this.getSender().tell(new PrimeResult(primes), this.getSelf());
        }
    }

    /**
     * @return whether n is a prime number
     */
    private boolean isPrime(long n) {
        return BigInteger.valueOf(n).isProbablePrime(100);
    }
}
