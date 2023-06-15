package limonengine.events;

/**
 * slimon
 * 25.06.2014
 */
public class EventFrameTick extends Event {

    private long frame;

    public EventFrameTick(long frame) {
        super();
        this.frame = frame;
    }

    public long getFrame() {
        return frame;
    }
}
