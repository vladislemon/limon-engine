package limonengine.events;

import java.util.ArrayList;
import java.util.List;

/**
 * slimon
 * 26.04.2014
 */
public class EventHandler {

    private static List<EventListener> listeners = new ArrayList<EventListener>();

    public static synchronized void register(EventListener listener) {
        if(!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    public static synchronized void unregister(EventListener listener) {
        if(listeners.contains(listener)) {
            listeners.remove(listener);
        }
    }

    public static synchronized void handleEvent(Event event) {
        if(event != null) {
            for(EventListener listener : listeners) {
                if(listener.getRequiredEventsName().contains(event.getName())) {
                    listener.onEvent(event);
                }
            }
        }
    }
}
