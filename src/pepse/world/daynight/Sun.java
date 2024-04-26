/**
 * The package pepse.world.daynight contains classes related to managing day and night
 * cycles in a game world.
 */
package pepse.world.daynight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.OvalRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import java.awt.*;
/**
 * The Sun class represents the sun object in the game world.
 * It creates a sun GameObject and manages its movement in the sky during the day.
 */
public class Sun {
    /**
     * Sum Diameter.
     */
    static final float SUN_DIAMETER = 50;
    private static final float INIT_ANGLE = 0f;
    private static final float FINAL_ANGLE = 360f;
    private static final String SUN_TAG ="sun";
    private static final float HALF = 0.5f;

    private static final float MID_SKY = 1f/3f;
    private static final float HORIZON = (float) 2/3;
    /**
     * Sun constructor.
     */
    public Sun(){};

    /**
     * Creates a sun GameObject with the specified window dimensions and day-night cycle length.
     * The sun moves in a circular path in the sky.
     * @param windowDimensions The dimensions of the game window.
     * @param cycleLength The length of the day-night cycle.
     * @return The GameObject representing the sun.
     */
    public static GameObject create(Vector2 windowDimensions, float cycleLength){
        Renderable sunRender = new OvalRenderable(Color.YELLOW);
        Vector2 center = new Vector2(windowDimensions.x()*HALF, windowDimensions.y()*MID_SKY);
        GameObject sun = new GameObject(center, new Vector2(SUN_DIAMETER,SUN_DIAMETER), sunRender);
        sun.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        sun.setTag(SUN_TAG);
        Vector2 cycleCenter = new Vector2(windowDimensions.x()*HALF,
                windowDimensions.y()*(HORIZON));
        new Transition<Float>(sun,
                    (Float angle) -> sun.setCenter
                            (center.subtract(cycleCenter)
                                    .rotated(angle)
                                    .add(cycleCenter)),
                INIT_ANGLE, FINAL_ANGLE,
                Transition.LINEAR_INTERPOLATOR_FLOAT, cycleLength,
                Transition.TransitionType.TRANSITION_LOOP, null);
        return sun;
    }
}
