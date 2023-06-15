package limonengine;

import limonengine.events.*;
import limonengine.render.Render;
import limonengine.util.TimeUtil;
import limonengine.util.input.InputHandler;
import org.lwjgl.input.Keyboard;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * slimon
 * 09.08.2014
 */
public class DebugController {

    public DebugController() {
        EventHandler.register(eventListener);
    }

    private EventListener eventListener = new EventListener() {

        private ArrayList<String> requiredEventsName = new ArrayList<String>(Arrays.asList(
                EventFrameTick.class.getName(), EventTimerTick.class.getName(), EventInputKeyboard.class.getName()));
        @Override
        public void onEvent(Event event) {
            if(event.getName().equals(EventTimerTick.class.getName())) {

            } else if(event.getName().equals(EventFrameTick.class.getName())) {

            } else if(event.getName().equals(EventInputKeyboard.class.getName())) {
                if(((EventInputKeyboard)event).getEventKey() == Keyboard.KEY_F2 &&
                        ((EventInputKeyboard)event).getEventKeyState()) {
                    File file = new File("screenshots/" + TimeUtil.getTime("dd^MM^YY_hh^mm^ss") + ".png");
                    try {
                        file.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Render.saveScreenShot(file, "PNG");
                }
            }
        }

        @Override
        public ArrayList<String> getRequiredEventsName() {
            return requiredEventsName;
        }
    };
}
