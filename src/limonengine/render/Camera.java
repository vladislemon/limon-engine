package limonengine.render;

import limonengine.events.*;
import limonengine.util.math.MathUtil;
import limonengine.util.math.vector.Vec2F;
import limonengine.util.math.vector.Vec3F;
import limonengine.util.input.InputHandler;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * slimon
 * 09.08.2014
 */
public class Camera {

    private Vec3F pos, rot, motionPos;
    private Vec2F mousePos;
    private boolean grab;

    public Camera(Vec3F pos, Vec3F rot) {
        this.pos = pos;
        this.rot = rot;
        motionPos = new Vec3F();
        mousePos = new Vec2F();
        EventHandler.register(eventListener);
    }

    private void update() {
        if(InputHandler.isMouseRightDown()) {
            if(!grab) {
                grab = true;
                mousePos.x = InputHandler.getMouseX();
                mousePos.y = InputHandler.getMouseY();
            } else {
                motionPos.x += (InputHandler.getMouseX() - mousePos.x) * 0.003f * MathUtil.sin(rot.z);
                motionPos.x += (InputHandler.getMouseY() - mousePos.y) * 0.003f * MathUtil.cos(rot.z);
                motionPos.y += (InputHandler.getMouseY() - mousePos.y) * 0.003f * -MathUtil.cos(rot.z);
                motionPos.y += (InputHandler.getMouseX() - mousePos.x) * 0.003f * MathUtil.sin(rot.z);
            }

        } else {
            grab = false;
        }

        if(InputHandler.isKeyDown(Keyboard.KEY_W)) {
            float speed = 0.01F;
            motionPos.x += speed * MathUtil.cos(rot.x);
            motionPos.x += speed * MathUtil.sin(rot.y);

            motionPos.y += speed * MathUtil.cos(rot.y);
            motionPos.y += speed * MathUtil.sin(rot.z);

            motionPos.z += speed * MathUtil.cos(rot.x);
            motionPos.z += speed * MathUtil.sin(rot.z);
        }
    }

    public void adjustPosition() {
        //GL11.glPushMatrix();
        GL11.glRotatef(rot.x, 1, 0, 0);
        GL11.glRotatef(rot.y, 0, 1, 0);
        GL11.glRotatef(rot.z, 0, 0, 1);
        GL11.glTranslatef(pos.x, pos.y, pos.z);
        //GL11.glPopMatrix();

        if(InputHandler.isMouseMiddleDown()) {
            rot.z += Mouse.getDX();
            rot.x += Mouse.getDY();
            return;
        }

        pos.add(motionPos);
        motionPos.multiply(0);
    }

    private EventListener eventListener = new EventListener() {

        private ArrayList<String> requiredEventsName = new ArrayList<String>(Arrays.asList(
                EventFrameTick.class.getName(), EventTimerTick.class.getName()));
        @Override
        public void onEvent(Event event) {
            if(event.getName().equals(EventTimerTick.class.getName())) {
                update();
            } else if(event.getName().equals(EventFrameTick.class.getName())) {
                adjustPosition();
            }
        }

        @Override
        public ArrayList<String> getRequiredEventsName() {
            return requiredEventsName;
        }
    };
}
