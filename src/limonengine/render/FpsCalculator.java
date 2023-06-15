package limonengine.render;

import limonengine.events.Event;
import limonengine.events.EventFrameTick;
import limonengine.events.EventHandler;
import limonengine.events.EventListener;
import limonengine.util.TimeUtil;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * slimon
 * 09.08.2014
 */
public class FpsCalculator {

    private double fps, fpsCount;
    private long lastTime, previousTime, lastFrame;
    private double period;

    public FpsCalculator(final double period) {
        lastTime = System.nanoTime();
        previousTime = lastTime;
        this.period = period;

        EventListener eventListener = new EventListener() {

            private ArrayList<String> requiredEventsName = new ArrayList<String>(Arrays.asList(EventFrameTick.class.getName()));

            @Override
            public void onEvent(Event event) {
                long frame = ((EventFrameTick)event).getFrame();
                long time = System.nanoTime();
                fpsCount += 1000000000. / (double) ((time - previousTime));
                if((double)(time - lastTime) / 1000000. >= period) {
                    fps = fpsCount / (double)(frame - lastFrame);
                    fpsCount = 0;
                    lastFrame = frame;
                    lastTime = time;
                }
                previousTime = time;
            }

            @Override
            public ArrayList<String> getRequiredEventsName() {
                return requiredEventsName;
            }
        };
        EventHandler.register(eventListener);
    }

    public double getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public double getFps() {
        return fps;
    }
}
