package limonengine.events;

/**
 * slimon
 * 09.08.2014
 */
public class EventInputKeyboard extends Event {

    private int eventKey;
    private boolean eventKeyState;

    public EventInputKeyboard(int eventKey, boolean eventKeyState) {
        super();
        this.eventKey = eventKey;
        this.eventKeyState = eventKeyState;
    }

    public int getEventKey() {
        return eventKey;
    }

    public boolean getEventKeyState() {
        return eventKeyState;
    }
}
