/**
 * The package pepse.world.daynight contains classes related to managing day and
 * night cycles in a game world.
 */
package pepse.world.daynight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import java.awt.*;
/**
 * The Night class represents a visual representation of the night in a day-night cycle
 * within a game environment.
 * It creates a GameObject that serves as the background for the night, gradually
 * transitioning its opacity from transparent
 * to a certain level of darkness over a specified cycle length.
 */
public class Night {
    private static final String NIGHT_TAG = "night";
    private static final Float MIDNIGHT_OPACITY = 0.5f;
    /**
     * Creates a GameObject representing the night background with a gradually
     * transitioning opacity.
     * @param windowDimensions The dimensions of the game window as a Vector2 object
     *                        representing width and height.
     * @param cycleLength      The duration of the day-night cycle in seconds.
     * @return A GameObject representing the night background.
     */
    public static GameObject create(Vector2 windowDimensions, float cycleLength){
        Renderable nightRender = new RectangleRenderable(Color.BLACK);
        GameObject night = new GameObject(Vector2.ZERO, windowDimensions, nightRender);
        night.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        night.setTag(NIGHT_TAG);
        new Transition<Float>(night, night.renderer()::setOpaqueness, 0f,
                MIDNIGHT_OPACITY,
                Transition.CUBIC_INTERPOLATOR_FLOAT, cycleLength,
        Transition.TransitionType.TRANSITION_BACK_AND_FORTH, null);
        return night;
    }
}
