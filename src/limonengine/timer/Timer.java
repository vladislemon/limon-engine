package limonengine.timer;

import limonengine.events.EventHandler;
import limonengine.events.EventTimerTick;

public class Timer extends Thread {

	private long tick;
	private int tickRate;
    private boolean stop = false;

    public Timer(int tickRate) {
		this.tickRate = tickRate;
    }

    public void stopTimer() {
        stop = true;
    }
	
	private void tick() {
		if(tick == Long.MAX_VALUE)
			tick = 0;
		tick++;
        EventHandler.handleEvent(new EventTimerTick(tick));
    }

	public int getTickRate() {
		return tickRate;
	}

    public void run() {
        long time, lastTime;
        int delay = Math.round(1000000000f / (float)tickRate); //nanoseconds
        int sleepTime;
        while(!stop) {
            lastTime = System.nanoTime();
            tick();
            time = System.nanoTime();
            sleepTime = (int)(delay - (time - lastTime));
            if(time - lastTime < delay) {
                try {
                    Thread.sleep(sleepTime/1000000, sleepTime%1000000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
