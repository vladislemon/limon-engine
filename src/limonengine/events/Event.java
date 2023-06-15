package limonengine.events;

/**
 * slimon
 * 19.06.2014
 */
public abstract class Event {

    private final int id;

    protected Event() {
        this.id = 0;//Events.assignId(this);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return this.getClass().getName();
    }
}
