package me.steffenjacobs.akka;

import java.util.List;

public class PrimeResult {
    private final List<Long> results;

    public PrimeResult(List<Long> results){
        this.results = results;
    }

    public List<Long> getResults() {
        return results;
    }
}
