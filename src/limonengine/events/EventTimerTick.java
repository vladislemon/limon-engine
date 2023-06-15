package limonengine.events;

/**
 * slimon
 * 25.06.2014
 */
public class EventTimerTick extends Event {

    private long tick;

    public EventTimerTick(long tick) {
        super();
        this.tick = tick;
    }

    public long getTick() {
        return tick;
    }
}
