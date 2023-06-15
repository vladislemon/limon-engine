package limonengine.events;

import java.util.ArrayList;

/**
 * slimon
 * 19.06.2014
 */
public interface EventListener {

    public void onEvent(Event event);

    public ArrayList<String> getRequiredEventsName();
}
