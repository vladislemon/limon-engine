package limonengine.events;

/**
 * slimon
 * 09.08.2014
 */
public class EventInputMouse extends Event {

    private int eventButton;
    private boolean eventButtonState;

    public EventInputMouse(int eventButton, boolean eventButtonState) {
        super();
        this.eventButton = eventButton;
        this.eventButtonState = eventButtonState;
    }

    public int getEventButton() {
        return eventButton;
    }

    public boolean getEventButtonState() {
        return eventButtonState;
    }
}
