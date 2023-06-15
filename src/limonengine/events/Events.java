package limonengine.events;

import java.util.ArrayList;
import java.util.List;

/**
 * slimon
 * 25.06.2014
 */
public class Events {

    private static List<String> events = new ArrayList<String>();
    private static int lastId = -1;

    public static int assignId(Event event) {
        String name = event.getClass().getName();
        if(!events.contains(name)) {
            lastId++;
            events.add(lastId, name);
            return lastId;
        }
        return events.indexOf(name);
    }

    public static String getEventNameById(int id) {
        if(id >= 0 && id < events.size()) {
            return events.get(id);
        }
        return null;
    }
}
