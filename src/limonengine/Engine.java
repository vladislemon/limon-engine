package limonengine;

import limonengine.render.Camera;
import limonengine.render.FpsCalculator;
import limonengine.render.Render;
import limonengine.render.model.ModelLoader;
import limonengine.timer.Timer;
import limonengine.util.input.InputHandler;
import org.lwjgl.opengl.DisplayMode;

/**
 * User: slimon
 * Date: 18.03.14
 * Time: 12:40
 */
public class Engine {

	private static boolean openGLInitialized = false;
    private static boolean isRunning = false;
    private static String title = "LimonEngine";

    private static Timer mainTimer;
    private static Render mainRender;
    private static FpsCalculator fpsCalculator;
    private static Camera devCamera;
    private static DebugController debugController;

    public static void initialize(int mainTimerTickRate, int maxFps, int renderMode, int displayWidth,
                                  int displayHeight, boolean vSync) {
        initRender(maxFps, renderMode, displayWidth, displayHeight, vSync);
        InputHandler.init();
        initTimer(mainTimerTickRate);

        debugController = new DebugController();
    }

    private static void initTimer(int mainTimerTickRate) {
        mainTimer = new Timer(mainTimerTickRate);
        mainTimer.start();
    }

    private static void initRender(int maxFps, int renderMode, int displayWidth, int displayHeight, boolean vSync) {
        mainRender = new Render(maxFps, vSync);
        mainRender.createDisplay(new DisplayMode(displayWidth, displayHeight));
        mainRender.initOpenGL(renderMode);
        openGLInitialized = true;

        fpsCalculator = new FpsCalculator(100);
        //devCamera = new Camera(new Vec3F(0, 0, 10), new Vec3F(180, 0, 0));

        ModelLoader.load(10);
    }

    public static void setTitle(String t) {
        title = t;
    }

    public static String getTitle() {
        return title;
    }

    public static void run() {
        isRunning = true;
        while(isRunning) {
            mainRender.render();
            mainRender.setTitle(title/* + " == FPS: " + Math.round(fpsCalculator.getFps())*/);
            if(mainRender.isCloseRequested()) {
                isRunning = false;
                mainTimer.stopTimer();
            }
        }
    }

    public static Timer getMainTimer() {
        return mainTimer;
    }

    public static Render getMainRender() {
        return mainRender;
    }

    public static boolean isOpenGLInitialized() {
		return openGLInitialized;
	}
}
