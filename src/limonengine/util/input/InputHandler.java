package limonengine.util.input;

import limonengine.Engine;
import limonengine.events.*;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.util.ArrayList;
import java.util.Arrays;

public class InputHandler {
	
	private static boolean[] keyboard;
	private static boolean[] mouse;
    private static int[] mousePos;

    public static void init() {
        if(Engine.isOpenGLInitialized()) {
            keyboard = new boolean[Keyboard.getKeyCount()*4];
            mouse = new boolean[Mouse.getButtonCount()];
            mousePos = new int[] {0, 0};
        } else {
            return;
        }
        EventHandler.register(eventListener);
    }
	
	public static boolean isKeyDown(int key) {
		return keyboard[key];
	}
	
	public static boolean isMouseLeftDown() {
		return mouse[0];
	}
	
	public static boolean isMouseRightDown() {
		return mouse[1];
	}
	
	public static boolean isMouseMiddleDown() {
		return mouse[2];
	}

    public static int getMouseX() {
        return mousePos[0];
    }

    public static int getMouseY() {
        return mousePos[1];
    }

    public static void handleInput() {
		while(Keyboard.next()) {
			if(Keyboard.getEventKey() == -1) {
				continue;
			}
			keyboard[Keyboard.getEventKey()] = Keyboard.getEventKeyState();
			EventHandler.handleEvent(new EventInputKeyboard(Keyboard.getEventKey(), Keyboard.getEventKeyState()));
		}
		while(Mouse.next()) {
			if(Mouse.getEventButton() == -1) {
				continue;
			}
			mouse[Mouse.getEventButton()] = Mouse.getEventButtonState();
            EventHandler.handleEvent(new EventInputMouse(Mouse.getEventButton(), Mouse.getEventButtonState()));
		}
        mousePos[0] = Mouse.getX();
        mousePos[1] = Mouse.getY();
	}

    private static EventListener eventListener = new EventListener() {

        private ArrayList<String> requiredEventsName = new ArrayList<String>(Arrays.asList(EventFrameTick.class.getName()));

        @Override
        public void onEvent(Event event) {
            if(Engine.isOpenGLInitialized()) {
                handleInput();
            }
        }

        @Override
        public ArrayList<String> getRequiredEventsName() {
            return requiredEventsName;
        }
    };
}
