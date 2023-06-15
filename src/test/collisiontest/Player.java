package test.collisiontest;

import limonengine.events.*;
import limonengine.util.math.MathUtil;
import limonengine.util.input.InputHandler;
import limonengine.util.math.vector.Vec2D;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

/**
 * ===
 * Created by slimon on 30.07.2015.
 */
public class Player {

    private Vec2D pos;
    private Model2D model;
    private double rot, moveSpeed, rotSpeed;

    public Player(Vec2D pos, double rot, Vec2D... modelPoints) {
        this(pos, rot, new Model2D(modelPoints));
    }

    public Player(Vec2D pos, double rot, Model2D model) {
        this.pos = pos;
        this.model = model;
        this.rot = rot;
        this.model.setRotation(rot);

        moveSpeed = 2;
        rotSpeed = 2;

        EventListener eventListener = new EventListener() {

            @Override
            public void onEvent(Event event) {
                if(event instanceof EventFrameTick) {
                    update();
                    render();
                }
                else if(event instanceof EventInputKeyboard) {
                    EventInputKeyboard eventInputKeyboard = (EventInputKeyboard) event;
                    onKeyboardEvent(eventInputKeyboard.getEventKey(), eventInputKeyboard.getEventKeyState());
                }
                else if(event instanceof EventInputMouse) {
                    EventInputMouse eventInputMouse = (EventInputMouse) event;
                    onMouseEvent(eventInputMouse.getEventButton(), eventInputMouse.getEventButtonState());
                }
            }

            @Override
            public ArrayList<String> getRequiredEventsName() {
                ArrayList<String> requiredEventsName = new ArrayList<String>();
                requiredEventsName.add(EventFrameTick.class.getName());
                requiredEventsName.add(EventInputKeyboard.class.getName());
                requiredEventsName.add(EventInputMouse.class.getName());
                return requiredEventsName;
            }
        };
        EventHandler.register(eventListener);
    }

    private void render() {
        GL11.glPushMatrix();
        GL11.glTranslatef((float )pos.x, (float) pos.y, 0);
        model.render();
        GL11.glPopMatrix();
    }

    private void update() {
        if(InputHandler.isKeyDown(Keyboard.KEY_W)) {
            moveForward();
        }
        if(InputHandler.isKeyDown(Keyboard.KEY_S)) {
            moveBack();
        }
        if(InputHandler.isKeyDown(Keyboard.KEY_A)) {
            rotateClockwise();
        }
        if(InputHandler.isKeyDown(Keyboard.KEY_D)) {
            rotateCounterclockwise();
        }
    }

    private void onKeyboardEvent(int eventKey, boolean eventState) {
    }

    private void onMouseEvent(int eventButton, boolean eventButtonState) {
    }

    private void moveForward() {
        Vec2D move = new Vec2D(moveSpeed * MathUtil.cos(rot), moveSpeed * MathUtil.sin(rot));
        pos.add(move);
    }

    private void moveBack() {
        Vec2D move = new Vec2D(moveSpeed * MathUtil.cos(rot), moveSpeed * MathUtil.sin(rot));
        move.multiply(-1);
        pos.add(move);
    }

    private void rotateClockwise() {
        rot += rotSpeed;
        model.setRotation(rot);
    }

    private void rotateCounterclockwise() {
        rot -= rotSpeed;
        model.setRotation(rot);
    }

    public Vec2D[] getPoints() {
        Vec2D[] rotated = model.getRotatedPoints();
        Vec2D[] points = new Vec2D[rotated.length];
        Vec2D point;
        for(int i = 0; i < points.length; i++) {
            point = new Vec2D();
            point.add(rotated[i]);
            point.add(pos);
            points[i] = point;
        }
        return points;
    }
}
