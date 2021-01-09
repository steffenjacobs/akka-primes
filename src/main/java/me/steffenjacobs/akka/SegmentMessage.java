package me.steffenjacobs.akka;

public class SegmentMessage {
    private final long start;
    private final long end;

    public SegmentMessage(long start, long end) {
        this.start = start;
        this.end = end;
    }

    public long getEnd() {
        return end;
    }

    public long getStart() {
        return start;
    }
}
